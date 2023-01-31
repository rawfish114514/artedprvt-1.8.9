package rawfish.artedprvt.script;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.mozilla.javascript.Context;
import rawfish.artedprvt.script.js.ClassCollection;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 脚本进程
 * 运行脚本时创建
 */
public class ScriptProcess {
    public static List<ScriptProcess> proList=new ArrayList<>();//运行的进程
    public static List<String> sargList=new ArrayList<>();//可用的参数
    public static void initSargs() {
        if (sargList.size() != 0) {
            return;
        }
        sargList.add("-s");//服务端处理
        sargList.add("-rc");//重混淆
        sargList.add("-eh");//线程处理
        sargList.add("-m");//备忘录
        sargList.add("-st");//单线程
        sargList.add("-pm");//线程最大优先级
        sargList.add("-al");//自动设置监听器
    }

    protected static int spid=0;//进程起始id
    protected int pid;//进程id
    protected boolean pkg;//是pkg
    protected Map<String,String> pkgs;//文件内容
    protected ScriptConfig config;//配置
    protected String commandName;//命令
    protected String dir;//项目目录
    protected String spath;//脚本路径
    protected ICommandSender sender;//用户
    protected List<String> sargs;//命令参数
    protected String pack;//主包名
    protected String name;//进程名
    protected List<String> args;//脚本参数
    protected Context rhino;//脚本上下文
    protected ScriptSystem sys;//脚本系统
    protected ScriptClient client;//脚本客户端
    protected PortClass port;//导入类
    protected Map<String,ScriptUnit> env;//包加载集合
    protected MainThread thread;//主线程
    protected List<ScriptThread> tl;//等待线程列表

    protected long time;//开始时间
    protected int ret;//状态

    protected int nativeobject;//创建的java对象数

    public ScriptProcess(ICommandSender senderIn,String commandNameIn,String dirIn,List<String> sargsIn,String packIn, List<String> argsIn) throws CommandException {
        sender=senderIn;
        commandName=commandNameIn;
        dir=dirIn;
        sargs=sargsIn;
        pack=packIn;
        name=pack;
        args=argsIn;

        pkg=new File(dir).isFile();

        if(pkg){
            //配置必要
            try {
                readPkg();
            } catch (Exception e) {
                throw new CommandException("apkg: 加载文件失败");
            }
            config=ScriptConfig.loads(pkgs.get("config.json"));
            if(config==null){
                throw new CommandException("apkg: 未找到配置文件");
            }

            if(config.pkgError){
                throw new CommandException("script: 读取配置时发生意外");
            }
            pack=(String)config.pkg.get("pack");
            name=packIn;
        }else {
            //配置非必要
            config=ScriptConfig.load(dir);
        }

        if(config!=null) {
            if(config.error){
                throw new CommandException("script: 读取配置时发生意外");
            }
            for(Object sarg:config.options){
                if(sargList.contains(String.valueOf(sarg))){
                    sargs.add(String.valueOf(sarg));
                }
            }
        }
        Set<String> ss=new HashSet<>(sargs);
        sargs=new ArrayList<>();
        sargs.addAll(ss);
        systemArgs(sargs);
        if(getValueS()){
            StringBuilder sb = new StringBuilder("/");
            sb.append(commandNameIn);
            for (String arg:sargs) {
                if(!arg.equals("-s")) {
                    sb.append(' ');
                    sb.append(arg);
                }
            }
            sb.append(' ');
            sb.append(packIn);
            for(String arg:args){
                sb.append(' ');
                sb.append(arg);
            }
            Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(new C01PacketChatMessage(sb.toString()));
            ret=11;//进程失效
            return;
        }
        if(getValueRc()) {
            ClassCollection.putExtend();
        }
        ret=0;//进程创建 无效退出

        spath=dir+"/script/";

        if(proList.size()==0){
            spid=0;
        }
        pid=spid++;
        proList.add(this);
    }

    public ICommandSender getSender(){
        return sender;
    };
    public String getPack(){
        return pack;
    }

    public int getId(){
        return pid;
    }

    public int getRet(){
        return ret;
    }

    public long getTime(){
        return time;
    }

    public List<String> getSargs(){
        return sargs;
    }

    public List<String> getArgs(){
        return args;
    }

    public ScriptSystem getSys(){
        return sys;
    }

    public void addNativeObjectNumber(){
        nativeobject++;
    }

    protected void systemArgs(List<String> sargs){
        for(String sarg:sargs){
            if(sarg.equals("-s")){
                sarg_S();
            }
            if(sarg.equals("-rc")){
                sarg_Rc();
            }
            if(sarg.equals("-eh")){
                sarg_EH();
            }
            if(sarg.equals("-m")){
                sarg_M();
            }
            if(sarg.equals("-st")){
                sarg_ST();
            }
            if(sarg.equals("-pm")){
                sarg_PM();
            }
            if(sarg.equals("-al")){
                sarg_AL();
            }
        }
    }

    /**
     * 读取文件
     */
    public void readPkg() throws Exception {
        pkgs=new HashMap<>();

        ZipInputStream zip = new ZipInputStream(
                new FileInputStream(dir),
                Charset.forName("GBK"));

        Reader reader=new InputStreamReader(zip,StandardCharsets.UTF_8);

        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            String name = entry.getName();
            if (!entry.isDirectory()) {
                int n;
                int i=0;
                StringBuilder sb=new StringBuilder(0);
                while ((n = reader.read()) != -1) {
                    sb.append((char)n);
                }
                pkgs.put(entry.getName(),sb.toString());
            }
        }
    }

    /**
     * 读取工作目录下的文件
     * @param packIn 模块名
     * @return
     */
    protected String readString(String packIn){
        if(packIn.contains("/")){
            throw new RuntimeException("pack: "+packIn+" (用'.'分隔符而不是'/')");
        }
        if(!withPackRule(packIn)){
            throw new RuntimeException("pack: "+packIn+" (开头为英文字母后接英文字母数字下划线)");
        }
        String path=packIn.replace('.','/')+".js";

        if(pkg){
            String str=pkgs.get("script/"+path);
            if(str==null){
                throw new RuntimeException("Invalid file path");
            }
            return str;
        }

        File file=new File(spath+path);
        Reader reader;
        StringBuilder sb;
        try {
            reader=new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            sb=new StringBuilder();
            while(true){
                int n=reader.read();
                if(n==-1) {
                    break;
                }
                sb.append((char)n);
            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    protected static String packMatcher="^[a-zA-Z][a-zA-Z0-9_]*$";
    protected boolean withPackRule(String path){
        String[] layers=path.split("\\.");
        for(String s:layers){
            if(!s.matches(packMatcher)){
                return false;
            }
        }
        return true;
    }

    /**
     * 加载模块
     * @param packIn 包名
     * @param invokerIn 调用上级
     */
    public void load(String packIn,String invokerIn){
        ScriptUnit module=new ScriptUnit(this,readString(packIn),packIn);
        env.put(module.pack,module);
        module.run(invokerIn);
    }


    //准备工作并运行
    public void start(){
        ret=1;//进程启动 无效退出
        time=0;
        tl=new ArrayList<>();
        thread=new MainThread(this);

        thread.start();
    }
    //准备工作完成
    public void begin(){
        ret=2;//进程准备 正常退出
        time=new Date().getTime();
        sys.print(pack,"\u00a73run:\u00a7a " + name);
    }
    //终止进程
    public void stop(ScriptThread st){
        if(ret==1) {
            ret=-1;//进程终止 无效退出
        }
        if(ret==2){
            ret=-2;//进程终止 非正常退出
        }
        thread.jstop(st);
    }
    //运行结束
    protected boolean onEnd=false;
    public void end(){
        if(onEnd){
            //防止有更多线程执行end
            return;
        }
        onEnd=true;
        long t=new Date().getTime();
        if(ret==2) {
            sys.print(pack,"\u00a72end:\u00a7a " + name + "\u00a77(" + (t-time) + "ms)",getStatistics(t));
        }else if(ret==-2){
            sys.print(pack,"\u00a74break:\u00a7a " + name + "\u00a77(" + (t-time) + "ms)",getStatistics(t));
        }
        proList.remove(this);
        ret=7;//进程结束
    }


    //服务端处理 这个进程将任务推给服务端
    protected boolean s_value=false;
    public boolean getValueS(){
        return s_value;
    }
    protected void sarg_S(){
        s_value=true;
    }

    //重混淆 访问字段或方法时将MCP名映射到Srg名 在非开发环境中使用
    protected boolean rc_value=false;
    public boolean getValueRc(){
        return rc_value;
    }
    protected void sarg_Rc(){
        rc_value=true;
    }

    //线程处理 等待线程异常不会终结主线程
    protected boolean eh_value=false;
    public boolean getValueEh(){
        return eh_value;
    }
    protected void sarg_EH(){
        eh_value=true;
    }

    //备忘录模式 所有和世界相关的操作都会记录 方便撤销和重做
    protected void sarg_M(){

    }

    //单线程 等待线程开始运行不会启动新线程
    protected boolean st_value=false;
    public boolean getValueSt(){
        return st_value;
    }
    protected void sarg_ST(){
        st_value=true;
    }

    //线程最大优先级 线程默认优先级为10 稍微提高运行速度
    protected boolean pm_value=false;
    public boolean getValuePm(){
        return pm_value;
    }
    protected void sarg_PM(){
        pm_value=true;
    }

    //自动设置监听器 无需手动创建和注册监听器 以声明事件同名函数完成监听
    protected boolean al_value=false;
    public boolean getValueAl(){
        return al_value;
    }
    protected void sarg_AL(){
        al_value=true;
    }

    public String getStatistics(){
        return getStatistics(new Date().getTime());
    }

    public String getStatistics(long time){
        //ret: 状态 runtime: 运行时间 [系统参数;脚本参数]
        String line1;
        //nativejava: 创建java对象数
        String line2;

        line1=String.format("ret: %s runtime: %s",ret,time-getTime());
        String as=String.format(" [%s;%s]",String.join(" ",getSargs()),String.join(" ",getArgs()));
        if(!as.equals(" [;]")){
            line1+=as;
        }

        line2=String.format("nativeobject: %s",nativeobject);

        return String.join("\n",line1,line2);
    }
}

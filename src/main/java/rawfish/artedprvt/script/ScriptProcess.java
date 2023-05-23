package rawfish.artedprvt.script;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.mozilla.javascript.Context;
import rawfish.artedprvt.script.js.ClassCollection;

import java.io.*;
import java.util.*;

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
    protected FileLoader fileLoader;//文件加载器
    protected ScriptLoader scriptLoader;//脚本加载器
    protected Map<String,String> props;//属性
    protected ScriptConfig config;//配置
    protected String commandName;//命令
    protected String dir;//项目目录
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
    protected List<ScriptThread> tl;//脚本线程列表
    protected int tn;//脚本线程创建数

    protected long time;//开始时间
    protected int ret;//状态

    protected boolean hasError;

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
            try {
                fileLoader=new ApkgFileLoader(dir);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            fileLoader=new SourceFileLoader(dir);
        }

        String conf=fileLoader.readFile("config.json");
        if(conf!=null){
            config=ScriptConfig.loads(conf);
        }

        if(pkg){
            //配置必要
            if(config==null){
                throw new CommandException("apkg: 未找到配置文件");
            }
            if(config.pkgError){
                throw new CommandException("script: 读取配置时发生意外");
            }
            pack=(String)config.pkg.get("pack");
            name=packIn;
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

        hasError=false;


        if(proList.size()==0){
            spid=0;
        }
        pid=spid++;
        proList.add(this);

        scriptLoader=new ScriptLoader(fileLoader);
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

    public MainThread getThread(){
        return thread;
    }

    public boolean getPkg(){
        return pkg;
    }

    public String getDir(){
        return dir;
    }

    public String getName(){
        return name;
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
     * 读取工作目录下的脚本
     * @param packIn 模块名
     * @return
     */
    protected String readScript(String packIn){
        if(packIn.contains("/")){
            throw new RuntimeException("pack: "+packIn+" (用'.'分隔符而不是'/')");
        }
        if(!withPackRule(packIn)){
            throw new RuntimeException("pack: "+packIn+" (开头为英文字母后接英文字母数字下划线)");
        }
        String script=scriptLoader.readScript(packIn);
        if(script==null){
            throw new RuntimeException("找不到文件");
        }
        return script;
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
        ScriptUnit module=new ScriptUnit(this, readScript(packIn),packIn);
        env.put(module.pack,module);
        module.run(invokerIn);
    }


    //准备工作并运行
    public void start(){
        ret=1;//进程启动 无效退出
        time=0;
        tl=new ArrayList<>();
        tn=0;
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
    public void stop(ScriptThread st,int exitstatus){
        if(ret==1) {
            ret=-1;//进程终止 无效退出
        }
        if(ret==2){
            ret=-2;//进程终止 非正常退出
        }
        if(exitstatus<1&&!hasError){
            ret=3;
        }
        thread.jstop(st,exitstatus);
    }
    //运行结束
    protected boolean onEnd=false;
    public void end(int exitstatus){
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
        }else if(ret==3) {
            if(exitstatus==0) {
                sys.print(pack, "\u00a72exit:\u00a7a " + name + "\u00a77(" + (t - time) + "ms)", getStatistics(t));
            }else{
                sys.print(pack,"\u00a74exit:\u00a7a " + name + "\u00a77(" + (t-time) + "ms) "+exitstatus,getStatistics(t));
            }
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

    //线程处理 脚本线程异常不会终结主线程
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

    //单线程 脚本线程开始运行不会启动新线程
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
        List<String> lines=new ArrayList<>();
        String str;
        str=String.format("%s (%s)",pack,pid);
        String as=String.format(" [%s;%s]",String.join(" ",getSargs()),String.join(" ",getArgs()));
        if(!as.equals(" [;]")){
            str+=as;
        }
        lines.add(str);//进程名 (进程id) [系统参数;脚本参数]

        str=String.format("ret: %s runtime: %s",ret,time-getTime());
        lines.add(str);//ret: 进程状态 runtime: 运行时间

        return String.join("\n",lines);
    }
}

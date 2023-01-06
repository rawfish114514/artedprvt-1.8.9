package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import org.mozilla.javascript.Context;
import rawfish.artedprvt.id.FormatCode;

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
        sargList.add("-eh");//线程处理模式
        sargList.add("-m");//备忘录模式
        sargList.add("-st");//单线程模式
        sargList.add("-pm");//线程最大优先级
    }

    protected static int spid=0;//进程起始id
    protected int pid;//进程id


    protected String dir;//脚本目录
    protected ICommandSender sender;//用户
    protected String[] sargs;//命令参数
    protected String pack;//主包名 进程名
    protected String[] args;//脚本参数
    protected Context rhino;//脚本上下文
    protected ScriptSystem sys;//脚本系统
    protected Map<String,ScriptUnit> env;//包加载集合
    protected MainThread thread;//主线程
    protected List<ScriptThread> tl;//等待线程列表

    protected long time;//开始时间
    protected int ret;//状态
    public ScriptProcess(ICommandSender senderIn,String[] sargsIn,String packIn, String[] argsIn){
        ret=0;//进程创建 无效退出
        sender=senderIn;
        sargs=sargsIn;
        pack=packIn;
        args=argsIn;
        dir=System.getProperties().get("user.dir").toString()+"/artedprvt/script/";

        if(proList.size()==0){
            spid=0;
        }
        pid=spid++;
        proList.add(this);
        systemArgs(sargs);
    }

    public String getName(){
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

    public String[] getSargs(){
        return sargs;
    }

    public String[] getArgs(){
        return args;
    }

    protected void systemArgs(String[] sargs){
        for(String sarg:sargs){
            if(sarg.equals("-eh")){
                sarg_EH();
            }
            if(sarg.equals("-m")){
                sarg_M();
            }
        }
    }

    /**
     * 读取工作目录下的文件
     * @param packIn 模块名
     * @return
     */
    protected String readString(String packIn){
        if(packIn.indexOf("/")!=-1){
            try {
                throw new Exception("pack: "+packIn+" (用'.'分隔符而不是'/')");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(!withPackRule(packIn)){
            try {
                throw new Exception("pack: "+packIn+" (开头为英文字母后接英文字母数字下划线)");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String path=packIn.replace('.','/')+".js";
        File file=new File(dir+path);
        Reader reader;
        StringBuilder sb;
        try {
            reader=new FileReader(file);
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
        ScriptThread.n=0;
        thread=new MainThread(this);

        thread.start();
    }
    //准备工作完成
    public void begin(){
        ret=2;//进程准备 正常退出
        time=new Date().getTime();
        sys.print(pack,"\u00a73run:\u00a7a " + pack);
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
        long t=new Date().getTime()-time;
        if(ret==2) {
            sys.print(pack,"\u00a72end:\u00a7a " + pack + "\u00a77(" + t + "ms)");
        }else if(ret==-2){
            sys.print(pack,"\u00a74break:\u00a7a " + pack + "\u00a77(" + t + "ms)");
        }
        proList.remove(this);
        ret=7;//进程结束
    }

    protected boolean eh_value=false;

    //线程处理模式 等待线程异常不会终结主线程
    protected void sarg_EH(){
        eh_value=true;
    }

    //备忘录模式 所有和世界相关的操作都会记录 方便撤销和重做
    protected void sarg_M(){

    }

    //单线程模式 等待线程开始运行不会启动新线程
    protected void sarg_ST(){

    }

    //线程最大优先级 线程默认优先级为10 稍微提高运行速度
    protected void sarg_PM(){

    }

}

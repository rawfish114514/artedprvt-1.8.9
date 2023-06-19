package rawfish.artedprvt.core;

import rawfish.artedprvt.core.engine.ScriptEngine;
import rawfish.artedprvt.core.engine.ScriptStackParser;
import rawfish.artedprvt.core.rhino.RhinoEngine;
import rawfish.artedprvt.core.rhino.RhinoScriptStackParser;
import rawfish.artedprvt.core.struct.ApkgFileLoader;
import rawfish.artedprvt.core.struct.FileLoader;
import rawfish.artedprvt.core.struct.ScriptLoader;
import rawfish.artedprvt.core.struct.SourceFileLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 进程
 */
public class ScriptProcess {
    private static List<ScriptProcess> proList=new ArrayList<>();//运行的进程
    private static int spid=0;//进程起始id
    private Map<String,String> props;//属性
    private FileLoader fileLoader;//文件加载器
    private ScriptLoader scriptLoader;//脚本加载器
    private List<String> scriptArgument;//脚本参数
    private ScriptInfo scriptInfo;//脚本配置
    private List<ScriptEngine> engines;//引擎列表
    private MainThread mainThread;//主线程
    private List<ScriptThread> threads;//脚本线程列表
    private List<ScriptStackParser> stackParsers;//脚本堆栈解析器
    private ScriptExceptionHandler exceptionHandler;//异常处理程序
    private int pid;//进程id
    private String name;//进程名

    private long time;//开始时间
    private int ret;//进程状态
    private boolean hasError;//运行错误

    private ScriptSystem scriptSystem;//脚本系统


    private List<ScriptObject> scriptObjects;//脚本对象列表
    private int scriptObjectNumber;//脚本对象创建数

    /**
     * 创建进程
     * 包具有以下概念
     * 可作为完整模块名 "js:a.b.c" -> a/b/c.js
     * 可作为apkg包的路径 "a.b.c" -> a/b/c.apkg
     * 具体使用由文件加载器决定
     * @param command 命令类型
     * @param pack 包
     * @param scriptArgument 脚本参数
     * @throws Exception
     */
    public ScriptProcess(
            String command,
            String pack,
            List<String> scriptArgument) throws Exception {
        ret=CREATE;
        hasError=false;
        props= FrameProperties.props();
        fileLoader=null;
        if(command.equals("script")){
            fileLoader=new SourceFileLoader(props.get("frame.dir")+"/src");
        }
        if(command.equals("apkg")){
            fileLoader=new ApkgFileLoader(props.get("frame.dir")+"/lib/"+pack.replace(".","/")+".apkg");
        }
        if(fileLoader==null){
            throw new RuntimeException("文件加载器初始化失败");
        }
        scriptLoader=new ScriptLoader(fileLoader);
        this.scriptArgument=scriptArgument;

        String apkginfo=fileLoader.readFile("apkg.info");
        scriptInfo=ScriptInfo.parse(apkginfo);
        ScriptInfo.inspect(scriptInfo);

        if(command.equals("script")){
            scriptInfo.setModule(pack);
            scriptInfo.setName(pack);
        }

        engines=new ArrayList<>();
        engines.add(new RhinoEngine(this));

        stackParsers=new ArrayList<>();
        stackParsers.add(new RhinoScriptStackParser());

        exceptionHandler=new ScriptExceptionHandler(this);
        if(proList.size()==0){
            spid=0;
        }
        pid=spid++;
        proList.add(this);
        name= scriptInfo.getName();
        time=0;

        scriptObjects=new ArrayList<>();
        scriptObjectNumber=0;
        scriptSystem=new ScriptSystem(this);
    }

    /**
     * 准备工作并运行
     * 由外部调用
     */
    public synchronized void start(){
        if(ret!=CREATE){
            ScriptExceptions.exception("进程状态异常");
        }
        ret=START;//进程启动 无效退出
        threads=new ArrayList<>();
        mainThread=new MainThread(this);

        mainThread.start();
    }

    /**
     * 准备工作完成
     * 由主线程调用
     */
    public void begin(){
        ret=BEGIN;//进程准备 正常退出
        time=System.currentTimeMillis();
        printStart();
    }

    /**
     * 终止进程
     * 由外部调用或运行结束自动调用
     * @param exitCode
     */
    public void stop(int exitCode){
        if(ret==START) {
            ret=ESTART;//进程终止 无效退出
        }
        if(ret==BEGIN){
            ret=EBEGIN;//进程终止 非正常退出
        }
        if(exitCode<1&&!hasError){
            ret=STOP;
        }

        //终止所有相关的线程 在最后终止当前线程前结束进程
        Thread currentThread=Thread.currentThread();
        if(currentThread==mainThread) {
            //主线程
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).stop();
            }
            end(exitCode);
            mainThread.jstop();
        }else{
            //脚本线程
            ScriptThread t;
            for (int i = 0; i < threads.size(); i++) {
                t=threads.get(i);
                if(!t.equals(currentThread)){
                    t.stop();
                }
            }
            end(exitCode);
            mainThread.jstop();
            if(threads.contains(currentThread)) {
                currentThread.stop();
            }
        }
    }

    protected boolean onEnd=false;

    /**
     * 进程结束
     * 由外部调用或运行结束自动调用
     * @param exitCode
     */
    public void end(int exitCode){
        if(onEnd){
            return;
        }
        onEnd=true;
        closeScriptObject();
        ret=END;
        printEnd(exitCode,System.currentTimeMillis()-time);
        proList.remove(this);
    }

    private void printStart(){
        scriptSystem.print(ScriptSystem.HIGH,"§3run:§r "+name);
    }

    private void printEnd(int status,long runtime){
        rtime=runtime;
        String s= getStatistics();
        if(status==EXIT){
            scriptSystem.print(ScriptSystem.HIGH,"§2end:§r "+name+"§7("+runtime+"ms)",s);
            return;
        }
        if(status==ERROR){
            scriptSystem.print(ScriptSystem.HIGH,"§4break:§r "+name+"§7("+runtime+"ms)",s);
            return;
        }
        if(status==STOPS){
            scriptSystem.print(ScriptSystem.HIGH,"§4stop:§r "+name+"§7("+runtime+"ms)",s);
            return;
        }
        if(status>=0){
            scriptSystem.print(ScriptSystem.HIGH,"§2exit:§r "+name+"§7("+runtime+"ms) "+status,s);
        }else{
            scriptSystem.print(ScriptSystem.HIGH,"§4exit:§r "+name+"§7("+runtime+"ms) "+status,s);
        }
    }

    /**
     * ret const
     */
    public static final int
    CREATE=0,
    START=1,
    BEGIN=2,
    STOP=3,
    ESTART=-1,
    EBEGIN=-2,
    END=7;

    public static final int
    EXIT=0,
    ERROR=-1,
    STOPS=-2;


    public void addScriptObject(ScriptObject scriptObject){
        scriptObjects.add(scriptObject);
        scriptObjectNumber++;
    }

    public void closeScriptObject(){
        ScriptObject scriptObject;
        for(int i=0;i<scriptObjects.size();){
            scriptObject=scriptObjects.get(i);
            scriptObject.close();
            scriptObjects.remove(scriptObject);
        }
    }

    public int getOnRunThreadNumber(){
        int n=0;
        if(mainThread.getState()!=Thread.State.TERMINATED){
            n++;
        }
        ScriptThread thread;
        for(int i=0;i<threads.size();i++){
            thread=threads.get(i);
            if(thread.getState()!=Thread.State.TERMINATED){
                n++;
            }
        }
        return n;
    }

    private long rtime=-1;
    public String getStatistics(){
        long time=getRuntime();

        String line1="[";
        for(String arg:scriptArgument){
            line1+=arg;
            line1+=", ";
        }
        if(line1.length()>1) {
            line1=line1.substring(0, line1.length() - 2);
        }
        line1+="]";
        String line2="ret: "+ret;
        String line3="object: "+scriptObjectNumber;
        String stime=String.valueOf(time);
        if(stime.length()>3){
            stime=stime.substring(0,stime.length()-3)+"§7"+stime.substring(stime.length()-3);
        }else{
            stime="§7"+stime;
        }
        String line4="runtime: "+stime;

        String str="";
        if(!line1.equals("[]")){
            str+=line1+"\n";
        }
        str+=line2+"\n";
        str+=line3+"\n";
        str+=line4;
        return str;
    }

    public long getRuntime(){
        long time;
        if(rtime>=0){
            time=rtime;
        }else{
            time=System.currentTimeMillis()-this.time;
        }
        return time;
    }

    public boolean isThread(Thread thread) {
        if(thread==mainThread){
            return true;
        }
        for (int i = 0; i < threads.size(); i++) {
            if(thread==threads.get(i)){
                return true;
            }
        }
        return false;
    }

    public static List<ScriptProcess> getProList() {
        return proList;
    }

    public static int getSpid() {
        return spid;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public FileLoader getFileLoader() {
        return fileLoader;
    }

    public ScriptLoader getScriptLoader() {
        return scriptLoader;
    }

    public List<String> getScriptArgument() {
        return scriptArgument;
    }

    public ScriptInfo getScriptInfo() {
        return scriptInfo;
    }

    public List<ScriptEngine> getEngines() {
        return engines;
    }

    public List<ScriptStackParser> getStackParsers(){
        return stackParsers;
    }

    public ScriptExceptionHandler getExceptionHandler(){
        return exceptionHandler;
    }

    public MainThread getMainThread() {
        return mainThread;
    }

    public List<ScriptThread> getThreads() {
        return threads;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public int getRet() {
        return ret;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void hasError(){
        hasError=true;
    }

    public ScriptSystem getScriptSystem() {
        return scriptSystem;
    }
}

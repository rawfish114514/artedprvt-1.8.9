package rawfish.artedprvt.core.script;

import rawfish.artedprvt.core.CoreInitializer;
import rawfish.artedprvt.core.InProcess;
import rawfish.artedprvt.core.Logger;
import rawfish.artedprvt.core.Process;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.script.engine.ScriptEngine;
import rawfish.artedprvt.core.script.engine.ScriptStackParser;
import rawfish.artedprvt.core.script.rhino.RhinoEngine;
import rawfish.artedprvt.core.script.rhino.RhinoStackParser;
import rawfish.artedprvt.core.script.struct.AarFileLoader;
import rawfish.artedprvt.core.script.struct.FileLoader;
import rawfish.artedprvt.core.script.struct.ScriptLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程
 */
public class ScriptProcess extends Process {
    public static final ProcessIdLevel SCRIPT_PROCESS_ID_LEVEL=new ProcessIdLevel(0,4095);
    private WorkSpace workSpace;
    private FileLoader fileLoader;//文件加载器
    private ScriptLoader scriptLoader;//脚本加载器
    private List<String> scriptArgument;//脚本参数
    private MetaData metadata;//脚本配置
    private ScriptLogger scriptLogger;//脚本日志记录器
    private List<ScriptEngine> engines;//引擎列表
    private MainThread mainThread;//主线程
    private List<ScriptThread> threads;//脚本线程列表
    private List<ScriptStackParser> stackParsers;//脚本堆栈解析器
    private ScriptExceptionHandler exceptionHandler;//异常处理程序

    private ScriptSystem scriptSystem;//脚本系统


    private List<InProcess> inProcessList;//InProcess列表
    private int inProcessCount;//InProcess对象创建数




    private OutputStream logFileOutputStream;//日志的文件输出流


    /**
     * 从输入流创建进程
     * 以aar的方式加载文件
     * @param inputStream
     * @param scriptArgument
     * @throws Exception
     */
    public ScriptProcess(
            InputStream inputStream,
            List<String> scriptArgument) throws Exception {
        this(new AarFileLoader(inputStream),scriptArgument);
    }

    public ScriptProcess(String pack,FileLoader fileLoader,List<String> scriptArgument) throws Exception{
        this(fileLoader,scriptArgument);
        metadata.setModule(pack);
        metadata.setName(pack);
        MetaData.inspect(metadata);
        name= metadata.getName();
    }

    public ScriptProcess(FileLoader fileLoader,List<String> scriptArgument) throws Exception{
        super();

        ret=CREATE;
        workSpace=WorkSpace.currentWorkSpace();
        this.fileLoader=fileLoader;
        scriptLoader=new ScriptLoader(fileLoader);
        this.scriptArgument=scriptArgument;

        String aarinfo=fileLoader.getContent("aar.toml");
        metadata = MetaData.parse(aarinfo);
        MetaData.inspect(metadata);

        name= metadata.getName();
        icon= loadIcon(fileLoader.getInputStream("icon.png"));

        synchronized (ScriptProcess.class) {
            LocalDate localDate=LocalDate.now();
            logFileOutputStream=CoreInitializer.getLogFileController().openLog(
                    workSpace,
                    localDate,
                    name.substring(name.indexOf(':')+1));
            logFileOutputStream=new BufferedOutputStream(logFileOutputStream);
            scriptLogger = new ScriptLogger(
                    this,
                    localDate,
                    logFileOutputStream,
                    System.out);
        }


        engines=new ArrayList<>();
        engines.add(new RhinoEngine(this));

        stackParsers=new ArrayList<>();
        stackParsers.add(new RhinoStackParser());

        exceptionHandler=new ScriptExceptionHandler(this);

        inProcessList=new ArrayList<>();
        inProcessCount=0;
    }


    private BufferedImage loadIcon(InputStream stream){
        if(stream==null){
            return loadDefaultIcon();
        }
        try {
            BufferedImage image= ImageIO.read(stream);
            if(image==null||image.getHeight()!=16||image.getWidth()!=16){
            }else {
                return image;
            }
        } catch (IOException ignored) {
        }
        return loadDefaultIcon();
    }


    @Override
    public ProcessIdLevel pidLevel() {
        return SCRIPT_PROCESS_ID_LEVEL;
    }

    @Override
    public Logger logger() {
        return scriptLogger;
    }

    /**
     * 准备工作并运行
     * 由外部调用
     */
    @Override
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
        initTime();
        printStart();
    }

    /**
     * 终止进程
     * 由外部调用或运行结束自动调用
     * @param exitCode
     */
    @Override
    public void stop(int exitCode){
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

    /**
     * 进程结束
     * 由外部调用或运行结束自动调用
     * @param exitCode
     */
    @Override
    public synchronized void end(int exitCode){
        if(ret==END){
            return;
        }
        try {
            closeInProcessObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ret=END;
        printEnd(exitCode,runningTime());
        closeLog();
    }

    private void printStart(){
        String s="§3run:§r "+name;
        scriptLogger.natives(s);
        scriptSystem.print("§3run:§r "+name);
    }

    private void printEnd(int status,long runtime){
        rtime=runtime;
        String stat= getStatistics();
        String s;
        sw:{
            if (status == EXIT) {
                s="§2end:§r " + name + "§7(" + runtime + "ms)";
                break sw;
            }
            if (status == ERROR) {
                s="§4break:§r " + name + "§7(" + runtime + "ms)";
                break sw;
            }
            if (status == STOPS) {
                s="§4stop:§r " + name + "§7(" + runtime + "ms)";
                break sw;
            }
            if (status >= 0) {
                s="§2exit:§r " + name + "§7(" + runtime + "ms) " + status;
            } else {
                s="§4exit:§r " + name + "§7(" + runtime + "ms) " + status;
            }
        }
        scriptLogger.natives(s);
        scriptSystem.print(s,stat);
    }

    @Override
    public void up(InProcess inProcessObject){
        inProcessList.add(inProcessObject);
        inProcessCount++;
    }

    @Override
    public void down(InProcess inProcessObject) {
        inProcessList.remove(inProcessObject);
    }

    private void closeInProcessObject(){
        InProcess inProcess;
        for(int i=0;i<inProcessList.size();){
            inProcess=inProcessList.get(i);
            inProcess.close();
            inProcessList.remove(inProcess);
        }
    }

    private void closeLog() {
        scriptLogger.close();
        try {
            logFileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRunningThreadCount(){
        int n=0;
        if(mainThread!=null&&mainThread.getState()!=Thread.State.TERMINATED){
            n++;
        }
        ScriptThread thread;
        for(int i=0;i<threads.size();i++){
            thread=threads.get(i);
            if(thread!=null&&thread.getState()!=Thread.State.TERMINATED){
                n++;
            }
        }
        return n;
    }

    public void setName(String name){
        this.name=name;
    }

    private long rtime=-1;
    public String getStatistics(){
        long time=runningTime();

        String line1="[";
        for(String arg:scriptArgument){
            line1+=arg;
            line1+="§7, §r";
        }
        if(line1.length()>1) {
            line1=line1.substring(0, line1.length() - 6);
        }
        line1+="]";
        String line2="ret: "+ret;
        String line3="object: "+inProcessCount;
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

    private long gclastCpuTime =-1;
    private long gclastTime =-1;
    private double oldCPU=0;
    public double getCPU(){
        if(ret==END){
            return 0;
        }
        if(gclastCpuTime ==0){
            gclastCpuTime =0;
            gclastTime =System.currentTimeMillis();
            List<Thread> threadList=new ArrayList<>();
            threadList.add(mainThread);
            threadList.addAll(threads);
            ThreadMXBean threadMXBean= ManagementFactory.getThreadMXBean();
            for(Thread t:threadList){
                long cpu=threadMXBean.getThreadCpuTime(t.getId());
                gclastCpuTime +=cpu;
            }
            return 0;
        }
        long cpuTime=0;
        long time=System.currentTimeMillis();
        List<Thread> threadList=new ArrayList<>();
        threadList.add(mainThread);
        threadList.addAll(threads);
        ThreadMXBean threadMXBean= ManagementFactory.getThreadMXBean();
        for(Thread t:threadList){
            long cpu=threadMXBean.getThreadCpuTime(t.getId());
            cpuTime+=cpu;
        }

        long cpuIncrement=cpuTime- gclastCpuTime;
        long timeIncrement=time- gclastTime;
        if(timeIncrement<200){
            return oldCPU;
        }
        gclastCpuTime =cpuTime;
        gclastTime =time;
        if(timeIncrement>10000){
            return 0;
        }
        oldCPU=cpuIncrement/1.0e6/Runtime.getRuntime().availableProcessors()/timeIncrement;
        return oldCPU;
    }

    public long getMemory(){
        return -1;
    }

    public WorkSpace getWorkSpace() {
        return workSpace;
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

    public MetaData getScriptInfo() {
        return metadata;
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

    public ScriptSystem getScriptSystem() {
        return scriptSystem;
    }

    public void setScriptSystem(ScriptSystem scriptSystem) {
        this.scriptSystem=scriptSystem;
    }

}

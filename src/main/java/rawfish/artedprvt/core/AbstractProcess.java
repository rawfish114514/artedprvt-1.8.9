package rawfish.artedprvt.core;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象进程，主要完成这几个功能和工作
 * 维护 InProcess 对象列表
 * 要求实现进程线程
 * 要求实现异常处理程序
 */
public abstract class AbstractProcess<T extends AbstractProcess<T>> extends Process implements Runnable{
    protected List<InProcess> inProcessList;

    protected AbstractThread<T> mainThread;

    protected List<AbstractThread<T>> threads;


    public AbstractProcess(){
        super();
        ret=CREATE;

        inProcessList=new ArrayList<>();
    }

    public abstract AbstractExceptionHandler<T> getExceptionHandler();

    public abstract AbstractThread<T> createThread(Runnable runnable);


    /**
     * 准备工作并运行
     * 由外部调用
     */
    @Override
    public synchronized void start(){
        if(ret!=CREATE){
            throw new RuntimeException("进程异常");
        }
        ret=START;
        threads =new ArrayList<>();
        mainThread=createThread(this);

        mainThread.start();
    }

    /**
     * 准备工作完成
     * 由主线程调用
     */
    public void begin(){
        ret=BEGIN;
    }

    /**
     * 终止进程
     * 由外部调用或运行结束自动调用
     * @param exitCode
     */
    @Override
    public void stop(int exitCode){
        synchronized (threads) {
            //终止所有相关的线程 在最后终止当前线程前结束进程
            Thread currentThread = Thread.currentThread();
            if (currentThread == mainThread) {
                //主线程
                for (int i = 0; i < threads.size(); i++) {
                    threads.get(i).stop();
                }
                end(exitCode);
                mainThread.stop();
            } else {
                AbstractThread t;
                for (int i = 0; i < threads.size(); i++) {
                    t = threads.get(i);
                    if (!t.equals(currentThread)) {
                        t.stop();
                    }
                }
                end(exitCode);
                mainThread.stop();
                if (threads.contains(currentThread)) {
                    currentThread.stop();
                }
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
        }finally {
            ret=END;
        }
    }


    @Override
    public void up(InProcess inProcessObject){
        inProcessList.add(inProcessObject);
    }

    @Override
    public void down(InProcess inProcessObject) {
        inProcessList.remove(inProcessObject);
    }

    protected void closeInProcessObject(){
        InProcess inProcess;
        for(int i=0;i<inProcessList.size();){
            inProcess=inProcessList.get(i);
            inProcess.close();
            inProcessList.remove(inProcess);
        }
    }


    @Override
    public int getRunningThreadCount(){
        synchronized (threads) {
            int n = 0;
            AbstractThread thread;
            for (int i = 0; i < threads.size(); i++) {
                thread = threads.get(i);
                if (thread != null && thread.getState() != Thread.State.TERMINATED) {
                    n++;
                }
            }
            return n;
        }
    }

    public boolean isThread(Thread thread) {
        for (int i = 0; i < threads.size(); i++) {
            if(thread== threads.get(i)){
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
            threadList.addAll(this.threads);
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
        threadList.addAll(this.threads);
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

    public AbstractThread<T> getMainThread(){
        return mainThread;
    }

    public List<AbstractThread<T>> getThreads(){
        return threads;
    }
}

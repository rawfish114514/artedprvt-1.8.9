package rawfish.artedprvt.script.mi;

import rawfish.artedprvt.script.MainThread;
import rawfish.artedprvt.script.ScriptProcess;
import rawfish.artedprvt.script.ScriptThread;

/**
 * 此类的生命周期依赖MainThread
 */
public abstract class LifeDepend {
    /**
     * 目标进程
     */
    public ScriptProcess pro;

    /**
     * 添加到主线程中
     */
    public void up(){
        Thread t=Thread.currentThread();
        MainThread main;
        if(t instanceof MainThread){
            main=(MainThread)t;
        }else{
            if(t instanceof ScriptThread){
                main=((ScriptThread)t).getMainThread();
            }else{
                throw new RuntimeException("对象构造不在主线程或子线程");
            }
        }

        pro=main.getProcess();
        main.addld(this);
    }

    /**
     * 主线程结束时被调用
     */
    public void end(){
        terminate();
    }

    /**
     * 主线程结束时被调用
     * 在此处理数据
     */
    public abstract void terminate();
}

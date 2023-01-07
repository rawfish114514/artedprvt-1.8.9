package rawfish.artedprvt.script.mi;

import rawfish.artedprvt.script.MainThread;
import rawfish.artedprvt.script.ScriptThread;

//依赖主线程的生命周期
public abstract class LifeDepend {
    //构造对象时添加到主线程
    public void up(){
        Thread t=Thread.currentThread();
        MainThread main;
        if(t instanceof MainThread){
            main=(MainThread)t;
        }else{
            if(t instanceof ScriptThread){
                main=((ScriptThread)t).getMainThread();
            }else{
                throw new RuntimeException("Not in ScriptProcess");
            }
        }

        main.addld(this);
    }
    //主线程结束时调用
    public void end(){
        terminate();
    }

    public abstract void terminate();
}

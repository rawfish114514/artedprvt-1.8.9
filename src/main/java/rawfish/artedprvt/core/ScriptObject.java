package rawfish.artedprvt.core;

/**
 * 脚本对象
 * 避免在进程结束时继续使用对象资源，如脚本函数实现的接口
 * 尽早调用up()方法使当前脚本进程得到自己的引用
 * 脚本进程在结束时会调用close()方法进而调用onClose()抽象方法
 * up()还返回当前的脚本进程，由于可能返回null（当前线程不是脚本线程）
 * 所以还需要应对获取不到脚本进程的情况
 * 这也意味着close()方法不会被调用
 */
public interface ScriptObject {
    default ScriptProcess up(){
        Thread thread=Thread.currentThread();
        ScriptProcess process=null;
        if(thread instanceof MainThread){
            process=((MainThread) thread).getProcess();
            process.addScriptObject(this);
        }
        if(thread instanceof ScriptThread){
            process=((ScriptThread) thread).getProcess();
            process.addScriptObject(this);
        }
        return process;
    }

    void onClose();

    default void close(){
        onClose();
    }
}

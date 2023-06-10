package rawfish.artedprvt.core;

/**
 * 脚本对象
 * 为了正确的管理脚本运行时产生的java对象
 * 通常是游戏接口相关的对象
 * 它们可能在进程结束后保持活动
 * 这样的类应该实现ScriptObject接口
 * 注册到游戏的
 * 以其他方式被游戏引用的
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

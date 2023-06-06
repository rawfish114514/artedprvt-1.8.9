package rawfish.artedprvt.core;

/**
 * 脚本对象
 * 为了正确的管理脚本运行时产生的java对象
 * 通常是游戏接口相关的对象
 * 它们可能在进程结束后保持活动
 * 这样的类应该实现ScriptObject接口
 * 持有游戏资源的
 * 注册到游戏的
 * 以其他方式被游戏引用的
 * 基本所有和游戏交互相关的类都无法避免
 */
public interface ScriptObject {
    default void up(){
        Thread thread=Thread.currentThread();
        if(thread instanceof MainThread){
            ((MainThread) thread).getProcess().addScriptObject(this);
        }
        if(thread instanceof ScriptThread){
            ((ScriptThread) thread).getProcess().addScriptObject(this);
        }
    }

    void onClose();

    default void close(){
        onClose();
    }
}

package rawfish.artedprvt.core.script;

/**
 * 脚本对象
 * 避免在进程结束时继续使用对象资源
 * 如脚本函数实现的接口
 * 尽早调用up()方法使当前脚本进程得到自己的引用
 * 脚本进程在结束时会调用close()方法
 * up()还返回当前的脚本进程
 * 如果up()返回了null
 * 那么此对象不在脚本进程的环境中
 * close()方法也不会被调用
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

    void close() throws Exception;
}

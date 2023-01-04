package rawfish.artedprvt.script;

/**
 * 脚本线程
 * 受主线程管理
 */
public class ScriptThread extends Thread{
    protected static int n=0;
    protected ScriptProcess pro;
    public ScriptThread(ScriptProcess proIn,Runnable target){
        super(target);
        pro=proIn;
        setName(String.format("%s_%s",Thread.currentThread().getName(),n++));
        pro.tl.add(this);

        setUncaughtExceptionHandler(new ScriptExceptionHandler(pro));
    }
}

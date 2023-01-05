package rawfish.artedprvt.script;

/**
 * 脚本线程
 * 受主线程管理
 */
public class ScriptThread extends Thread{
    //进程的线程创建次数 不包括主线程
    protected static int n=0;
    protected ScriptProcess pro;

    //错误处理 不处理则发生错误直接终止进程
    protected boolean errorHandle;
    public ScriptThread(ScriptProcess proIn,Runnable target){
        super(target);
        pro=proIn;
        errorHandle=pro.eh_value;
        setName(String.format("%s_%s",Thread.currentThread().getName(),n++));
        pro.tl.add(this);

        setUncaughtExceptionHandler(new ScriptExceptionHandler(pro));
    }

    public boolean isErrorHandle(){
        return errorHandle;
    }

    public void setErrorHandle(boolean b){
        errorHandle=b;
    }
}

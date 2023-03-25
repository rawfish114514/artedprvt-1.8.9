package rawfish.artedprvt.script;

/**
 * 脚本线程
 * 受主线程管理
 */
public class ScriptThread extends Thread{
    protected ScriptProcess pro;

    //错误处理 不处理则发生错误直接终止进程
    protected boolean errorHandle;

    protected boolean notStart;
    public ScriptThread(ScriptProcess proIn,Runnable target){
        super(target);
        pro=proIn;
        errorHandle=pro.getValueEh();
        notStart=pro.getValueSt();
        setName(String.format("%s_%s",Thread.currentThread().getName(),pro.tn++));
        pro.tl.add(this);

        setUncaughtExceptionHandler(new ScriptExceptionHandler(pro));
        if(pro.getValuePm()){
            setPriority(Thread.MAX_PRIORITY);
        }
    }
    public MainThread getMainThread(){
        return pro.thread;
    }

    private boolean isr=true;
    @Override
    public void start(){
        if(notStart){
            if(isr) {
                isr=false;
                run();
            }else{
                throw new IllegalThreadStateException();
            }
        }else{
            super.start();
        }
    }

    public boolean isErrorHandle(){
        return errorHandle;
    }

    public void setErrorHandle(boolean b){
        errorHandle=b;
    }
}

package rawfish.artedprvt.core;

public class AbstractExceptionHandler<T extends AbstractProcess<T>> implements Thread.UncaughtExceptionHandler {
    protected T process;

    public AbstractExceptionHandler(T process){
        this.process=process;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}

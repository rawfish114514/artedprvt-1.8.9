package rawfish.artedprvt.core.app.java;

import rawfish.artedprvt.core.AbstractExceptionHandler;
import rawfish.artedprvt.core.Process;

public class JavaExceptionHandler extends AbstractExceptionHandler<JavaProcess> {
    public JavaExceptionHandler(JavaProcess process) {
        super(process);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace(System.err);

        process.logger().error(e.getMessage());
        process.hasError();
        process.stop(Process.ERROR);
    }
}

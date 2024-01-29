package com.artedprvt.core.app.java;

import com.artedprvt.core.AbstractExceptionHandler;
import com.artedprvt.core.Process;

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

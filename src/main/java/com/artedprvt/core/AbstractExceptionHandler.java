package com.artedprvt.core;

public abstract class AbstractExceptionHandler<T extends AbstractProcess<T>> implements Thread.UncaughtExceptionHandler {
    protected T process;

    public AbstractExceptionHandler(T process) {
        this.process = process;
    }
}

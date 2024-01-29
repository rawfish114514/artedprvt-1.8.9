package com.artedprvt.core;

public class AbstractThread<T extends AbstractProcess<T>> extends Thread implements ProcessProvider {
    protected T process;

    public AbstractThread(T process) {
        this.process = process;
    }

    public AbstractThread(T process, Runnable runnable) {
        super(runnable);
        this.process = process;
    }

    @Override
    public Process getProcess() {
        return process;
    }
}

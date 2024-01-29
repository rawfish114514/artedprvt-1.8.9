package com.artedprvt.core;

import com.artedprvt.api.Solvable;

@Solvable
public interface InProcess {
    @Solvable
    default Process up() {
        Thread thread = Thread.currentThread();
        Process process = null;
        if (thread instanceof ProcessProvider) {
            process = ((ProcessProvider) thread).getProcess();
        }
        if (process != null) {
            process.up(this);
        }
        return process;
    }

    @Solvable
    void close();
}

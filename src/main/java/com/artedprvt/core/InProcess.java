package com.artedprvt.core;

import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public interface InProcess extends AutoCloseable {
    @InterfaceView
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

    @InterfaceView
    void close() throws Exception;
}

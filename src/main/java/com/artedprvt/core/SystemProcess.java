package com.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统进程
 * 与普通进程不同
 * 有更特殊的行为
 * 同时也是最快速构造进程的方法
 */
public abstract class SystemProcess extends Process implements Runnable {
    private final Thread thread;
    private List<InProcess> inProcessList;

    public SystemProcess(String name) {
        thread = new SystemProcessThread(name);
        this.name = name;
        inProcessList = new ArrayList<>();
    }

    @Override
    public final ProcessIdLevel pidLevel() {
        return ProcessController.SYSTEM_PROCESS_ID_LEVEL;
    }

    @Override
    public Logger logger() {
        return new Logger.VoidLogger();
    }

    @Override
    public void start() {
        ret = START;
        thread.start();
    }

    public <T extends SystemProcess> T toStart() {
        start();
        return (T) this;
    }

    @Override
    public void stop(int exitCode) {
        ret = STOP;
        thread.stop();
        end(exitCode);
    }

    @Override
    public void end(int exitCode) {
        if (ret == END) {
            return;
        }
        try {
            closeInProcessObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ret = END;
    }

    @Override
    public void up(InProcess inProcessObject) {
        inProcessList.add(inProcessObject);
    }

    @Override
    public void down(InProcess inProcessObject) {
        inProcessList.remove(inProcessObject);
    }

    private void closeInProcessObject() {
        InProcess inProcess;
        for (int i = 0; i < inProcessList.size(); ) {
            inProcess = inProcessList.get(i);
            try {
                inProcess.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            inProcessList.remove(inProcess);
        }
    }

    /**
     * 最后执行
     * 实际线程在finally块内调用此方法保证这个方法一定有机会执行
     */
    public void fil() {

    }

    private class SystemProcessThread extends Thread implements ProcessProvider {
        public SystemProcessThread(String name) {
            setName(name);
        }

        @Override
        public void run() {
            try {
                SystemProcess.this.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    fil();
                } catch (Throwable ignore) {

                }
            }
            end(0);
        }

        @Override
        public Process getProcess() {
            return SystemProcess.this;
        }
    }
}

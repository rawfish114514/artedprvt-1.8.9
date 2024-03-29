package com.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

public class ProcessController extends SystemProcess {
    public ProcessController() {
        super("ProcessController");
    }

    @Override
    public void run() {
        while (true) {
            //清除死进程
            for (int i = 0; i < processes.length; i++) {
                if (processes[i] != null && processes[i].getRet() == Process.END) {
                    processes[i] = null;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 强制注册
     *
     * @return
     */
    @Override
    protected int register() {
        initProcesses();
        processes[768] = this;
        return 768;
    }

    private void initProcesses() {
        if (processes == null) {
            processes = new Process[1024];
        }
    }

    private Process[] processes;

    private static final ProcessIdLevel ALL = new ProcessIdLevel(0, 1023);

    public static final ProcessIdLevel SYSTEM_PROCESS_ID_LEVEL = new ProcessIdLevel(768, 1023);

    public static final ProcessIdLevel APP_PROCESS_ID_LEVEL = new ProcessIdLevel(0, 767);

    /**
     * 注册进程并获取PID
     *
     * @param process
     * @return [0, 1023]注册成功 -1注册失败
     */
    public synchronized int registerProcess(Process process) {
        return registerProcess(process, ALL);
    }

    /**
     * 注册进程并获取PID
     *
     * @param process
     * @param processIdLevel
     * @return [0, 1023]注册成功 -1注册失败
     */
    public synchronized int registerProcess(Process process, ProcessIdLevel processIdLevel) {
        if (!ALL.contain(processIdLevel)) {
            return -1;
        }
        for (int i = processIdLevel.START; i <= processIdLevel.END; i++) {
            if (processes[i] == null) {
                processes[i] = process;
                return i;
            }
        }
        return -1;
    }

    public List<? extends Process> getProcessList() {
        List<Process> processList = new ArrayList<>();
        for (int i = 0; i < processes.length; i++) {
            if (processes[i] != null) {
                processList.add(processes[i]);
            }
        }
        return processList;
    }

    public <T extends Process> List<T> getProcessList(Class<T> clas) {
        List<T> processList = new ArrayList<>();
        for (int i = 0; i < processes.length; i++) {
            if (processes[i] != null && clas.isAssignableFrom(processes[i].getClass())) {
                processList.add((T) processes[i]);
            }
        }
        return processList;
    }
}

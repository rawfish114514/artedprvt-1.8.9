package rawfish.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

public class ProcessController {
    private static Thread thread=null;

    public static Thread getThread(){
        return thread;
    }

    public static synchronized void init(){
        if(thread==null){
            thread=new Thread(ProcessController::control);
            thread.setName("ProcessControllerThread");
            thread.start();
        }
    }

    private static void control(){
        while(true){
            for (int i = 0; i < processes.length; i++) {
                if(processes[i]!=null&&processes[i].getRet()== Process.END){
                    processes[i]=null;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final Process[] processes=new Process[256];

    /**
     * 注册进程并获取PID
     * @param process
     * @return [0,255]注册成功 -1注册失败
     */
    public static synchronized int registerProcess(Process process){
        for (int i = 0; i < processes.length; i++) {
            if(processes[i]==null){
                processes[i]=process;
                return i;
            }
        }
        return -1;
    }

    public static List<? extends Process> getProcessList(){
        List<Process> processList=new ArrayList<>();
        for (int i = 0; i < processes.length; i++) {
            if(processes[i]!=null){
                processList.add(processes[i]);
            }
        }
        return processList;
    }
}

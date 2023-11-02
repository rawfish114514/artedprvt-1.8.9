package rawfish.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

public class ProcessController extends SystemProcess{
    public ProcessController() {
        super("ProcessController");
    }

    @Override
    public void run() {
        while(true){
            //清除死进程
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

    /**
     * 强制注册
     * @return
     */
    @Override
    protected int register(){
        initProcesses();
        processes[3072]=this;
        return 3072;
    }

    private void initProcesses(){
        if(processes==null){
            processes=new Process[4096];
        }
    }

    private Process[] processes;

    private final ProcessIdLevel ALL=new ProcessIdLevel(0,4095);

    /**
     * 注册进程并获取PID
     * @param process
     * @return [0,4095]注册成功 -1注册失败
     */
    public synchronized int registerProcess(Process process){
        return registerProcess(process,ALL);
    }
    /**
     * 注册进程并获取PID
     * @param process
     * @param processIdLevel
     * @return [0,4095]注册成功 -1注册失败
     */
    public synchronized int registerProcess(Process process,ProcessIdLevel processIdLevel){
        if(!ALL.contain(processIdLevel)){
            return -1;
        }
        for (int i = processIdLevel.START; i <= processIdLevel.END; i++) {
            if(processes[i]==null){
                processes[i]=process;
                return i;
            }
        }
        return -1;
    }

    public List<? extends Process> getProcessList(){
        List<Process> processList=new ArrayList<>();
        for (int i = 0; i < processes.length; i++) {
            if(processes[i]!=null){
                processList.add(processes[i]);
            }
        }
        return processList;
    }
}

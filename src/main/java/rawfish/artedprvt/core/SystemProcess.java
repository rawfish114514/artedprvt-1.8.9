package rawfish.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

public abstract class SystemProcess extends Process implements Runnable{
    private final Thread thread;
    private List<InProcess> inProcessList;
    public SystemProcess(String name){
        thread=new SystemProcessThread(name);
        this.name=name;
        inProcessList=new ArrayList<>();
    }

    @Override
    public ProcessIdLevel pidLevel() {
        return ProcessController.SYSTEM_PROCESS_ID_LEVEL;
    }

    @Override
    public Logger logger(){
        return new Logger.VoidLogger();
    }

    @Override
    public void start() {
        ret=START;
        thread.start();
    }

    public <T extends SystemProcess> T toStart(){
        start();
        return (T)this;
    }

    @Override
    public void stop(int exitCode) {
        ret=STOP;
        thread.stop();
        end(exitCode);
    }

    @Override
    public void end(int exitCode) {
        if(ret==END){
            return;
        }
        try {
            closeInProcessObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ret=END;
    }

    @Override
    public void up(InProcess inProcessObject){
        inProcessList.add(inProcessObject);
    }

    public void down(InProcess inProcessObject){
        inProcessList.remove(inProcessObject);
    }

    private void closeInProcessObject(){
        InProcess inProcess;
        for(int i=0;i<inProcessList.size();){
            inProcess=inProcessList.get(i);
            inProcess.close();
            inProcessList.remove(inProcess);
        }
    }

    public void fil(){

    }

    private class SystemProcessThread extends Thread implements ProcessThread{
        public SystemProcessThread(String name){
            setName(name);
        }

        @Override
        public void run(){
            try {
                SystemProcess.this.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    fil();
                }catch (Throwable ignore){

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

package rawfish.artedprvt.core;

public abstract class SystemProcess extends Process implements Runnable{
    public static final ProcessIdLevel SYSTEM_PROCESS_ID_LEVEL=new ProcessIdLevel(3072,4095);

    private final Thread thread;
    public SystemProcess(String name){
        thread=new SystemProcessThread(name);
        this.name=name;
    }

    @Override
    public ProcessIdLevel pidLevel() {
        return SYSTEM_PROCESS_ID_LEVEL;
    }

    @Override
    public Logger logger(){
        return new Logger.VoidLogger();
    }

    @Override
    public void start() {
        thread.start();
    }

    public <T extends SystemProcess> T toStart(){
        start();
        return (T)this;
    }

    @Override
    public void stop(int exitCode) {
        thread.stop();
    }

    @Override
    public void end(int exitCode) {
        //pass
    }

    @Override
    public void up(InProcess inProcessObject){
        //pass
    }

    public void down(InProcess inProcessObject){
        //pass
    }

    private class SystemProcessThread extends Thread implements ProcessThread{
        public SystemProcessThread(String name){
            setName(name);
        }

        @Override
        public void run(){
            SystemProcess.this.run();
            end(0);
        }

        @Override
        public Process getProcess() {
            return SystemProcess.this;
        }
    }
}

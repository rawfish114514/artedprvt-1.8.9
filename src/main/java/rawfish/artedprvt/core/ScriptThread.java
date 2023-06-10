package rawfish.artedprvt.core;

import java.util.List;

public class ScriptThread extends Thread {
    private ScriptProcess process;

    public ScriptThread(ScriptProcess process, Runnable target) {
        super(target);
        this.process = process;
        List<ScriptThread> threads = process.getThreads();
        setName("Script-" + threads.size());
        setUncaughtExceptionHandler(process.getExceptionHandler());

        threads.add(this);
    }

    public ScriptProcess getProcess() {
        return process;
    }
}

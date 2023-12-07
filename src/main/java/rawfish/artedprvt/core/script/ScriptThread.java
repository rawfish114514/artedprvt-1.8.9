package rawfish.artedprvt.core.script;

import rawfish.artedprvt.core.ProcessThread;

import java.util.List;

public class ScriptThread extends Thread implements ProcessThread {
    private ScriptProcess process;

    public ScriptThread(ScriptProcess process, Runnable target) {
        super(target);
        this.process = process;
        List<ScriptThread> threads = process.getThreads();
        setName("Script-" + threads.size());
        setUncaughtExceptionHandler(process.getExceptionHandler());

        threads.add(this);
    }

    @Override
    public ScriptProcess getProcess() {
        return process;
    }
}

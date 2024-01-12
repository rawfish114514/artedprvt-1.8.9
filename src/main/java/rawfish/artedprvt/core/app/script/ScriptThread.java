package rawfish.artedprvt.core.app.script;

import rawfish.artedprvt.core.AbstractThread;
import rawfish.artedprvt.core.ProcessProvider;

import java.util.List;

public class ScriptThread extends AbstractThread<ScriptProcess> implements ProcessProvider {

    public ScriptThread(ScriptProcess process, Runnable target) {
        super(process,target);
        List<AbstractThread<ScriptProcess>> threads = process.getThreads();
        setName("Script-" + threads.size());
        setUncaughtExceptionHandler(process.getExceptionHandler());
    }

    @Override
    public ScriptProcess getProcess() {
        return process;
    }
}

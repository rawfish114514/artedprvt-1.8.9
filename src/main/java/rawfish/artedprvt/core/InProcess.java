package rawfish.artedprvt.core;

import rawfish.artedprvt.api.Solvable;

@Solvable
public interface InProcess {
    @Solvable
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

    @Solvable
    void close();
}

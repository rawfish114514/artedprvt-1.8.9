package rawfish.artedprvt.command;

import rawfish.artedprvt.core.SystemProcess;
import rawfish.artedprvt.std.cli.Command;

public abstract class TaskProcess extends SystemProcess {
    public TaskProcess(Command command) {
        super(command.getName());
        start();
    }
}

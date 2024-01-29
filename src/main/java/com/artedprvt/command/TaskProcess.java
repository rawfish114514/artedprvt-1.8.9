package com.artedprvt.command;

import com.artedprvt.core.SystemProcess;
import com.artedprvt.std.cli.Command;

public abstract class TaskProcess extends SystemProcess {
    public TaskProcess(Command command) {
        super(command.getName());
        start();
    }
}

package com.artedprvt.command;

import com.artedprvt.std.cli.Command;
import com.artedprvt.std.cli.Messager;

import java.util.List;

public abstract class BaseCommand extends Command {
    public BaseCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, Messager messager) {
        process(args, new FormatMessager(messager, getName()));
    }

    public abstract void process(List<String> args, FormatMessager messager);
}

package com.artedprvt.work;

import com.artedprvt.std.cli.CompleteInterface;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.FormatInterface;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.InfoInterface;
import com.artedprvt.std.cli.Messager;
import com.artedprvt.std.cli.ProcessInterface;
import com.artedprvt.std.cli.util.Literals;

import java.util.List;

public class CommandHandle implements ProcessInterface, CompleteInterface, FormatInterface, InfoInterface {
    private ProcessInterface processInterface;
    private CompleteInterface completeInterface;
    private FormatInterface formatInterface;
    private InfoInterface infoInterface;

    public CommandHandle(String commandName, ProcessInterface processInterface, CompleteInterface completeInterface, FormatInterface formatInterface, InfoInterface infoInterface) {
        this.processInterface = processInterface;
        this.completeInterface = completeInterface;
        this.formatInterface = formatInterface;
        this.infoInterface = infoInterface;
    }

    @Override
    public void process(List<String> args, Messager messager) {
        if (processInterface == null) {
            return;
        }
        processInterface.process(args, messager);
    }

    @Override
    public List<String> complete(List<String> args) {
        if (completeInterface == null) {
            return Literals.emptyComplete();
        }
        return completeInterface.complete(args);
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        if (formatInterface == null) {
            return Literals.emptyFormat();
        }
        return formatInterface.format(args);
    }

    @Override
    public InfoHandler info(List<String> args) {
        if (infoInterface == null) {
            return Literals.emptyInfo();
        }
        return infoInterface.info(args);
    }
}

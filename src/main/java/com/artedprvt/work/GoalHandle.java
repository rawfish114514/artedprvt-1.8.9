package com.artedprvt.work;

import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.Messager;
import com.artedprvt.std.cli.ProcessInterface;

import java.util.List;

public class GoalHandle implements ProcessInterface, InfoHandler {
    private ProcessInterface processInterface;
    private InfoHandler infoHandler;

    public GoalHandle(String goalName, ProcessInterface processInterface, InfoHandler infoHandler) {
        this.processInterface = processInterface;
        this.infoHandler = infoHandler;
    }

    @Override
    public void process(List<String> args, Messager messager) {
        processInterface.process(args, messager);
    }

    @Override
    public String handleInfo(String source) {
        if (infoHandler == null) {
            return "";
        }
        return infoHandler.handleInfo(source);
    }
}

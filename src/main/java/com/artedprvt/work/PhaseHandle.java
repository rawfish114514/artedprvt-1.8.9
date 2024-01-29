package com.artedprvt.work;

import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.Messager;
import com.artedprvt.std.cli.ProcessInterface;

import java.util.List;

public class PhaseHandle implements ProcessInterface, InfoHandler {
    private List<GoalHandle> goals;
    private InfoHandler infoHandler;

    public PhaseHandle(String phaseName, List<GoalHandle> goals, InfoHandler infoHandler) {
        this.goals = goals;
        this.infoHandler = infoHandler;
    }

    @Override
    public void process(List<String> args, Messager messager) {
        goals.forEach(goal -> goal.process(args, messager));
    }

    @Override
    public String handleInfo(String source) {
        if (infoHandler == null) {
            return "";
        }
        return infoHandler.handleInfo(source);
    }
}

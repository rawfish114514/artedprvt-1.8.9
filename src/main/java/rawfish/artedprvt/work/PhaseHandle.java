package rawfish.artedprvt.work;

import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.CompleteInterface;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.FormatInterface;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.InfoInterface;
import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.cli.ProcessInterface;
import rawfish.artedprvt.std.cli.util.Literals;

import java.util.List;

public class PhaseHandle implements ProcessInterface,InfoHandler {
    private List<GoalHandle> goals;
    private InfoHandler infoHandler;

    public PhaseHandle(String phaseName, List<GoalHandle> goals,InfoHandler infoHandler) {
        this.goals = goals;
        this.infoHandler = infoHandler;
    }

    @Override
    public void process(List<String> args, Messager messager) {
        goals.forEach(goal -> goal.process(args, messager));
    }

    @Override
    public String handleInfo(String source) {
        if(infoHandler==null){
            return "";
        }
        return infoHandler.handleInfo(source);
    }
}

package rawfish.artedprvt.work;

import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.CompleteInterface;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.FormatInterface;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.InfoInterface;
import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.cli.util.Literals;

import java.util.List;

public class PhaseHandle extends Command {
    private CompleteInterface complete;
    private FormatInterface format;
    private InfoInterface info;
    private List<GoalHandle> goals;

    public PhaseHandle(String commandName, CompleteInterface complete, FormatInterface format, InfoInterface info, List<GoalHandle> goals) {
        super(commandName);
        this.complete = complete;
        this.format = format;
        this.info = info;
        this.goals = goals;
    }

    @Override
    public void process(List<String> args, Messager messager){
        goals.forEach(goal->goal.process(args,messager));
    }

    @Override
    public List<String> complete(List<String> args) {
        if(complete==null) {
            return Literals.emptyComplete();
        }
        return complete.complete(args);
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        if(format==null) {
            return Literals.emptyFormat();
        }
        return format.format(args);
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(info==null){
            return Literals.emptyInfo();
        }
        return info.info(args);
    }
}

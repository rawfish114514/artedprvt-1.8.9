package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.BaseCommand;
import rawfish.artedprvt.command.FormatMessager;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.work.PhaseHandle;
import rawfish.artedprvt.work.Project;
import rawfish.artedprvt.work.WorkRuntime;

import java.util.ArrayList;
import java.util.List;

public class CommandWork extends BaseCommand {
    public CommandWork(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        Project project= Project.project;
        if(project!=null){
            if(project.isLoaded()){
                WorkRuntime workRuntime=project.getRuntime();
                if(args.size()==1){
                    String arg0=args.get(0);
                    List<String> phases=workRuntime.getPhases();
                    List<String> goals=workRuntime.getGoals();
                    if(phases.contains(arg0)){
                        List<PhaseHandle> phaseHandleList=workRuntime.getPhaseHandle(arg0);
                        for(int i=0;i<phaseHandleList.size();i++){
                            phaseHandleList.get(i).process(new ArrayList<>(),messager);
                        }
                    }else{
                        if(goals.contains(arg0)){
                            workRuntime.getGoalHandle(arg0).process(new ArrayList<>(),messager);
                        }else {
                            messager.red("未知 phase 或 goal");
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        Project project= Project.project;
        if(project!=null) {
            if (project.isLoaded()) {
                WorkRuntime workRuntime = project.getRuntime();
                List<String> list=new ArrayList<>();
                list.addAll(workRuntime.getPhases());
                list.addAll(workRuntime.getGoals());

                return list;
            }
        }

        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.emptyFormat();
    }

    @Override
    public InfoHandler info(List<String> args) {
        return Literals.emptyInfo();
    }
}

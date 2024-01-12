package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.BaseCommand;
import rawfish.artedprvt.command.FormatMessager;
import rawfish.artedprvt.command.TaskProcess;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.FormatHandlerListBuilder;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.work.PhaseHandle;
import rawfish.artedprvt.work.Project;
import rawfish.artedprvt.work.WorkRuntime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandWork extends BaseCommand {
    public CommandWork(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        new TaskProcess(this) {
            @Override
            public void run() {
                if (Project.lock.tryLock()) {
                    Project project = Project.project;
                    boolean isOpen = project != null;

                    if (isOpen) {
                        if (project.isLoaded()) {
                            WorkRuntime workRuntime = project.getRuntime();
                            if (args.size() > 0) {
                                String arg0 = args.get(0);
                                List<String> phases = workRuntime.getPhases();
                                List<String> goals = workRuntime.getGoals();
                                List<String> commands = workRuntime.getCommands();

                                boolean existPhaseOrGoal = false;
                                boolean existCommand = false;

                                if (phases.contains(arg0) || goals.contains(arg0)) {
                                    existPhaseOrGoal = true;
                                }
                                if (commands.contains(arg0)) {
                                    existCommand = true;
                                }

                                if (existPhaseOrGoal) {
                                    List<String> processed = new ArrayList<>();
                                    for (int i = 0; i < args.size(); i++) {
                                        String arg = args.get(i);
                                        if (phases.contains(arg)) {
                                            processed.add(arg);
                                        } else {
                                            if (goals.contains(arg)) {
                                                processed.add(arg);
                                            } else {
                                                messager.red("找不到 phase 或 goal: " + arg);
                                                return;
                                            }
                                        }
                                    }

                                    for (String p : processed) {
                                        if (phases.contains(p)) {
                                            List<PhaseHandle> phaseHandleList = workRuntime.getPhaseHandles(p);
                                            for (int j = 0; j < phaseHandleList.size(); j++) {
                                                phaseHandleList.get(j).process(new ArrayList<>(), messager);
                                            }
                                            continue;
                                        }
                                        if (goals.contains(p)) {
                                            workRuntime.getGoalHandle(p).process(new ArrayList<>(), messager);
                                        }
                                    }
                                } else {
                                    if (existCommand) {
                                        workRuntime.getCommandHandle(arg0).process(args.subList(1, args.size()), messager);
                                    }
                                }

                                if (existPhaseOrGoal || existCommand) {

                                } else {
                                    messager.red("找不到 phase 或 goal 或 command: " + arg0);
                                }
                            }
                        } else {
                            messager.red("未加载项目");
                        }
                    } else {
                        messager.red("未打开项目");
                    }
                } else {
                    messager.red("有项目相关的任务正在执行");
                }
            }

            @Override
            public void fil() {
                Project.lock.unlock();
            }
        };
    }

    @Override
    public List<String> complete(List<String> args) {
        Project project = Project.project;
        if (project != null) {
            if (project.isLoaded()) {
                String arg0 = args.get(0);
                WorkRuntime workRuntime = project.getRuntime();
                List<String> list = new ArrayList<>();
                List<String> commandList = workRuntime.getCommands();
                list.addAll(workRuntime.getPhases());
                list.addAll(workRuntime.getGoals());

                if (list.contains(arg0)) {
                    return list;
                }
                if (commandList.contains(arg0)) {
                    return workRuntime.getCommandHandle(arg0).complete(args.subList(1, args.size()));
                }

                list.addAll(commandList);
                return list;
            }
        }

        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        Project project = Project.project;
        if (project != null) {
            if (project.isLoaded()) {
                WorkRuntime workRuntime = project.getRuntime();
                if (args.size() > 0) {
                    String arg0 = args.get(0);
                    List<String> phases = workRuntime.getPhases();
                    List<String> goals = workRuntime.getGoals();
                    List<String> commands = workRuntime.getCommands();

                    boolean existPhaseOrGoal = false;
                    boolean existCommand = false;

                    if (phases.contains(arg0) || goals.contains(arg0)) {
                        existPhaseOrGoal = true;
                    }
                    if (commands.contains(arg0)) {
                        existCommand = true;
                    }

                    FormatHandlerListBuilder builder = Literals.formatListBuilder();

                    if (existPhaseOrGoal) {
                        for (int i = 0; i < args.size(); i++) {
                            if (phases.contains(args.get(i))) {
                                builder.add(phaseFormatHandler);
                                continue;
                            }
                            if (goals.contains(args.get(i))) {
                                builder.add(goalFormatHandler);
                                continue;
                            }
                            builder.empty();
                        }
                    } else {
                        if (existCommand) {
                            builder.add(commandFormatHandler);
                            builder.addAll(workRuntime.getCommandHandle(arg0).format(args.subList(1, args.size())));
                        }
                    }

                    return builder;
                }
            }
        }
        return Literals.emptyFormat();
    }

    @Override
    public InfoHandler info(List<String> args) {
        Project project = Project.project;
        if (project != null) {
            if (project.isLoaded()) {
                if (args.size() == 0) {
                    return Literals.infoFactory().string("项目构建工具 §d[" + project.getDir() + "]");
                }
                WorkRuntime workRuntime = project.getRuntime();
                String arg0 = args.get(0);
                List<String> phases = workRuntime.getPhases();
                List<String> goals = workRuntime.getGoals();
                List<String> commands = workRuntime.getCommands();

                boolean existPhaseOrGoal = false;
                boolean existCommand = false;

                if (phases.contains(arg0) || goals.contains(arg0)) {
                    existPhaseOrGoal = true;
                }
                if (commands.contains(arg0)) {
                    existCommand = true;
                }

                if (existPhaseOrGoal) {
                    String lastArg = args.get(args.size() - 1);
                    if (phases.contains(lastArg)) {
                        return Literals.infoFactory().string("" + workRuntime.getPhaseHandle(lastArg).handleInfo(lastArg));
                    } else {
                        if (goals.contains(lastArg)) {
                            return workRuntime.getGoalHandle(lastArg);
                        }
                    }
                } else {
                    if (existCommand) {
                        return workRuntime.getCommandHandle(arg0).info(args.subList(1, args.size()));
                    }
                }
            }
        }
        if (args.size() == 0) {
            return Literals.infoFactory().string("项目构建工具 §c未加载项目");
        }
        return Literals.emptyInfo();
    }

    public FormatHandler phaseFormatHandler = Literals.formatFactory().regex(
            Pattern.compile("(?<phase>[a-z]*(_[a-z]*)*)"),
            "§?phase",
            Literals.formatMapBuilder()
                    .puts("phase", Literals.formatFactory().append("a")),
            Literals.formatFactory().append("4")
    );

    public FormatHandler goalFormatHandler = Literals.formatFactory().regex(
            Pattern.compile("(?<phase>[a-z]*(_[a-z]*)*)(?<pro>:)(?<goal>[a-z]*(_[a-z]*)*)"),
            "§?phase§?pro§?goal",
            Literals.formatMapBuilder()
                    .puts("phase", Literals.formatFactory().append("a"))
                    .puts("pro", Literals.formatFactory().append("7"))
                    .puts("goal", Literals.formatFactory().append("b")),
            Literals.formatFactory().append("4")
    );

    public FormatHandler commandFormatHandler = Literals.formatFactory().regex(
            Pattern.compile("(?<command>[a-z]*(_[a-z]*)*)"),
            "§?command",
            Literals.formatMapBuilder()
                    .puts("command", Literals.formatFactory().append("d")),
            Literals.formatFactory().append("4")
    );
}

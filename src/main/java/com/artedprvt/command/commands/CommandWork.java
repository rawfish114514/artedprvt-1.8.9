package com.artedprvt.command.commands;

import com.artedprvt.command.TaskProcess;
import com.artedprvt.core.localization.types.CIS;
import com.artedprvt.core.localization.types.CMS;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.std.text.Formatting;
import com.artedprvt.work.Project;
import com.artedprvt.command.BaseCommand;
import com.artedprvt.command.FormatMessager;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.util.FormatHandlerListBuilder;
import com.artedprvt.work.PhaseHandle;
import com.artedprvt.work.WorkRuntime;

import java.text.MessageFormat;
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
                                                messager.red(MessageFormat.format(CMS.cms41,arg));
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
                                    messager.red(MessageFormat.format(CMS.cms42,arg0));
                                }
                            }
                        } else {
                            messager.red(CMS.cms9);
                        }
                    } else {
                        messager.red(CMS.cms6);
                    }
                } else {
                    messager.red(CMS.cms39);
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
                    return Literals.infoFactory().string(MessageFormat.format(CIS.cis19, Formatting.LIGHT_PURPLE,project.getDir()));
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
            return Literals.infoFactory().string(MessageFormat.format(CIS.cis6,Formatting.RED,CIS.cis20));
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

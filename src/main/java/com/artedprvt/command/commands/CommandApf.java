package com.artedprvt.command.commands;

import com.artedprvt.core.localization.types.CMS;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.std.text.Formatting;
import com.artedprvt.core.localization.types.CIS;
import com.artedprvt.std.cli.Command;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.Messager;
import com.artedprvt.std.cli.format.FormatHandlerAppend;
import com.artedprvt.std.cli.util.FormatHandlerListBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 命令集合
 * 可执行所有这个模组定义的命令 防止命名冲突
 */
public class CommandApf extends Command {
    public List<Command> commandList;

    public CommandApf(String commandName) {
        super(commandName);
        commandList = new ArrayList<>();

        commandList.add(new CommandApp("app"));
        commandList.add(new CommandProject("project"));
        commandList.add(new CommandPros("pros"));
        commandList.add(new CommandStops("stops"));
        commandList.add(new CommandWork("work"));
    }

    @Override
    public void process(List<String> args, Messager messager) {
        if (args.size() < 1) {
            messager.send(Formatting.DARK_RED + getName() + CMS.cms0);
            return;
        }
        for (Command command : commandList) {
            if (command.getName().equals(args.get(0))) {
                command.process(args.subList(1, args.size()), messager);
                return;
            }
        }
        messager.send(Formatting.DARK_RED + getName() + MessageFormat.format(CMS.cms1, args.get(0)));
    }

    @Override
    public List<String> complete(List<String> args) {
        if (args.size() == 1) {
            List<String> cl = new ArrayList<>();
            String name;
            for (Command command : commandList) {
                name = command.getName();
                if (name.startsWith(args.get(0))) {
                    cl.add(name);
                }
            }
            return cl;
        }
        //补全子命令参数
        Command c = null;
        for (Command command : commandList) {
            if (command.getName().equals(args.get(0))) {
                c = command;
            }
        }
        if (c == null) {
            return Literals.emptyComplete();
        }
        return c.complete(args.subList(1, args.size()));
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        Command c = null;
        for (Command command : commandList) {
            if (command.getName().equals(args.get(0))) {
                c = command;
            }
        }
        if (c == null) {
            return Literals.formatListBuilder().append("c");
        }
        FormatHandlerListBuilder fl = Literals.formatListBuilder();
        fl.add(new FormatHandlerAppend("bo"));
        List<String> sargs = args.subList(1, args.size());
        if (sargs.size() > 0) {
            fl.addAll(c.format(sargs));
        }
        return fl;
    }

    @Override
    public InfoHandler info(List<String> args) {
        if (args.size() == 0) {
            return Literals.infoFactory().string(CIS.cis0);
        }
        if (args.size() == 1 && args.get(0).isEmpty()) {
            return Literals.infoFactory().string(CIS.cis1);
        }
        Command c = null;
        for (Command command : commandList) {
            if (command.getName().equals(args.get(0))) {
                c = command;
            }
        }
        if (c == null) {
            return Literals.infoFactory().string(CIS.cis2);
        }
        return c.info(args.subList(1, args.size()));
    }
}

package com.artedprvt.common;

import com.artedprvt.std.cli.Command;
import com.artedprvt.std.cli.Messager;
import com.artedprvt.std.cli.util.Literals;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandAdapter extends CommandBase {
    private final Command command;

    public CommandAdapter(Command command) {
        this.command = command;
    }

    @Override
    public String getCommandName() {
        return command.getName();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands." + command.getName() + ".usage";
    }


    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        //去空参数
        List<String> slist = new ArrayList<>();
        for (String arg : args) {
            if (!arg.equals("")) {
                slist.add(arg);
            }
        }
        try {
            command.process(slist, messager);
        } catch (Throwable throwable) {
            throw new CommandException(throwable.getMessage());
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (!(sender.getEntityWorld() instanceof WorldServer)) {
            List<String> argList = Arrays.asList(args);
            return startWith(argList, command.complete(argList));
        }
        return Literals.emptyComplete();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }


    public List<String> startWith(List<String> args, List<String> cs) {
        List<String> ns = cs.stream().filter(v -> v.startsWith(args.get(args.size() - 1))).collect(Collectors.toList());
        if (ns.size() > 0) {
            return ns;
        }
        return cs;
    }

    private static Messager messager = new Messager0();

    static class Messager0 implements Messager {
        @Override
        public void send(String message) {
            System.out.println(Literals.fcClear(message));
        }

        @Override
        public void send(String message, String hover) {
            System.out.println(Literals.fcClear(message) + "\n\n" + Literals.fcClear(hover));
        }

        @Override
        public boolean canHover() {
            return false;
        }

        @Override
        public int dialog(String message, String... buttons) {
            send(message);
            send(String.join(" ", buttons));
            return -1;
        }

        @Override
        public boolean canDialog() {
            return false;
        }
    }
}

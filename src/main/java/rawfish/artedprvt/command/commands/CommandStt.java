package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.std.text.Formatting;

import java.util.Arrays;
import java.util.List;

/**
 * 从src目录创建脚本进程并指定main模块
 */
public class CommandStt extends Command {
    public CommandScript commandScript;

    public CommandStt(String commandName) {
        super(commandName);
        commandScript = new CommandScript(commandName);
    }

    @Override
    public void process(List<String> args, Messager messager) {
        if (args.size() > 0) {
            messager.send(Formatting.DARK_RED + getName() + CMS.cms0);
            return;
        }
        commandScript.process(Arrays.asList("main"), messager);
    }

    @Override
    public List<String> complete(List<String> args) {
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.emptyFormat();
    }

    @Override
    public InfoHandler info(List<String> args) {
        if (args.size() > 0 && (!args.get(0).isEmpty())) {
            return Literals.infoFactory().string(CIS.cis3);
        }
        return Literals.emptyInfo();
    }
}

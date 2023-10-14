package rawfish.artedprvt.command.commandimpls;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.command.Formatter;

import java.util.Arrays;
import java.util.List;

/**
 * 从src目录创建脚本进程并指定main模块
 */
public class CommandStt extends Command {
    public CommandScript commandScript;
    public CommandStt(String commandName) {
        super(commandName);
        commandScript=new CommandScript(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()>0){
            CommandMessages.exception(getName(),"cms0");
            return;
        }
        commandScript.process(Arrays.asList("main"));
    }

    @Override
    public List<String> complete(List<String> args) {
        return getEmptyStringList();
    }

    @Override
    public List<? extends Formatter> format(List<String> args) {
        return getEmptyFormatterList();
    }

    @Override
    public String info(List<String> args) {
        if(args.size()>0&&(!args.get(0).isEmpty())){
            return CommandMessages.translate("cis3");
        }
        return getEmptyString();
    }
}

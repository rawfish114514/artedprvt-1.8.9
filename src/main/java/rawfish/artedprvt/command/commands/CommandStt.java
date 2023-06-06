package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;

import java.util.Arrays;
import java.util.List;

public class CommandStt extends Command {
    public CommandScript commandScript;
    public CommandStt(String commandName) {
        super(commandName);
        commandScript=new CommandScript(null);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()>0){
            CommandMessages.exception(getCommandName(),"不能有参数");
            return;
        }
        commandScript.process(Arrays.asList("main"));
    }

    @Override
    public List<String> tab(List<String> args) {
        return nullTab;
    }
}

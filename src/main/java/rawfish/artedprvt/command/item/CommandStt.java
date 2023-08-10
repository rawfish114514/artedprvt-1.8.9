package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;

import java.util.Arrays;
import java.util.List;

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
        return nullTab;
    }
}

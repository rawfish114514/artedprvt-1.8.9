package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ScriptSystem;

import java.util.List;

public class CommandAd extends Command {
    public CommandAd(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()>0){
            CommandMessages.exception(getName(),"cms0");
            return;
        }
        ScriptSystem.B_DEBUG =!ScriptSystem.B_DEBUG;
        CommandMessages.key(getName(),"cms13",ScriptSystem.B_DEBUG);
    }

    @Override
    public List<String> complete(List<String> args) {
        return nullTab;
    }
}

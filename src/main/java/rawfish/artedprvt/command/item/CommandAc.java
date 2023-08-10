package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ScriptSystem;

import java.util.List;

public class CommandAc extends Command {
    public CommandAc(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()>0){
            CommandMessages.exception(getName(),"cms0");
            return;
        }
        ScriptSystem.B_CHAT=!ScriptSystem.B_CHAT;
        CommandMessages.key(getName(),"cms12",ScriptSystem.B_CHAT);
    }

    @Override
    public List<String> complete(List<String> args) {
        return nullTab;
    }
}

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
            CommandMessages.exception(getCommandName(),"不能有参数");
            return;
        }
        ScriptSystem.B_CHAT=!ScriptSystem.B_CHAT;
        CommandMessages.key(getCommandName(),"切换chat状态到: "+ScriptSystem.B_CHAT);
    }

    @Override
    public List<String> tab(List<String> args) {
        return nullTab;
    }
}

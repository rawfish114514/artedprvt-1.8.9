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
            CommandMessages.exception(getCommandName(),"不能有参数");
            return;
        }
        ScriptSystem.B_DEBUG =!ScriptSystem.B_DEBUG;
        CommandMessages.key(getCommandName(),"切换debug状态到: "+ScriptSystem.B_DEBUG);
    }

    @Override
    public List<String> tab(List<String> args) {
        return nullTab;
    }
}

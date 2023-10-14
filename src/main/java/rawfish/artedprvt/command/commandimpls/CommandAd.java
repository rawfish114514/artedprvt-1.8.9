package rawfish.artedprvt.command.commandimpls;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.command.Formatter;
import rawfish.artedprvt.core.ScriptSystem;

import java.util.List;

/**
 * 切换DEBUG状态 true/false
 */
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

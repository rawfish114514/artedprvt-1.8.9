package rawfish.artedprvt.command.commandimpls;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.command.Formatter;
import rawfish.artedprvt.core.ScriptSystem;

import java.util.List;

/**
 * 切换CHAT状态 true/false
 */
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
        System.out.println(args);
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

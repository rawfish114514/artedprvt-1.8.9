package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.ScriptSystem;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;

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
            CommandMessages.exception(getName(), CMS.cms0);
            return;
        }
        ScriptSystem.B_CHAT=!ScriptSystem.B_CHAT;
        CommandMessages.key(getName(),CMS.cms12,ScriptSystem.B_CHAT);
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
        if(args.size()>0&&(!args.get(0).isEmpty())){
            return Literals.infoBuilder().string(CIS.cis3);
        }
        return Literals.emptyInfo();
    }
}
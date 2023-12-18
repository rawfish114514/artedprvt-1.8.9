package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.core.script.ScriptSystem;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.std.text.Formatting;

import java.text.MessageFormat;
import java.util.List;

/**
 * 切换CHAT状态 true/false
 */
public class CommandAc extends Command {
    public CommandAc(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, Messager messager) {
        if(args.size()>1){
            messager.send(Formatting.DARK_RED+getName()+CMS.cms16);
            return;
        }
        if(args.size()==1){
            ScriptSystem.CHAT_SWITCH =Boolean.valueOf(args.get(0));
        }else{
            ScriptSystem.CHAT_SWITCH =!ScriptSystem.CHAT_SWITCH;
        }
        messager.send(Formatting.GOLD+getName()+ MessageFormat.format(CMS.cms12,ScriptSystem.CHAT_SWITCH));
    }

    @Override
    public List<String> complete(List<String> args) {
        return Literals.stringListBuilder().adds("true","false");
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.formatListBuilder().set(
                Literals.stringListBuilder().adds("true","false"),
                Literals.formatFactory().append("9"),
                Literals.formatFactory().append("c")
        );
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()>1){
            return Literals.infoFactory().string(CIS.cis3);
        }
        return Literals.emptyInfo();
    }
}

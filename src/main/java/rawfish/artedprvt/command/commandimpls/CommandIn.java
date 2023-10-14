package rawfish.artedprvt.command.commandimpls;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.command.Formatter;
import rawfish.artedprvt.common.EventLoader;
import rawfish.artedprvt.event.InputStringEvent;

import java.util.List;

/**
 * 输入任意字符串 可被脚本进程监听并处理
 */
public class CommandIn extends Command {
    public CommandIn(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        InputStringEvent event=new InputStringEvent(String.join(" ",args));
        EventLoader.post(event);
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
        return CommandMessages.translate("cis10");
    }
}

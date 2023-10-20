package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;
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
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.emptyFormat();
    }

    @Override
    public InfoHandler info(List<String> args) {
        return Literals.infoBuilder().string(CommandMessages.translate("cis10"));
    }
}

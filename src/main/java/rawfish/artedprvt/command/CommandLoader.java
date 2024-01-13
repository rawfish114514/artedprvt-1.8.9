package rawfish.artedprvt.command;

import rawfish.artedprvt.command.commands.CommandApf;
import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.CommandHandler;

public class CommandLoader {
    public CommandApf apf = new CommandApf("apf");
    public CommandApf artedprvt = new CommandApf("artedprvt");

    public CommandLoader() {
        CommandHandler.register(apf, null);
        CommandHandler.register(artedprvt, null);
        for (Command command : apf.commandList) {
            CommandHandler.register(command, null);
        }
    }
}

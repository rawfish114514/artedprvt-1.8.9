package rawfish.artedprvt.command;

import rawfish.artedprvt.std.cli.CommandHandler;
import rawfish.artedprvt.command.commands.CommandApf;
import rawfish.artedprvt.std.cli.Command;

public class CommandLoader {
    public CommandApf apf=new CommandApf("apf");
    public CommandApf artedprvt=new CommandApf("artedprvt");

    public CommandLoader(){
        CommandHandler.register(apf,null);
        CommandHandler.register(artedprvt,null);
        for(Command command:apf.commandList){
            CommandHandler.register(command,null);
        }
    }
}

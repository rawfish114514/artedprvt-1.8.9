package rawfish.artedprvt.command;

import net.minecraftforge.client.ClientCommandHandler;
import rawfish.artedprvt.command.commands.CommandApf;
import rawfish.artedprvt.command.commands.CommandScript;

public class CommandLoader {
    public CommandApf apf=new CommandApf("apf");
    public CommandApf artedprvt=new CommandApf("artedprvt");

    public CommandLoader(ClientCommandHandler handler){
        handler.registerCommand(apf);
        handler.registerCommand(artedprvt);
        for(Command command:apf.commandList){
            handler.registerCommand(command);
        }
    }
}

package rawfish.artedprvt.command;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.command.item.CommandApf;

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

    public CommandLoader(FMLServerStartingEvent event){
        event.registerServerCommand(apf);
        event.registerServerCommand(artedprvt);
        for(Command command:apf.commandList){
            event.registerServerCommand(command);
        }
    }
}

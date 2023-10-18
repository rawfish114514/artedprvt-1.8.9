package rawfish.artedprvt.command.minecraft;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandInputHandler;
import rawfish.artedprvt.command.commands.CommandApf;

public class CommandLoader {
    public CommandApf apf=new CommandApf("apf");
    public CommandApf artedprvt=new CommandApf("artedprvt");

    public CommandLoader(ClientCommandHandler handler){
        handler.registerCommand(adapter(apf));
        handler.registerCommand(adapter(artedprvt));
        for(Command command:apf.commandList){
            handler.registerCommand(adapter(command));
        }

        CommandInputHandler.register(apf);
        CommandInputHandler.register(artedprvt);
        for(Command command:apf.commandList){
            CommandInputHandler.register(command);
        }
    }

    public CommandLoader(FMLServerStartingEvent event){
        event.registerServerCommand(adapter(apf));
        event.registerServerCommand(adapter(artedprvt));
        for(Command command:apf.commandList){
            event.registerServerCommand(adapter(command));
        }
    }

    public CommandAdapter adapter(Command command){
        return new CommandAdapter(command);
    }
}

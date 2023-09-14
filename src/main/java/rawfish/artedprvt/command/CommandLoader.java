package rawfish.artedprvt.command;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.client.CommandInfoChatGui;
import rawfish.artedprvt.command.item.CommandApf;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandLoader {
    public CommandApf apf=new CommandApf("apf");
    public CommandApf artedprvt=new CommandApf("artedprvt");

    public CommandLoader(ClientCommandHandler handler){
        handler.registerCommand(adapter(apf));
        handler.registerCommand(adapter(artedprvt));
        for(Command command:apf.commandList){
            handler.registerCommand(adapter(command));
        }

        CommandInfoChatGui.put(apf);
        CommandInfoChatGui.put(artedprvt);
        for(Command command:apf.commandList){
            CommandInfoChatGui.put(command);
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

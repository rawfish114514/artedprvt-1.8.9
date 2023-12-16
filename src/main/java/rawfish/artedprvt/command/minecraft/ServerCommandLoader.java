package rawfish.artedprvt.command.minecraft;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.command.commands.CommandApf;
import rawfish.artedprvt.client.CommandHandler;

public class ServerCommandLoader {
    public CommandApf apf=new CommandApf("apf");
    public CommandApf artedprvt=new CommandApf("artedprvt");

    public ServerCommandLoader(FMLServerStartingEvent event){
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

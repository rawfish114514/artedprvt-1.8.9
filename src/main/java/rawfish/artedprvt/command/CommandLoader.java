package rawfish.artedprvt.command;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader
{
    public CommandLoader(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandApf("artedprvt"));
        event.registerServerCommand(new CommandApf("apf"));
        event.registerServerCommand(new CommandAc("ac"));
        event.registerServerCommand(new CommandAd("ad"));
        event.registerServerCommand(new CommandScript("script"));
        event.registerServerCommand(new CommandApkg("apkg"));
        event.registerServerCommand(new CommandStops("stops"));
        event.registerServerCommand(new CommandPros("pros"));
    }

    public CommandLoader(ClientCommandHandler handler){
        handler.registerCommand(new CommandApf("artedprvt"));
        handler.registerCommand(new CommandApf("apf"));
        handler.registerCommand(new CommandAc("ac"));
        handler.registerCommand(new CommandAd("ad"));
        handler.registerCommand(new CommandScript("script"));
        handler.registerCommand(new CommandApkg("apkg"));
        handler.registerCommand(new CommandStops("stops"));
        handler.registerCommand(new CommandPros("pros"));
    }
}
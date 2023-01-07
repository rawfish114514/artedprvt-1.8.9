package rawfish.artedprvt.command;

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
        event.registerServerCommand(new CommandStops("stops"));
        event.registerServerCommand(new CommandPros("pros"));
    }
}
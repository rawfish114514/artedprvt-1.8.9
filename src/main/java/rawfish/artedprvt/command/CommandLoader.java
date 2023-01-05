package rawfish.artedprvt.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader
{
    public CommandLoader(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandAc());
        event.registerServerCommand(new CommandAd());
        event.registerServerCommand(new CommandScript());
        event.registerServerCommand(new CommandStops());
        event.registerServerCommand(new CommandApf());
        event.registerServerCommand(new CommandApf.CommandArtedprvt());
    }
}
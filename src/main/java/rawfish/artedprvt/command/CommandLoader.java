package rawfish.artedprvt.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader
{
    public CommandLoader(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandDebug());
        event.registerServerCommand(new CommandFile());
    }
}
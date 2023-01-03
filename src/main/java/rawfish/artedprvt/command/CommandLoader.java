package rawfish.artedprvt.command;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader
{
    public CommandLoader(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandChat());
        event.registerServerCommand(new CommandDebug());
        event.registerServerCommand(new CommandScript());
        event.registerServerCommand(new CommandStop());
    }
}
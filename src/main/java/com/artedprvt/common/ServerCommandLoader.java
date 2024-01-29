package com.artedprvt.common;

import com.artedprvt.command.commands.CommandApf;
import com.artedprvt.std.cli.Command;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ServerCommandLoader {
    public CommandApf apf = new CommandApf("apf");
    public CommandApf artedprvt = new CommandApf("artedprvt");

    public ServerCommandLoader(FMLServerStartingEvent event) {
        event.registerServerCommand(adapter(apf));
        event.registerServerCommand(adapter(artedprvt));
        for (Command command : apf.commandList) {
            event.registerServerCommand(adapter(command));
        }
    }

    public CommandAdapter adapter(Command command) {
        return new CommandAdapter(command);
    }
}

package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader
{
    public CommandLoader(FMLServerStartingEvent event)
    {
        CommandApf commandApf=new CommandApf("artedprvt");
        event.registerServerCommand(commandApf);
        event.registerServerCommand(new CommandApf("apf"));

        for(CommandBase c:commandApf.cmdm.values()){
            event.registerServerCommand(c);
        }
    }

    public CommandLoader(ClientCommandHandler handler){
        CommandApf commandApf=new CommandApf("artedprvt");
        handler.registerCommand(commandApf);
        handler.registerCommand(new CommandApf("apf"));

        for(CommandBase c:commandApf.cmdm.values()){
            handler.registerCommand(c);
        }
    }
}
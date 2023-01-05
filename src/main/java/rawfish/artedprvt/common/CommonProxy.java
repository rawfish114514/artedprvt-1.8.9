package rawfish.artedprvt.common;

import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.command.CommandLoader;
import rawfish.artedprvt.script.ScriptProcess;

import java.util.Map;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    public void init(FMLInitializationEvent event)
    {
        new EventLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    public static CommandHandler ch;
    public void serverStarting(FMLServerStartingEvent event)
    {
        ch = (CommandHandler) event.getServer().getCommandManager();
        Map<String, ICommand> commands= ch.getCommands();
        System.out.println(commands);

        new CommandLoader(event);
        ScriptProcess.initSargs();
    }
}
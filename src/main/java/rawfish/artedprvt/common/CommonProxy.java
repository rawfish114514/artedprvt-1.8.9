package rawfish.artedprvt.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.command.CommandLoader;
import rawfish.artedprvt.scriptold.ScriptProcess;
import rawfish.artedprvt.scriptold.js.ClassCollection;
import rawfish.artedprvt.scriptold.js.McpToSrgString;

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

    public void serverStarting(FMLServerStartingEvent event)
    {
        new CommandLoader(event);
        ScriptProcess.initSargs();
        ClassCollection.load(McpToSrgString.getMcpToSrgString());
    }
}
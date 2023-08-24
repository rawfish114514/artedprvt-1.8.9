package rawfish.artedprvt.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.command.CommandLoader;
import rawfish.artedprvt.core.FrameOptions;
import rawfish.artedprvt.core.FrameProperties;
import rawfish.artedprvt.core.ProcessController;
import rawfish.artedprvt.core.engine.ServiceEngines;
import rawfish.artedprvt.core.rhino.ClassCollection;
import rawfish.artedprvt.core.rhino.McpToSrgString;
import rawfish.artedprvt.mi.group.ClassGroupLoader;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    public void init(FMLInitializationEvent event)
    {
        new EventLoader();
        FrameProperties.init();
        ServiceEngines.init();
        ProcessController.init();
        ClassGroupLoader.reg();
        ClassCollection.load(McpToSrgString.getMcpToSrgString());
        FrameOptions.load();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    public void serverStarting(FMLServerStartingEvent event)
    {
        new CommandLoader(event);
    }
}
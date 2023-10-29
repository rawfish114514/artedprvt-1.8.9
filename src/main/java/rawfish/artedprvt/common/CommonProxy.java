package rawfish.artedprvt.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.command.minecraft.CommandLoader;
import rawfish.artedprvt.core.UserOptions;
import rawfish.artedprvt.core.ProcessController;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.script.engine.ScriptEngineInit;
import rawfish.artedprvt.core.script.rhino.Rhino;
import rawfish.artedprvt.mi.group.ClassGroupLoader;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        Rhino.init();
    }

    public void init(FMLInitializationEvent event)
    {
        new EventLoader();
        ClassGroupLoader.reg();
        WorkSpace.init();
        ProcessController.init();
        UserOptions.load();

        ScriptEngineInit.init();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    public void serverStarting(FMLServerStartingEvent event)
    {
        new CommandLoader(event);
    }
}
package rawfish.artedprvt.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.core.CoreInitializer;
import rawfish.artedprvt.core.UserOptions;
import rawfish.artedprvt.core.app.App;
import rawfish.artedprvt.core.app.script.engine.ScriptEngineInit;
import rawfish.artedprvt.core.app.script.rhino.Rhino;
import rawfish.artedprvt.std.cgl.ClassGroupLoader;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        Rhino.init();
    }

    public void init(FMLInitializationEvent event) {
        new EventLoader();
        CoreInitializer.init();
        ClassGroupLoader.load();
        UserOptions.load();

        ScriptEngineInit.init();
        App.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void serverStarting(FMLServerStartingEvent event) {
        new ServerCommandLoader(event);
    }
}
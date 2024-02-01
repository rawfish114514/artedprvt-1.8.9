package com.artedprvt.common;

import com.artedprvt.command.CommandLoader;
import com.artedprvt.core.CoreInitializer;
import com.artedprvt.core.app.App;
import com.artedprvt.core.app.script.engine.ScriptEngineInit;
import com.artedprvt.core.app.script.rhino.Rhino;
import com.artedprvt.std.cgl.ClassGroupLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import com.artedprvt.api.API;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        Rhino.init();
    }

    public void init(FMLInitializationEvent event) {
        new CommandLoader();
        new EventLoader();
        CoreInitializer.init();
        ClassGroupLoader.load();

        ScriptEngineInit.init();
        App.init();

        API.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void serverStarting(FMLServerStartingEvent event) {
        new ServerCommandLoader(event);
    }
}
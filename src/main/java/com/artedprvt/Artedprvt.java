package com.artedprvt;


import com.artedprvt.core.Environment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import com.artedprvt.common.CommonProxy;

@Mod(
        modid = Environment.MODID,
        name = Environment.MODNAME,
        acceptedMinecraftVersions = Environment.MCVERSION,
        acceptableRemoteVersions = "*"
)
public class Artedprvt {
    @SidedProxy(clientSide = "com.artedprvt.client.ClientProxy", serverSide = "com.artedprvt.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Environment.MODID)
    public static Artedprvt instance;

    public Artedprvt() {
    }

    public ModMetadata modMetadata;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        modMetadata = event.getModMetadata();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        modMetadata.description = getDescription();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }


    public String getDescription() {
        return "创造世界的最简单编程。" +
                otherDescription() +
                "\n\n作者以及更多信息 ↓\nhttps://space.bilibili.com/455906194";
    }

    public String tempVersion = "std 2024/4/2";

    public String otherDescription() {
        if (tempVersion.isEmpty()) {
            return tempVersion;
        }
        return "\n\n§c这是临时版本: '" + tempVersion + "'";
    }
}
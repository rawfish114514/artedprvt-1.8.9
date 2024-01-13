package rawfish.artedprvt;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.common.CommonProxy;
import rawfish.artedprvt.core.Environment;

@Mod(
        modid = Environment.MODID,
        name = Environment.MODNAME,
        acceptedMinecraftVersions = Environment.MCVERSION,
        acceptableRemoteVersions = "*"
)
public class Artedprvt {
    @SidedProxy(clientSide = "rawfish.artedprvt.client.ClientProxy", serverSide = "rawfish.artedprvt.common.CommonProxy")
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
        return "Artedprvt Frame 是专为 Minecraft 设计的脚本运行框架，它仅以在某时刻添加代码的方式对游戏修改。" +
                otherDescription() +
                "\n\n作者 ↓\nhttps://space.bilibili.com/455906194";
    }

    public String tempVersion = "temp 2024/1/14";

    public String otherDescription() {
        if (tempVersion.isEmpty()) {
            return tempVersion;
        }
        return "\n\n§c这是临时版本: '" + tempVersion + "'";
    }
}
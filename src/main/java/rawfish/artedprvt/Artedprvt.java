package rawfish.artedprvt;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.common.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.reflect.Field;


@Mod(
        modid=Artedprvt.MODID,
        name=Artedprvt.NAME,
        acceptedMinecraftVersions="1.8.9",
        acceptableRemoteVersions="*"
)
public class Artedprvt
{
    @SidedProxy(clientSide="rawfish.artedprvt.client.ClientProxy",serverSide="rawfish.artedprvt.common.CommonProxy")
    public static CommonProxy proxy;
    public static final String MODID="artedprvt";
    public static final String NAME="Artedprvt Frame";

    @Instance(Artedprvt.MODID)
    public static Artedprvt instance;

    public Artedprvt(){
        init();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        event.getModMetadata().description=getDescription();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.serverStarting(event);
    }


    public String getDescription(){
        return "Artedprvt Frame 是专为 Minecraft 设计的脚本运行框架，它在游戏中随时运行单个脚本文件或apkg文件(脚本和资源的集合，本质是zip压缩包)。目前支持的脚本语言只有 JavaScript。" +
                "\n\n作者 ↓\nhttps://space.bilibili.com/455906194";
    }

    public boolean isNotDevelopment;
    public void init() {
        try {
            Field field=Minecraft.class.getDeclaredField("theMinecraft");
            isNotDevelopment=false;
        } catch (NoSuchFieldException e) {
            isNotDevelopment=true;
        }
        System.out.println("Artedprvt.init.ind");
        System.out.println("开发环境: "+!isNotDevelopment);
    }
}
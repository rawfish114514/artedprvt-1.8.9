package rawfish.artedprvt;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import rawfish.artedprvt.common.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


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

    public static Logger logger;

    public Artedprvt(){
        init();
    }

    public ModMetadata modMetadata;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        modMetadata=event.getModMetadata();
        logger=event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        modMetadata.description=getDescription();
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
        return "Artedprvt Frame 是专为 Minecraft 设计的脚本运行框架，它在游戏中随时运行单个脚本文件或apkg文件。" +
                otherDescription()+
                "\n\n作者 ↓\nhttps://space.bilibili.com/455906194";
    }

    public String tempVersion="temp(command) 2023/10/22";
    public String otherDescription(){
        if(tempVersion.isEmpty()){
            return tempVersion;
        }
        return "\n\n§c这是临时版本: '"+tempVersion+"'";
    }

    public boolean isNotDevelopment() {
        return isNotDevelopment;
    }

    private boolean isNotDevelopment;

    public boolean isHasClientSide() {
        return hasClientSide;
    }

    private boolean hasClientSide;

    public void init() {
        try {
            MinecraftServer.class.getDeclaredField("mcServer");
            isNotDevelopment=false;
        } catch (NoSuchFieldException e) {
            isNotDevelopment=true;
        }

        try {
            Class.forName("net.minecraft.client.Minecraft");
            hasClientSide=true;
        } catch (ClassNotFoundException e) {
            hasClientSide=false;
        }
        System.out.println("Artedprvt.init.ind");
        System.out.println("开发环境: d:"+!isNotDevelopment+" c:"+hasClientSide);
    }
}
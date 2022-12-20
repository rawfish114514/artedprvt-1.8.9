package rawfish.artedprvt;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rawfish.artedprvt.common.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid=Artedprvt.MODID,name=Artedprvt.NAME,version=Artedprvt.VERSION,acceptedMinecraftVersions ="1.8.9")
public class Artedprvt
{
    @SidedProxy(clientSide="rawfish.artedprvt.client.ClientProxy",serverSide="rawfish.artedprvt.common.CommonProxy")
    public static CommonProxy proxy;
    public static final String MODID="artedprvt";
    public static final String NAME="ArtedPrvt Frame";
    public static final String VERSION="1.0.0";

    @Instance(Artedprvt.MODID)
    public static Artedprvt instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
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
}
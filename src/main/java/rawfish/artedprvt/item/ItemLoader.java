package rawfish.artedprvt.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader
{
    public static Item list=new ItemCreativeList();
    public static Itemis explain=new ItemExplainModAP();
    public static Itemis file=new ItemFile();

    public ItemLoader(FMLPreInitializationEvent event)
    {
        register(list);
        register(file);
    }


    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(file);
    }

    private static void register(Item item)
    {
        GameRegistry.registerItem(item);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
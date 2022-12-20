package rawfish.artedprvt.block;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader {
    public static Blockis power = new BlockPower();
    public static Blockis area=new BlockArea();

    public BlockLoader(FMLPreInitializationEvent event)
    {
        register(power);
        register(area);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(power);
        registerRender(area);
    }

    public static void register(Block block){
        GameRegistry.registerBlock(block);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block)
    {
        ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
    }
}

package rawfish.artedprvt.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rawfish.artedprvt.block.BlockLoader;
import rawfish.artedprvt.creativetab.CreativeTabsLoader;

import java.util.List;

public class ItemCreativeList extends Item {
    public ItemCreativeList(){
        super();
        setCreativeTab(CreativeTabsLoader.tab);
        setRegistryName("list");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        //item
        registryItem(ItemLoader.explain,subItems);
        registryItem(ItemLoader.file,subItems);

        //block
        registryItem(BlockLoader.power,subItems);
        registryItem(BlockLoader.area,subItems);
    }

    public void registryItem(Itemme item,List<ItemStack> subItems){
        item.appendCreativeItem(subItems);
    }
}

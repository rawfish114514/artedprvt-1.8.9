package rawfish.artedprvt.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rawfish.artedprvt.item.Itemme;

import java.util.List;

public class Blockis extends Block implements Itemme {
    public Blockis(Material blockMaterialIn) {
        super(blockMaterialIn);
    }

    public void appendCreativeItem(List<ItemStack> subItems){
        subItems.add(new ItemStack(this,1,0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
    }

}
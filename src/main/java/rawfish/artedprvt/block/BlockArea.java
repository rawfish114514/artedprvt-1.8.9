package rawfish.artedprvt.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import rawfish.artedprvt.creativetab.CreativeTabsLoader;

/**
 * area用于划分工作区域和优化程序生态
 * 适合运行操作地图的大型程序
 */
public class BlockArea extends Blockis {
    public BlockArea(){
        super(Material.iron);
        setUnlocalizedName("area");
        setRegistryName("area");
        setCreativeTab(CreativeTabsLoader.tab);
        setHardness(50);
    }

}
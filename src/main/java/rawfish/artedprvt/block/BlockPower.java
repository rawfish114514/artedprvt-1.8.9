package rawfish.artedprvt.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import rawfish.artedprvt.creativetab.CreativeTabsLoader;

public class BlockPower extends Blockis {
    public BlockPower(){
        super(Material.iron);
        setUnlocalizedName("power");
        setRegistryName("power");
        setCreativeTab(CreativeTabsLoader.tab);
        setHardness(50);
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        return 7;
    }
}

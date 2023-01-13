package rawfish.artedprvt.script.mi;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * 世界画布
 */
public class WorldGraphics {
    /**
     * 世界管理
     */
    public WorldManager manager;

    /**
     * 世界
     */
    public World world;

    /**
     * 构造一个对世界管理的世界操作的世界画布
     * @param managerIn 世界管理
     */
    public WorldGraphics(WorldManager managerIn){
        manager=managerIn;
        world=manager.world;
        block=Blocks.wool.getDefaultState();
    }

    /**
     * 方块状态
     */
    public IBlockState block;

    /**
     * 设置方块状态
     * @param blockIn 方块状态
     */
    public void setBlock(IBlockState blockIn){
        block=blockIn;
    }

    /**
     * 设置方块状态
     * @param blockIn 方块
     */
    public void setBlock(Block blockIn){
        block=blockIn.getDefaultState();
    }

    /**
     * "画"一个方块
     * @param pos 方块坐标
     */
    public void drawBlock(BlockPos pos){
        world.setBlockState(pos,block);
    }

    /**
     * "画"一个方块
     * @param x 方块坐标x
     * @param y 方块坐标y
     * @param z 方块坐标z
     */
    public void drawBlock(double x,double y,double z){
        drawBlock(new BlockPos(x,y,z));
    }

}

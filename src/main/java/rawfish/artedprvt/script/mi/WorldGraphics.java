package rawfish.artedprvt.script.mi;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGraphics {
    public WorldManager manager;
    public World world;
    public WorldGraphics(WorldManager managerIn){
        manager=managerIn;
        world=manager.world;
        block=Blocks.wool.getDefaultState();
    }

    public IBlockState block;
    public void setBlock(IBlockState blockIn){
        block=blockIn;
    }
    public void setBlock(Block blockIn){
        block=blockIn.getDefaultState();
    }
    public void drawBlock(BlockPos pos){
        world.setBlockState(pos,block);
    }
    public void drawBlock(double x,double y,double z){
        drawBlock(new BlockPos(x,y,z));
    }

}

package rawfish.artedprvt.script.mi;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/**
 * 世界画布
 * 在操作世界地形的设计上
 * 借用了画布的概念
 * 与UI画布的区别是二维和三维
 * 以及颜色和方块
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
     * 方块状态
     */
    public IBlockState block;

    public int drawCount;

    /**
     * 构造一个对世界管理的世界操作的世界画布
     * @param managerIn 世界管理
     */
    public WorldGraphics(WorldManager managerIn){
        manager=managerIn;
        world=manager.world;
        block=Blocks.wool.getDefaultState();
        drawCount=0;
    }


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
     * 获取方块状态
     * @return 当前的方块状态
     */
    public IBlockState getBlock(){
        return block;
    }

    /**
     * 获取画的总数
     * 也就是设置的方块的总数
     * @return 画总数
     */
    public int getDrawCount(){
        return drawCount;
    }

    /**
     * 画一个方块
     * @param pos 方块坐标
     */
    public void drawBlock(BlockPos pos){
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        chunk.setBlockState(pos, block);
        drawCount++;
    }

    /**
     * 画一个方块
     * @param x 方块坐标x
     * @param y 方块坐标y
     * @param z 方块坐标z
     */
    public void drawBlock(double x,double y,double z){
        drawBlock(new BlockPos(x,y,z));
    }

    /**
     * 画一个立方体
     * @param pos 起始坐标
     * @param extend 扩展向量 它等于这个立方体的体对角线
     */
    public void drawCube(BlockPos pos,BlockPos extend){
        BlockPos startPos=pos;
        BlockPos endPos=pos.add(extend);

        int[] startChunk={startPos.getX()>>4,startPos.getZ()>>4};
        int[] endChunk={endPos.getX()>>4,endPos.getZ()>>4};

        for(int cx=startChunk[0];cx<endChunk[0]+1;cx++){
            for(int cz=startChunk[1];cz<endChunk[1]+1;cz++){
                Chunk chunk=world.getChunkFromChunkCoords(cx,cz);

                int hx=chunk.xPosition<<4,hz=chunk.zPosition<<4;

                int is=Math.max(startPos.getX(),hx);
                int ie=Math.min(endPos.getX(),hx+16);
                int js=startPos.getY();
                int je=endPos.getY();
                int ks=Math.max(startPos.getZ(),hz);
                int ke=Math.min(endPos.getZ(),hz+16);

                for(int i=is;i<ie;i++){
                    for(int j=js;j<je;j++){
                        for(int k=ks;k<ke;k++){
                            chunk.setBlockState(new BlockPos(i,j,k),block);
                        }
                    }
                }
            }
        }

        drawCount+=extend.getX()*extend.getY()*extend.getZ();
    }

    /**
     * 画一个立方体
     * @param x 起始坐标x
     * @param y 起始坐标y
     * @param z 起始坐标z
     * @param extendX 扩展向量x
     * @param extendY 扩展向量y
     * @param extendZ 扩展向量z
     */
    public void drawCube(double x,double y,double z,double extendX,double extendY,double extendZ){
        drawCube(new BlockPos(x,y,z),new BlockPos(extendX,extendY,extendZ));
    }

}

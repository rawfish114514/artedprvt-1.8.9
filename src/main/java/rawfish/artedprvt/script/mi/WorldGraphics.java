package rawfish.artedprvt.script.mi;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.awt.*;

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

    /**
     * 方块中心
     */
    public BlockPos center;

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
     * 获取世界上的方块
     * @param pos 方块坐标
     * @return
     */
    public IBlockState getBlock(BlockPos pos){
        return world.getBlockState(pos);
    }

    /**
     * 获取世界上的方块
     * @return
     */
    public IBlockState getBlock(double x,double y,double z){
        return world.getBlockState(new BlockPos(x,y,z));
    }

    /**
     * 设置焦点
     * @param center
     */
    public void setCenter(BlockPos center){
        this.center=center;
    }

    /**
     * 设置焦点
     * @param x
     * @param y
     * @param z
     */
    public void setCenter(double x,double y,double z){
        setCenter(new BlockPos(x,y,z));
    }

    /**
     * 获取焦点
     * @return
     */
    public BlockPos getCenter(){
        return center;
    }

    /**
     * 创建BlockPos对象
     * @param x
     * @param y
     * @param z
     * @return
     */
    public BlockPos wrapPos(double x,double y,double z){
        return new BlockPos(x,y,z);
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
        BlockPos thePos=pos.add(center);
        Chunk chunk = world.getChunkFromBlockCoords(thePos);
        chunk.setBlockState(thePos, block);
        drawCount++;
    }

    /**
     * 画方块
     * @param x 方块坐标x
     * @param y 方块坐标y
     * @param z 方块坐标z
     */
    public void drawBlock(double x,double y,double z){
        drawBlock(new BlockPos(x,y,z));
    }

    /**
     * 画立方体
     * @param pos1 顶点1
     * @param pos2 顶点2
     */
    public void drawCube(BlockPos pos1,BlockPos pos2){
        BlockPos startPos=new BlockPos(
                Math.min(pos1.getX(),pos2.getX()),
                Math.min(pos1.getY(),pos2.getY()),
                Math.min(pos1.getZ(),pos2.getZ())).
                add(center);
        BlockPos endPos=new BlockPos(
                Math.max(pos1.getX(),pos2.getX()),
                Math.max(pos1.getY(),pos2.getY()),
                Math.max(pos1.getZ(),pos2.getZ())).
                add(center);

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

        drawCount+=Math.abs((endPos.getX()-startPos.getX())*(endPos.getY()-startPos.getY())*(endPos.getZ()-startPos.getZ()));
    }

    /**
     * 画立方体
     * @param x1 顶点1
     * @param y1 顶点1
     * @param z1 顶点1
     * @param x2 顶点2
     * @param y2 顶点2
     * @param z2 顶点2
     */
    public void drawCube(double x1,double y1,double z1,double x2,double y2,double z2){
        drawCube(new BlockPos(x1,y1,z1),new BlockPos(x2,y2,z2));
    }

    /**
     * 画竖列
     * @param pos 坐标
     * @param height 高度
     */
    public void drawColumn(BlockPos pos,int height){
        BlockPos startPos=new BlockPos(pos.getX(),Math.min(pos.getY(),pos.getY()+height),pos.getZ()).add(center);
        int end=Math.max(pos.getY(),pos.getY()+height)+center.getY();
        Chunk chunk=world.getChunkFromChunkCoords(startPos.getX()>>4,startPos.getZ()>>4);
        for(int i=startPos.getY();i<end;i++){
            chunk.setBlockState(new BlockPos(startPos.getX(),i,startPos.getZ()),block);
        }
        drawCount+=Math.abs(height);
    }

    /**
     * 画竖列
     * @param x 坐标
     * @param y 坐标
     * @param z 坐标
     * @param h 高度
     */
    public void drawColumn(double x,double y,double z,int h){
        drawColumn(new BlockPos(x,y,z),h);
    }

}

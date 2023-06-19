package rawfish.artedprvt.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import rawfish.artedprvt.core.ScriptObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 世界管理
 */
public class WorldManager implements ScriptObject {

    /**
     * 世界
     */
    public World world;

    public List<WorldGraphics> graphicsList;

    @ScriptUsable
    public WorldManager(String side){
        up();
        if(side.equals("server")) {
            world = MinecraftServer.getServer().getEntityWorld();
        }else{
            world = Minecraft.getMinecraft().theWorld;
        }
        graphicsList=new ArrayList<>();
    }

    @ScriptUsable
    public WorldManager(World world){
        up();
        this.world=world;
        graphicsList=new ArrayList<>();
    }

    /**
     * 创建并返回WorldGraphics对象
     * @return 操作这个世界的WorldGraphics对象
     */
    @ScriptUsable
    public WorldGraphics getGraphics(){
        WorldGraphics graphics=new WorldGraphics(this);
        graphicsList.add(graphics);
        return graphics;
    }

    /**
     * 计算操作方块数
     * @return
     */
    @ScriptUsable
    public int getBlockOper(){
        int blockOper=0;
        for(WorldGraphics graphics:graphicsList){
            blockOper+=graphics.getDrawCount();
        }
        return blockOper;
    }

    @Override
    public void onClose() {
        int blockOper=getBlockOper();
        if(blockOper>0){
            PrintChat printChat=new PrintChat();
            printChat.print("§d任务结束 操作方块: "+blockOper);
        }
        world=null;
    }
}


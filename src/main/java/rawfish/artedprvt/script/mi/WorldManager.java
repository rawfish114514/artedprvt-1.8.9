package rawfish.artedprvt.script.mi;

import net.minecraft.world.World;
import rawfish.artedprvt.id.FormatCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 世界管理
 */
public class WorldManager extends LifeDepend{

    /**
     * 世界
     */
    public World world;

    public List<WorldGraphics> graphicsList;

    /**
     * 构造世界管理对象
     */
    public WorldManager(){
        up();
        world=pro.getSender().getEntityWorld();
        graphicsList=new ArrayList<>();
    }

    /**
     * 创建并返回WorldGraphics对象
     * @return 操作这个世界的WorldGraphics对象
     */
    public WorldGraphics getGraphics(){
        WorldGraphics graphics=new WorldGraphics(this);
        graphicsList.add(graphics);
        return graphics;
    }

    @Override
    public void terminate() {
        //计算操作方块数
        int blockOper=0;
        for(WorldGraphics graphics:graphicsList){
            blockOper+=graphics.getDrawCount();
        }
        if(blockOper>0){
            pro.getSys().print(pro.getPack(), FormatCode.COLOR_d+"任务结束 操作方块: "+blockOper);
        }
    }
}


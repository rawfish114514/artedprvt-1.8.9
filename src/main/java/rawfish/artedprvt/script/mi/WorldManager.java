package rawfish.artedprvt.script.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

/**
 * 世界管理
 */
public class WorldManager extends LifeDepend{

    /**
     * 世界
     */
    public World world;

    /**
     * 构造世界管理对象
     */
    public WorldManager(){
        up();
        world=pro.getSender().getEntityWorld();
    }

    /**
     * 创建并返回WorldGraphics对象
     * @return 操作这个世界的WorldGraphics对象
     */
    public WorldGraphics getGraphics(){
        return new WorldGraphics(this);
    }

    @Override
    public void terminate() {

    }
}


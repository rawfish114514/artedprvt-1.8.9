package rawfish.artedprvt.script.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class WorldManager extends LifeDepend{
    public World world;
    public WorldManager(){
        up();
        world=pro.getSender().getEntityWorld();
    }

    public WorldGraphics getGraphics(){
        return new WorldGraphics(this);
    }

    @Override
    public void terminate() {

    }
}


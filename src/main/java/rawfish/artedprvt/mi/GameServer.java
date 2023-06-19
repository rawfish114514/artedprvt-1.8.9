package rawfish.artedprvt.mi;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import rawfish.artedprvt.core.ScriptObject;

public class GameServer implements ScriptObject {
    public MinecraftServer minecraftServer;

    public GameServer(){
        up();
        minecraftServer=MinecraftServer.getServer();
    }

    public WorldServer getWorld(int i){
        return minecraftServer.worldServers[i];
    }
    @Override
    public void onClose() {
        minecraftServer=null;
    }
}

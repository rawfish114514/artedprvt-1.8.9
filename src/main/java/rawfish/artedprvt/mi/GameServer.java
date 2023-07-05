package rawfish.artedprvt.mi;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import rawfish.artedprvt.core.ProcedureUsable;
import rawfish.artedprvt.core.ScriptObject;

@ProcedureUsable
public class GameServer implements ScriptObject {
    public MinecraftServer minecraftServer;

    @ProcedureUsable
    public GameServer(){
        up();
        minecraftServer=MinecraftServer.getServer();
    }

    @ProcedureUsable
    public WorldServer getWorld(int i){
        return minecraftServer.worldServers[i];
    }

    @ProcedureUsable
    public EntityPlayerMP getPlayer(WorldServer world, String name){
        return (EntityPlayerMP)world.getPlayerEntityByName(name);
    }

    @Override
    public void onClose() {
        minecraftServer=null;
    }
}

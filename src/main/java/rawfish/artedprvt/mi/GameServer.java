package rawfish.artedprvt.mi;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import rawfish.artedprvt.core.ProgramUsable;
import rawfish.artedprvt.core.ScriptObject;
import rawfish.artedprvt.core.SideUsable;
import rawfish.artedprvt.core.Sides;

@SideUsable(Sides.SERVER)
@ProgramUsable
public class GameServer implements ScriptObject {
    public MinecraftServer minecraft;

    @ProgramUsable
    public GameServer(){
        up();
        minecraft =MinecraftServer.getServer();
    }

    @ProgramUsable
    public WorldServer getWorld(int i){
        return minecraft.worldServers[i];
    }

    @ProgramUsable
    public EntityPlayerMP getPlayer(WorldServer world, String name){
        return (EntityPlayerMP)world.getPlayerEntityByName(name);
    }

    @ProgramUsable
    public void sendChat(WorldServer world,String chat){
        S02PacketChat packetChat=new S02PacketChat(new ChatComponentText(chat));
        for(EntityPlayer player:world.playerEntities){
            if(player instanceof EntityPlayerMP){
                ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(packetChat);
            }
        }
    }

    @Override
    public void onClose() {
        minecraft =null;
    }
}
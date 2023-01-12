package rawfish.artedprvt.script;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.util.List;

/**
 * 提供客户端专用功能
 */
public class ScriptClient {
    public Minecraft minecraft;
    public NetworkManager manager;
    public ScriptClient(){
        minecraft=Minecraft.getMinecraft();
        manager=minecraft.getNetHandler().getNetworkManager();

    }

    public void sendChat(String message){
        manager.sendPacket(new C01PacketChatMessage(message));
    }

    public List<EntityPlayer> getAllEntity(){
        return Minecraft.getMinecraft().theWorld.playerEntities;
    }


    public EntityPlayerSP getThisPlayer(){
        return minecraft.thePlayer;
    }



}

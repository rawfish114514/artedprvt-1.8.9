package rawfish.artedprvt.script;

import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C01PacketChatMessage;

/**
 * 提供客户端专用功能
 */
public class ScriptClient {
    public NetworkManager manager;
    public ScriptClient(){
        manager=Minecraft.getMinecraft().getNetHandler().getNetworkManager();
    }

    public void sendChat(String message){
        manager.sendPacket(new C01PacketChatMessage(message));
    }


}

package rawfish.artedprvt.mi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import rawfish.artedprvt.core.ScriptObject;

/**
 * 游戏客户端
 */
public class GameClient implements ScriptObject {
    public Minecraft minecraft;
    public NetworkManager networkManager;

    @ScriptUsable
    public GameClient(){
        up();
        minecraft=Minecraft.getMinecraft();
        networkManager=minecraft.getNetHandler().getNetworkManager();
    }

    /**
     * 获取自己玩家
     * @return
     */
    @ScriptUsable
    public EntityPlayerSP getPlayer(){
        return minecraft.thePlayer;
    }

    /**
     * 获取自己世界
     * @return
     */
    @ScriptUsable
    public WorldClient getWorld(){
        return minecraft.theWorld;
    }

    /**
     * 发送数据包
     * @param packet 数据包
     */
    @ScriptUsable
    public void sendPacket(Packet packet){
        networkManager.sendPacket(packet);
    }

    /**
     * 发送聊天数据包
     * @param chat 聊天信息
     */
    @ScriptUsable
    public void sendChat(String chat){
        sendPacket(new C01PacketChatMessage(chat));
    }

    @Override
    public void onClose() {
        minecraft=null;
        networkManager=null;
    }
}

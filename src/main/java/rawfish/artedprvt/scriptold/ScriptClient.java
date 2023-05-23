package rawfish.artedprvt.scriptold;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

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

    /**
     * 发送消息数据包
     * @param message 消息
     */
    public void sendChat(String message) {
        manager.sendPacket(new C01PacketChatMessage(message));
    }

    /**
     * 发送交互实体数据包
     * @param entity 攻击的实体
     */
    public void sendUse(Entity entity){
        manager.sendPacket(new C02PacketUseEntity(entity,C02PacketUseEntity.Action.ATTACK));
    }

    /**
     * 获取所有实体
     * @return
     */
    public List<Entity> getAllEntity(){
        return Minecraft.getMinecraft().theWorld.getLoadedEntityList();
    }

    /**
     * 获取自己实体
     * @return
     */
    public EntityPlayerSP getThisPlayer(){
        return minecraft.thePlayer;
    }

    public Object getDemoObject(Scriptable scope){
        EntityPlayerSP p=getThisPlayer();
        return new NativeJavaObject(scope,p,p.getClass()){
            @Override
            public Object get(String name, Scriptable start) {
                System.out.println("访问字段: "+name);
                return super.get(name,start);
            }
        };
    }
}

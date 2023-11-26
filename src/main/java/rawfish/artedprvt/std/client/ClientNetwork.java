package rawfish.artedprvt.std.client;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.entity.Entity;

@Solvable
public class ClientNetwork {
    private NetHandlerPlayClient MnetHandlerPlayClient;

    public ClientNetwork(NetHandlerPlayClient MnetHandlerPlayClient){
        this.MnetHandlerPlayClient=MnetHandlerPlayClient;
    }

    @Solvable
    public void sendChat(String chat){
        MnetHandlerPlayClient.addToSendQueue(new C01PacketChatMessage(chat));
    }

    @Solvable
    public void sendAttack(Entity entity){
        MnetHandlerPlayClient.addToSendQueue(new C02PacketUseEntity(entity.getMentity(),C02PacketUseEntity.Action.ATTACK));
    }
}

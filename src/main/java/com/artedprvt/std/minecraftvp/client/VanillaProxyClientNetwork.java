package com.artedprvt.std.minecraftvp.client;

import com.artedprvt.std.minecraft.client.ClientNetwork;
import com.artedprvt.std.minecraft.entity.Entity;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class VanillaProxyClientNetwork implements ClientNetwork {
    private NetHandlerPlayClient v_netHandlerPlayClient;

    public VanillaProxyClientNetwork(NetHandlerPlayClient v_netHandlerPlayClient) {
        this.v_netHandlerPlayClient = v_netHandlerPlayClient;
    }

    @Override
    public NetHandlerPlayClient v_getNetHandlerPlayClient() {
        return v_netHandlerPlayClient;
    }

    @Override
    public void sendChat(String chat) {
        v_netHandlerPlayClient.addToSendQueue(new C01PacketChatMessage(chat));
    }

    @Override
    public void sendAttack(Entity entity) {
        v_netHandlerPlayClient.addToSendQueue(new C02PacketUseEntity(entity.v_getEntity(), C02PacketUseEntity.Action.ATTACK));
    }
}

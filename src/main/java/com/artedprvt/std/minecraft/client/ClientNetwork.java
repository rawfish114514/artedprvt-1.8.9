package com.artedprvt.std.minecraft.client;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.entity.Entity;
import net.minecraft.client.network.NetHandlerPlayClient;

@InterfaceView
public interface ClientNetwork {
    NetHandlerPlayClient v_getNetHandlerPlayClient();

    @InterfaceView
    void sendChat(String chat);

    @InterfaceView
    void sendAttack(Entity entity);
}

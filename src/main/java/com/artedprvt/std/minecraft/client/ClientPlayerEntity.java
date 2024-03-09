package com.artedprvt.std.minecraft.client;

import com.artedprvt.std.minecraft.entity.PlayerEntity;
import net.minecraft.client.entity.EntityPlayerSP;
import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public class ClientPlayerEntity extends PlayerEntity {
    public ClientPlayerEntity(EntityPlayerSP MentityPlayerSP) {
        super(MentityPlayerSP);
    }

    @InterfaceView
    public ClientNetwork getNetwork() {
        return new ClientNetwork(((EntityPlayerSP) Mentity).sendQueue);
    }
}

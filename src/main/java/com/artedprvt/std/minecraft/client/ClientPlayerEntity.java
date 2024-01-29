package com.artedprvt.std.minecraft.client;

import com.artedprvt.std.minecraft.entity.PlayerEntity;
import net.minecraft.client.entity.EntityPlayerSP;
import com.artedprvt.api.Solvable;

@Solvable
public class ClientPlayerEntity extends PlayerEntity {
    public ClientPlayerEntity(EntityPlayerSP MentityPlayerSP) {
        super(MentityPlayerSP);
    }

    @Solvable
    public ClientNetwork getNetwork() {
        return new ClientNetwork(((EntityPlayerSP) Mentity).sendQueue);
    }
}

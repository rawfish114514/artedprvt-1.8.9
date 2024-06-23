package com.artedprvt.std.impls.minecraft.client;

import com.artedprvt.std.minecraft.client.EntityClientPlayer;
import com.artedprvt.std.impls.minecraft.entity.VanillaProxyEntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;

public class VanillaProxyEntityClientPlayer extends VanillaProxyEntityPlayer implements EntityClientPlayer {
    public EntityPlayerSP v_entityPlayerSP;

    public VanillaProxyEntityClientPlayer(EntityPlayerSP v_entityPlayerSP) {
        super(v_entityPlayerSP);
        this.v_entityPlayerSP = v_entityPlayerSP;
    }

    @Override
    public EntityPlayerSP v_getEntityPlayerSP() {
        return v_entityPlayerSP;
    }

    @Override
    public VanillaProxyClientNetwork getNetwork() {
        return new VanillaProxyClientNetwork(((EntityPlayerSP) v_entity).sendQueue);
    }

}

package com.artedprvt.std.minecraftvp.client;

import com.artedprvt.std.minecraft.client.ClientEntityPlayer;
import com.artedprvt.std.minecraftvp.entity.VanillaProxyEntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;

public class VanillaProxyClientEntityPlayer extends VanillaProxyEntityPlayer implements ClientEntityPlayer {
    public EntityPlayerSP v_entityPlayerSP;

    public VanillaProxyClientEntityPlayer(EntityPlayerSP v_entityPlayerSP) {
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

package com.artedprvt.std.minecraftvp.client;

import com.artedprvt.std.minecraft.client.ClientWorld;
import com.artedprvt.std.minecraft.entity.Entity;
import com.artedprvt.std.minecraftvp.world.VanillaProxyWorld;
import net.minecraft.client.multiplayer.WorldClient;

public class VanillaProxyClientWorld extends VanillaProxyWorld implements ClientWorld {
    public net.minecraft.client.multiplayer.WorldClient v_worldClient;

    public VanillaProxyClientWorld(net.minecraft.client.multiplayer.WorldClient v_worldClient) {
        super(v_worldClient);
        this.v_worldClient = v_worldClient;
    }

    @Override
    public WorldClient v_getWorldClient() {
        return v_worldClient;
    }
}

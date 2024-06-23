package com.artedprvt.std.impls.minecraft.client;

import com.artedprvt.std.minecraft.client.WorldClient;
import com.artedprvt.std.impls.minecraft.world.VanillaProxyWorld;

public class VanillaProxyWorldClient extends VanillaProxyWorld implements WorldClient {
    public net.minecraft.client.multiplayer.WorldClient v_worldClient;

    public VanillaProxyWorldClient(net.minecraft.client.multiplayer.WorldClient v_worldClient) {
        super(v_worldClient);
        this.v_worldClient = v_worldClient;
    }

    @Override
    public net.minecraft.client.multiplayer.WorldClient v_getWorldClient() {
        return v_worldClient;
    }
}

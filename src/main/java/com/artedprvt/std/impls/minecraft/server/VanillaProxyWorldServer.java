package com.artedprvt.std.impls.minecraft.server;

import com.artedprvt.std.minecraft.server.WorldServer;
import com.artedprvt.std.impls.minecraft.world.VanillaProxyWorld;

public class VanillaProxyWorldServer extends VanillaProxyWorld implements WorldServer {
    public net.minecraft.world.WorldServer v_worldServer;

    public VanillaProxyWorldServer(net.minecraft.world.WorldServer v_worldServer) {
        super(v_worldServer);
        this.v_worldServer = v_worldServer;
    }

    @Override
    public net.minecraft.world.WorldServer v_getWorldServer() {
        return v_worldServer;
    }
}

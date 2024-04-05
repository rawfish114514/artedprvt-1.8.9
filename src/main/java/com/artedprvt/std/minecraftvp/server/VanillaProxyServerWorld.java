package com.artedprvt.std.minecraftvp.server;

import com.artedprvt.std.minecraft.server.ServerWorld;
import com.artedprvt.std.minecraftvp.world.VanillaProxyWorld;
import net.minecraft.world.WorldServer;

public class VanillaProxyServerWorld extends VanillaProxyWorld implements ServerWorld {
    public WorldServer v_worldServer;

    public VanillaProxyServerWorld(WorldServer v_worldServer) {
        super(v_worldServer);
        this.v_worldServer = v_worldServer;
    }

    @Override
    public WorldServer v_getWorldServer() {
        return v_worldServer;
    }
}

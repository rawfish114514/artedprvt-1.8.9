package com.artedprvt.std.impls.minecraft.server;

import com.artedprvt.std.minecraft.server.ServerMinecraft;
import com.artedprvt.std.minecraft.server.WorldServer;
import net.minecraft.server.MinecraftServer;

public class VanillaProxyServerMinecraft implements ServerMinecraft {
    public MinecraftServer v_minecraftServer;

    public VanillaProxyServerMinecraft() {
        v_minecraftServer = MinecraftServer.getServer();
    }

    @Override
    public MinecraftServer v_getMinecraftServer() {
        return v_minecraftServer;
    }

    @Override
    public WorldServer getWorld(int dimension) {
        return new VanillaProxyWorldServer(v_minecraftServer.worldServerForDimension(dimension));
    }
}

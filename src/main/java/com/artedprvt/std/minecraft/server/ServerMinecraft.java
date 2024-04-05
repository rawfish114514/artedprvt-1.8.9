package com.artedprvt.std.minecraft.server;

import com.artedprvt.iv.anno.InterfaceView;
import net.minecraft.server.MinecraftServer;

@InterfaceView
public interface ServerMinecraft {
    MinecraftServer v_getMinecraftServer();

    @InterfaceView
    ServerWorld getWorld(int dimension);
}

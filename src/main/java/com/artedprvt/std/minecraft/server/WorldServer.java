package com.artedprvt.std.minecraft.server;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.world.World;

@InterfaceView
public interface WorldServer extends World {
    net.minecraft.world.WorldServer v_getWorldServer();
}

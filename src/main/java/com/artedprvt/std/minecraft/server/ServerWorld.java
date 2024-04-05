package com.artedprvt.std.minecraft.server;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.world.World;
import net.minecraft.world.WorldServer;

@InterfaceView
public interface ServerWorld extends World {
    WorldServer v_getWorldServer();
}

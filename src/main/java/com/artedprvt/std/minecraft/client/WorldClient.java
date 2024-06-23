package com.artedprvt.std.minecraft.client;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.world.World;

@InterfaceView
public interface WorldClient extends World {
    net.minecraft.client.multiplayer.WorldClient v_getWorldClient();
}

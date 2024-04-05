package com.artedprvt.std.minecraft.client;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.world.World;
import net.minecraft.client.multiplayer.WorldClient;

@InterfaceView
public interface ClientWorld extends World {
    WorldClient v_getWorldClient();
}

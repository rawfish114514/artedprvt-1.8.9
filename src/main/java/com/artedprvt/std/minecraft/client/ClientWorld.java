package com.artedprvt.std.minecraft.client;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.world.World;

@InterfaceView
public class ClientWorld extends World {
    public ClientWorld(net.minecraft.client.multiplayer.WorldClient MworldClient) {
        super(MworldClient);
    }
}

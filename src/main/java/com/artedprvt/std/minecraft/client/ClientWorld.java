package com.artedprvt.std.minecraft.client;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.minecraft.world.World;

@Solvable
public class ClientWorld extends World {
    public ClientWorld(net.minecraft.client.multiplayer.WorldClient MworldClient) {
        super(MworldClient);
    }
}

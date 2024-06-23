package com.artedprvt.std.minecraft.client;

import com.artedprvt.iv.anno.InterfaceView;
import net.minecraft.client.Minecraft;

@InterfaceView
public interface ClientMinecraft {
    Minecraft v_getMinecraft();

    @InterfaceView
    EntityClientPlayer getPlayer();

    @InterfaceView
    WorldClient getWorld();
}

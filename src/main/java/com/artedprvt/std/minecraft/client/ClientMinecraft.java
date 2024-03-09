package com.artedprvt.std.minecraft.client;

import net.minecraft.client.Minecraft;
import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public class ClientMinecraft {
    private Minecraft minecraft;

    @InterfaceView
    public ClientMinecraft() {
        minecraft = Minecraft.getMinecraft();
    }

    @InterfaceView
    public ClientPlayerEntity getPlayer() {
        return new ClientPlayerEntity(minecraft.thePlayer);
    }

    @InterfaceView
    public ClientWorld getWorld() {
        return new ClientWorld(minecraft.theWorld);
    }
}

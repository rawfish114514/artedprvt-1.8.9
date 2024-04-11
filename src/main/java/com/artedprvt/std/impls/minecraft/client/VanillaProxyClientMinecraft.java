package com.artedprvt.std.impls.minecraft.client;

import com.artedprvt.std.minecraft.client.ClientMinecraft;
import net.minecraft.client.Minecraft;

public class VanillaProxyClientMinecraft implements ClientMinecraft {
    private Minecraft v_minecraft;

    public VanillaProxyClientMinecraft() {
        v_minecraft = Minecraft.getMinecraft();
    }

    @Override
    public Minecraft v_getMinecraft() {
        return v_minecraft;
    }

    @Override
    public VanillaProxyClientEntityPlayer getPlayer() {
        return new VanillaProxyClientEntityPlayer(v_minecraft.thePlayer);
    }

    @Override
    public VanillaProxyClientWorld getWorld() {
        return new VanillaProxyClientWorld(v_minecraft.theWorld);
    }
}

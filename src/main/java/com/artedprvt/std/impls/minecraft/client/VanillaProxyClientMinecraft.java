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
    public VanillaProxyEntityClientPlayer getPlayer() {
        return new VanillaProxyEntityClientPlayer(v_minecraft.thePlayer);
    }

    @Override
    public VanillaProxyWorldClient getWorld() {
        return new VanillaProxyWorldClient(v_minecraft.theWorld);
    }
}

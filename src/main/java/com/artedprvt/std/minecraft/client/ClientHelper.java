package com.artedprvt.std.minecraft.client;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.minecraft.client.VanillaProxyClientMinecraft;

@InterfaceView
public class ClientHelper {
    @InterfaceView
    public static ClientMinecraft getClientMinecraft() {
        return new VanillaProxyClientMinecraft();
    }
}

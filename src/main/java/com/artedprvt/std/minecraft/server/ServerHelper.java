package com.artedprvt.std.minecraft.server;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.minecraft.server.VanillaProxyServerMinecraft;

@InterfaceView
public class ServerHelper {
    @InterfaceView
    public static ServerMinecraft getServerMinecraft() {
        return new VanillaProxyServerMinecraft();
    }
}

package com.artedprvt.std.minecraft.client;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.entity.EntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;

@InterfaceView
public interface EntityClientPlayer extends EntityPlayer {
    EntityPlayerSP v_getEntityPlayerSP();

    @InterfaceView
    ClientNetwork getNetwork();
}

package com.artedprvt.std.impls.minecraft.entity;


import com.artedprvt.std.minecraft.entity.EntityPlayer;

public class VanillaProxyEntityPlayer extends VanillaProxyEntity implements EntityPlayer {
    public net.minecraft.entity.player.EntityPlayer v_entityPlayer;

    public VanillaProxyEntityPlayer(net.minecraft.entity.player.EntityPlayer v_entityPlayer) {
        super(v_entityPlayer);
        this.v_entityPlayer = v_entityPlayer;
    }

    @Override
    public net.minecraft.entity.player.EntityPlayer v_getEntityPlayer() {
        return v_entityPlayer;
    }
}

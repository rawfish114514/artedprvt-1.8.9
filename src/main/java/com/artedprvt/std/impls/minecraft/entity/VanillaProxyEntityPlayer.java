package com.artedprvt.std.impls.minecraft.entity;


import com.artedprvt.std.impls.minecraft.item.VanillaProxyItemStack;
import com.artedprvt.std.minecraft.entity.EntityPlayer;
import com.artedprvt.std.minecraft.item.ItemStack;

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

    @Override
    public ItemStack getCurrentItem() {
        return new VanillaProxyItemStack(v_entityPlayer.inventory.getCurrentItem());
    }
}

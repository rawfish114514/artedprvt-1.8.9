package com.artedprvt.std.minecraft.entity;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.item.ItemStack;

@InterfaceView
public interface EntityPlayer extends Entity {
    net.minecraft.entity.player.EntityPlayer v_getEntityPlayer();

    @InterfaceView
    ItemStack getCurrentItem();
}

package com.artedprvt.std.minecraft.item;

import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public interface ItemStack {
    net.minecraft.item.ItemStack v_getItemStack();

    @InterfaceView
    ItemType getItemType();
}

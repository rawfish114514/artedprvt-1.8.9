package com.artedprvt.std.minecraft.item;

import com.artedprvt.iv.anno.InterfaceView;
import net.minecraft.item.Item;

@InterfaceView
public interface ItemType {
    Item v_getItem();

    @InterfaceView
    ItemStack doItemStack();

    @InterfaceView
    ItemStack doItemStack(int amount);
}

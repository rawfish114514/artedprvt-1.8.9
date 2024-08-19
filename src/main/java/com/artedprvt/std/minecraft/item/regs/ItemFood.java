package com.artedprvt.std.minecraft.item.regs;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.minecraft.item.ItemStack;
import com.artedprvt.std.minecraft.item.ItemType;

@InterfaceView
public interface ItemFood extends ItemType {
    @InterfaceView
    int getHeal(ItemStack itemStack);

    @InterfaceView
    float getSaturation(ItemStack itemStack);
}

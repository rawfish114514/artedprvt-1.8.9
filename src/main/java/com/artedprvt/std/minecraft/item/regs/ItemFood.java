package com.artedprvt.std.minecraft.item.regs;

import com.artedprvt.std.minecraft.item.ItemStack;
import com.artedprvt.std.minecraft.item.ItemType;

public interface ItemFood extends ItemType {
    int getHeal(ItemStack itemStack);

    float getSaturation(ItemStack itemStack);
}

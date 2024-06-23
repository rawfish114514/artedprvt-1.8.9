package com.artedprvt.std.impls.minecraft.item;

import com.artedprvt.std.minecraft.item.ItemStack;
import com.artedprvt.std.minecraft.item.regs.ItemFood;

public class VanillaProxyItemFood extends VanillaProxyBaseItemType<net.minecraft.item.ItemFood> implements ItemFood {
    public VanillaProxyItemFood(net.minecraft.item.ItemFood v_item) {
        super(v_item);
    }

    @Override
    public int getHeal(ItemStack itemStack) {
        return v_item.getHealAmount(itemStack.v_getItemStack());
    }

    @Override
    public float getSaturation(ItemStack itemStack) {
        return v_item.getSaturationModifier(itemStack.v_getItemStack());
    }
}

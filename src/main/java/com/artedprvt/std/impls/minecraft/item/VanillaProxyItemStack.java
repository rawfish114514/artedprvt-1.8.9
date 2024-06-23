package com.artedprvt.std.impls.minecraft.item;

import com.artedprvt.std.minecraft.item.ItemStack;
import com.artedprvt.std.minecraft.item.ItemType;

public class VanillaProxyItemStack implements ItemStack {
    public net.minecraft.item.ItemStack v_itemStack;

    public VanillaProxyItemStack(net.minecraft.item.ItemStack v_itemStack) {
        this.v_itemStack = v_itemStack;
    }

    @Override
    public net.minecraft.item.ItemStack v_getItemStack() {
        return v_itemStack;
    }

    @Override
    public ItemType getItemType() {
        return new VanillaProxyBaseItemType<>(v_itemStack.getItem());
    }
}

package com.artedprvt.std.impls.minecraft.item;

import com.artedprvt.std.minecraft.item.ItemStack;
import com.artedprvt.std.minecraft.item.ItemType;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VanillaProxyItemStack that = (VanillaProxyItemStack) o;
        return Objects.equals(v_itemStack, that.v_itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(v_itemStack);
    }

    @Override
    public String toString() {
        return "ItemStack{" + v_itemStack + "}";
    }
}

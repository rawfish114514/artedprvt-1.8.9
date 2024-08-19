package com.artedprvt.std.impls.minecraft.item;

import com.artedprvt.std.minecraft.item.ItemStack;
import com.artedprvt.std.minecraft.item.ItemType;

import java.util.Objects;

public class RequiringProxyItemStack implements ItemStack {
    public ItemType itemType;

    public net.minecraft.item.ItemStack v_itemStack;

    public RequiringProxyItemStack(ItemType itemType) {
        this.itemType = itemType;
        v_itemStack = new net.minecraft.item.ItemStack(itemType.v_getItem());
    }

    public RequiringProxyItemStack(ItemType itemType, int amount) {
        this.itemType = itemType;
        v_itemStack = new net.minecraft.item.ItemStack(itemType.v_getItem(), amount);
    }

    @Override
    public net.minecraft.item.ItemStack v_getItemStack() {
        return v_itemStack;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequiringProxyItemStack that = (RequiringProxyItemStack) o;
        return Objects.equals(itemType, that.itemType) && Objects.equals(v_itemStack, that.v_itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, v_itemStack);
    }
}

package com.artedprvt.std.impls.minecraft.item;

import com.artedprvt.std.minecraft.item.ItemStack;
import com.artedprvt.std.minecraft.item.ItemType;
import net.minecraft.item.Item;

import java.util.Objects;

public class VanillaProxyBaseItemType<T extends Item> implements ItemType {
    public T v_item;

    public VanillaProxyBaseItemType(T v_item) {
        this.v_item = v_item;
    }

    @Override
    public Item v_getItem() {
        return v_item;
    }

    @Override
    public ItemStack doItemStack() {
        return new RequiringProxyItemStack(this);
    }

    @Override
    public ItemStack doItemStack(int amount) {
        return new RequiringProxyItemStack(this, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VanillaProxyBaseItemType<?> that = (VanillaProxyBaseItemType<?>) o;
        return Objects.equals(v_item, that.v_item);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(v_item);
    }

    @Override
    public String toString() {
        return "ItemType{" + v_item + "}";
    }
}

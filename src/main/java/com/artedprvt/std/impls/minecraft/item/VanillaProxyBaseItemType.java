package com.artedprvt.std.impls.minecraft.item;

import com.artedprvt.std.minecraft.item.ItemType;
import net.minecraft.item.Item;

public class VanillaProxyBaseItemType<T extends Item> implements ItemType {
    public T v_item;

    public VanillaProxyBaseItemType(T v_item) {
        this.v_item = v_item;
    }

    @Override
    public Item v_getItem() {
        return v_item;
    }
}

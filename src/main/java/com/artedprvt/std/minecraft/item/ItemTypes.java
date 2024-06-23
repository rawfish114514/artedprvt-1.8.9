package com.artedprvt.std.minecraft.item;

import com.artedprvt.std.impls.minecraft.item.VanillaProxyItemFood;
import com.artedprvt.std.minecraft.item.regs.ItemFood;
import net.minecraft.init.Items;

public class ItemTypes {
    public static final ItemFood golden_apple = new VanillaProxyItemFood((net.minecraft.item.ItemFood) Items.golden_apple);
}

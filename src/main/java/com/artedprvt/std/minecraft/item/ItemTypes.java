package com.artedprvt.std.minecraft.item;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.minecraft.item.regs.VanillaProxyItemFood;
import com.artedprvt.std.minecraft.item.regs.ItemFood;
import net.minecraft.init.Items;

// 原版物品表 以后将抽象出命名空间
@InterfaceView
public class ItemTypes {
    @InterfaceView
    public static final ItemFood golden_apple = new VanillaProxyItemFood((net.minecraft.item.ItemFood) Items.golden_apple);
}

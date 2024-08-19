package com.artedprvt.std.minecraft.block;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.impls.minecraft.block.regs.VanillaProxyBlockAir;
import com.artedprvt.std.minecraft.block.regs.BlockAir;
import net.minecraft.init.Blocks;

// 原版方块表 以后将抽象出命名空间
@InterfaceView
public class BlockTypes {
    @InterfaceView
    public static final BlockAir air = new VanillaProxyBlockAir((net.minecraft.block.BlockAir) Blocks.air);
}

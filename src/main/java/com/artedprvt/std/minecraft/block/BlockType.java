package com.artedprvt.std.minecraft.block;

import com.artedprvt.iv.anno.InterfaceView;
import net.minecraft.block.Block;

@InterfaceView
public interface BlockType {
    Block v_getBlock();

    @InterfaceView
    BlockState doBlockState();

    @InterfaceView
    BlockState doBlockState(int meta);
}

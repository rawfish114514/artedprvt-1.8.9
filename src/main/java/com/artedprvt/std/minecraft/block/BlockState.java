package com.artedprvt.std.minecraft.block;

import com.artedprvt.iv.anno.InterfaceView;
import net.minecraft.block.state.IBlockState;

@InterfaceView
public interface BlockState {
    IBlockState v_getIBlockState();

    @InterfaceView
    BlockType getBlockType();
}

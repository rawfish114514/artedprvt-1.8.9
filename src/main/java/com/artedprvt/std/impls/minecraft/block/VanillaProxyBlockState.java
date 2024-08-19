package com.artedprvt.std.impls.minecraft.block;

import com.artedprvt.std.minecraft.block.BlockType;
import net.minecraft.block.state.IBlockState;

public class VanillaProxyBlockState extends ProxyBlockState {
    public VanillaProxyBlockState(IBlockState v_iBlockState) {
        super(v_iBlockState);
    }

    @Override
    public BlockType getBlockType() {
        return new VanillaProxyBaseBlockType<>(v_iBlockState.getBlock());
    }
}

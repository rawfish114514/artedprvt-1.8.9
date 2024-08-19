package com.artedprvt.std.impls.minecraft.block;

import com.artedprvt.std.minecraft.block.BlockType;

public class RequiringProxyBlockState extends ProxyBlockState {
    public BlockType blockType;

    public RequiringProxyBlockState(BlockType blockType) {
        super(blockType.v_getBlock().getDefaultState());
        this.blockType = blockType;
    }

    public RequiringProxyBlockState(BlockType blockType, int meta) {
        super(blockType.v_getBlock().getStateFromMeta(meta));
        this.blockType = blockType;
    }

    @Override
    public BlockType getBlockType() {
        return blockType;
    }
}
package com.artedprvt.std.impls.minecraft.block;

import com.artedprvt.std.minecraft.block.BlockState;
import com.artedprvt.std.minecraft.block.BlockType;
import net.minecraft.block.Block;

import java.util.Objects;

public class VanillaProxyBaseBlockType<T extends Block> implements BlockType {
    public T v_block;

    public VanillaProxyBaseBlockType(T v_block) {
        this.v_block = v_block;
    }

    @Override
    public Block v_getBlock() {
        return v_block;
    }

    @Override
    public BlockState doBlockState() {
        return new RequiringProxyBlockState(this);
    }

    @Override
    public BlockState doBlockState(int meta) {
        return new RequiringProxyBlockState(this,meta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VanillaProxyBaseBlockType<?> that = (VanillaProxyBaseBlockType<?>) o;
        return Objects.equals(v_block, that.v_block);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(v_block);
    }
}

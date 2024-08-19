package com.artedprvt.std.impls.minecraft.block;

import com.artedprvt.std.minecraft.block.BlockState;
import net.minecraft.block.state.IBlockState;

import java.util.Objects;

abstract class ProxyBlockState implements BlockState {
    public IBlockState v_iBlockState;

    public ProxyBlockState(IBlockState v_iBlockState) {
        this.v_iBlockState = v_iBlockState;
    }

    @Override
    public IBlockState v_getIBlockState() {
        return v_iBlockState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !ProxyBlockState.class.isAssignableFrom(o.getClass())) return false;
        ProxyBlockState that = (ProxyBlockState) o;
        return Objects.equals(v_iBlockState, that.v_iBlockState);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(v_iBlockState);
    }

    @Override
    public String toString() {
        return "BlockState{" + v_iBlockState + '}';
    }
}

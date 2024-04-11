package com.artedprvt.std.impls.particle;

public class NativeParticleCompute {
    //Java和C++操作同一块内存 DirectByteBuffer
    //废弃 数据结构被限制 数据传递开销大 内存大几十倍 总体速度变慢
    /**
     *
     * @param address address+number*112
     * @param number
     * @param raw number*16
     * @param nonUnitScale number
     */
    public static native void computeAll(
            long address,
            int number,
            float[] raw,
            boolean[] nonUnitScale);

}

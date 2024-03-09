package com.artedprvt.std.math;

import com.artedprvt.iv.anno.InterfaceView;

@InterfaceView
public class Vector3 extends Vector {
    @InterfaceView
    public Vector3(double x, double y, double z) {
        super(x, y, z);
    }

    @InterfaceView
    public double getX() {
        return get(0);
    }

    @InterfaceView
    public double getY() {
        return get(1);
    }

    @InterfaceView
    public double getZ() {
        return get(2);
    }
}

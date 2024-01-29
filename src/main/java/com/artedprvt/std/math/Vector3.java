package com.artedprvt.std.math;

import com.artedprvt.api.Solvable;

@Solvable
public class Vector3 extends Vector {
    @Solvable
    public Vector3(double x, double y, double z) {
        super(x, y, z);
    }

    @Solvable
    public double getX() {
        return get(0);
    }

    @Solvable
    public double getY() {
        return get(1);
    }

    @Solvable
    public double getZ() {
        return get(2);
    }
}

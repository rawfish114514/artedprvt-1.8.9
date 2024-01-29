package com.artedprvt.std.cgl.math;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.math.Vector;
import com.artedprvt.std.math.Vector3;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupMath extends BaseClassGroup {
    @Solvable
    public static final ClassGroupMath INSTANCE = new ClassGroupMath("math");

    @Solvable
    public ClassGroupMath(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(Vector.class);
        add(Vector3.class);
    }
}
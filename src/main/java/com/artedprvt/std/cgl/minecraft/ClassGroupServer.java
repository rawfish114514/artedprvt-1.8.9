package com.artedprvt.std.cgl.minecraft;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupServer extends BaseClassGroup {
    @Solvable
    public static final ClassGroupServer INSTANCE = new ClassGroupServer("mcserver");

    @Solvable
    public ClassGroupServer(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupCommon.INSTANCE);
    }
}
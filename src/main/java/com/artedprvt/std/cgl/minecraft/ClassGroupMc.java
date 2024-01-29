package com.artedprvt.std.cgl.minecraft;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupMc extends BaseClassGroup {
    @Solvable
    public static final ClassGroupMc INSTANCE = new ClassGroupMc("mc");

    @Solvable
    public ClassGroupMc(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupClient.INSTANCE);
        union(ClassGroupServer.INSTANCE);
    }
}
package com.artedprvt.std.cgl.minecraft;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cgl.minecraft.client.ClassGroupMcClient;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupClient extends BaseClassGroup {
    @Solvable
    public static final ClassGroupClient INSTANCE = new ClassGroupClient("mcclient");

    @Solvable
    public ClassGroupClient(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupCommon.INSTANCE);
        union(ClassGroupMcClient.INSTANCE);
    }
}
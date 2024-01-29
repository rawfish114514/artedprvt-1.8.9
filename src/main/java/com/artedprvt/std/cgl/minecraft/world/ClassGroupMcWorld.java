package com.artedprvt.std.cgl.minecraft.world;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.minecraft.world.World;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupMcWorld extends BaseClassGroup {
    @Solvable
    public static final ClassGroupMcWorld INSTANCE = new ClassGroupMcWorld("mc.world");

    @Solvable
    public ClassGroupMcWorld(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(World.class);
    }
}
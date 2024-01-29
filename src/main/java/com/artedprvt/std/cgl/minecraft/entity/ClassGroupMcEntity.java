package com.artedprvt.std.cgl.minecraft.entity;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.minecraft.entity.Entity;
import com.artedprvt.std.minecraft.entity.PlayerEntity;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupMcEntity extends BaseClassGroup {
    @Solvable
    public static final ClassGroupMcEntity INSTANCE = new ClassGroupMcEntity("mc.entity");

    @Solvable
    public ClassGroupMcEntity(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(Entity.class);
        add(PlayerEntity.class);
    }
}
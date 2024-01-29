package com.artedprvt.std.cgl.minecraft;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cgl.minecraft.chat.ClassGroupMcChat;
import com.artedprvt.std.cgl.minecraft.entity.ClassGroupMcEntity;
import com.artedprvt.std.cgl.minecraft.world.ClassGroupMcWorld;
import com.artedprvt.api.Solvable;

@Solvable
public class ClassGroupCommon extends BaseClassGroup {
    @Solvable
    public static final ClassGroupCommon INSTANCE = new ClassGroupCommon("mccommon");

    @Solvable
    public ClassGroupCommon(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupMcChat.INSTANCE);
        union(ClassGroupMcEntity.INSTANCE);
        union(ClassGroupMcWorld.INSTANCE);
    }
}
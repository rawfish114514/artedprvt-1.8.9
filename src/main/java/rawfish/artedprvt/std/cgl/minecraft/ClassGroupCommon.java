package rawfish.artedprvt.std.cgl.minecraft;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cgl.minecraft.chat.ClassGroupMcChat;
import rawfish.artedprvt.std.cgl.minecraft.entity.ClassGroupMcEntity;
import rawfish.artedprvt.std.cgl.minecraft.world.ClassGroupMcWorld;

@Solvable
public class ClassGroupCommon extends BaseClassGroup {
    @Solvable
    public static final ClassGroupCommon INSTANCE=new ClassGroupCommon("mccommon");

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
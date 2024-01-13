package rawfish.artedprvt.std.cgl.minecraft.entity;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.minecraft.entity.Entity;
import rawfish.artedprvt.std.minecraft.entity.PlayerEntity;

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
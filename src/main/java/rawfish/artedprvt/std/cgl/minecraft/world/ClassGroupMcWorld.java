package rawfish.artedprvt.std.cgl.minecraft.world;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.minecraft.world.World;

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
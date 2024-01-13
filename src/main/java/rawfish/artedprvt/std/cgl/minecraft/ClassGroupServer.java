package rawfish.artedprvt.std.cgl.minecraft;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;

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
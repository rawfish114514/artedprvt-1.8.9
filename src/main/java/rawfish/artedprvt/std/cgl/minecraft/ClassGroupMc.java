package rawfish.artedprvt.std.cgl.minecraft;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;

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
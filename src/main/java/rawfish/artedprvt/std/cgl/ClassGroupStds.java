package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.BaseClassGroup;

/**
 * 可用于 ServerSide 的组
 */
public class ClassGroupStds extends BaseClassGroup {
    public static final ClassGroupStds INSTANCE=new ClassGroupStds("stds");

    public ClassGroupStds(String name) {
        super(name);
        init();
    }

    private void init() {
    }
}

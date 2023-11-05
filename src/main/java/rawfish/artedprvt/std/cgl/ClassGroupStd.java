package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.BaseClassGroup;

public class ClassGroupStd extends BaseClassGroup {
    public static final ClassGroupStd INSTANCE=new ClassGroupStd("std");

    public ClassGroupStd(String name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupStdc.INSTANCE);
        union(ClassGroupStds.INSTANCE);
    }
}

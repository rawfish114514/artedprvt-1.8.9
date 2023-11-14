package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.BaseClassGroup;

public class ClassGroupStd extends BaseClassGroup {
    public static final ClassGroupStd INSTANCE=new ClassGroupStd(ClassGroupNameEnum.STD);

    public ClassGroupStd(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupStdClient.INSTANCE);
        union(ClassGroupStdServer.INSTANCE);
    }
}

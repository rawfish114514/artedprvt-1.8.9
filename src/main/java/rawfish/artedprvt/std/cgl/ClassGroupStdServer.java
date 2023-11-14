package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.BaseClassGroup;

/**
 * 可用于 ServerSide 的组
 */
public class ClassGroupStdServer extends BaseClassGroup {
    public static final ClassGroupStdServer INSTANCE=new ClassGroupStdServer(ClassGroupNameEnum.STDS);

    public ClassGroupStdServer(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupStdCommon.INSTANCE);
    }
}

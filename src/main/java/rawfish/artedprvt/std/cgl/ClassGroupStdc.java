package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.BaseClassGroup;

/**
 * 可用于 ClientSide 的组
 */
public class ClassGroupStdc extends BaseClassGroup {
    public static final ClassGroupStdc INSTANCE=new ClassGroupStdc("stdc");

    public ClassGroupStdc(String name) {
        super(name);
        init();
    }

    private void init() {
    }

    @Override
    public boolean permission() {
        //has client
        return true;
    }
}

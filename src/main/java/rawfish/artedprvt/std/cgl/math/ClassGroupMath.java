package rawfish.artedprvt.std.cgl.math;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.math.Vector;
import rawfish.artedprvt.std.math.Vector3;

@Solvable
public class ClassGroupMath extends BaseClassGroup {
    @Solvable
    public static final ClassGroupMath INSTANCE=new ClassGroupMath("math");

    @Solvable
    public ClassGroupMath(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(Vector.class);
        add(Vector3.class);
    }
}
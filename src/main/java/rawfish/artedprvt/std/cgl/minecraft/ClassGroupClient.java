package rawfish.artedprvt.std.cgl.minecraft;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cgl.minecraft.client.ClassGroupMcClient;

@Solvable
public class ClassGroupClient extends BaseClassGroup {
    @Solvable
    public static final ClassGroupClient INSTANCE=new ClassGroupClient("mcclient");

    @Solvable
    public ClassGroupClient(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupCommon.INSTANCE);
        union(ClassGroupMcClient.INSTANCE);
    }
}
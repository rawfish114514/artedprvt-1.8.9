package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.Environment;
import rawfish.artedprvt.core.script.BaseClassGroup;
import rawfish.artedprvt.std.client.ClientMinecraft;
import rawfish.artedprvt.std.client.ClientPlayerEntity;

/**
 * 可用于 ClientSide 的组
 */
public class ClassGroupStdClient extends BaseClassGroup {
    public static final ClassGroupStdClient INSTANCE=new ClassGroupStdClient(ClassGroupNameEnum.STDC);

    public ClassGroupStdClient(Object name) {
        super(name);
        init();
    }

    private void init() {
        union(ClassGroupStdCommon.INSTANCE);
        if(permission()){
            add(ClientMinecraft.class);
            add(ClientPlayerEntity.class);
        }
    }

    @Override
    public boolean permission() {
        //has client
        return Environment.MCCLIENT;
    }
}

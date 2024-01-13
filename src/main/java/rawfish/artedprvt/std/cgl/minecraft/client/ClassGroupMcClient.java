package rawfish.artedprvt.std.cgl.minecraft.client;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cgl.minecraft.ClientBaseClassGroup;
import rawfish.artedprvt.std.minecraft.client.ClientMinecraft;
import rawfish.artedprvt.std.minecraft.client.ClientPlayerEntity;

@Solvable
public class ClassGroupMcClient extends ClientBaseClassGroup {
    @Solvable
    public static final ClassGroupMcClient INSTANCE = new ClassGroupMcClient("mc.client");

    @Solvable
    public ClassGroupMcClient(Object name) {
        super(name);
        init();
    }

    private void init() {
        if (permission()) {
            add(ClientMinecraft.class);
            add(ClientPlayerEntity.class);
        }
    }
}
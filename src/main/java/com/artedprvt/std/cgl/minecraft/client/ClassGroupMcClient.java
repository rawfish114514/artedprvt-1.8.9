package com.artedprvt.std.cgl.minecraft.client;

import com.artedprvt.std.minecraft.client.ClientMinecraft;
import com.artedprvt.std.minecraft.client.ClientPlayerEntity;
import com.artedprvt.api.Solvable;
import com.artedprvt.std.cgl.minecraft.ClientBaseClassGroup;

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
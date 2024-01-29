package com.artedprvt.std.cgl.minecraft;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.core.Environment;
import com.artedprvt.api.Solvable;

@Solvable
public class ClientBaseClassGroup extends BaseClassGroup {
    @Solvable
    public ClientBaseClassGroup(Object name) {
        super(name);
    }

    @Override
    @Solvable
    public boolean permission() {
        //has client
        return Environment.MCCLIENT;
    }
}

package rawfish.artedprvt.std.cgl.minecraft;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.core.Environment;

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

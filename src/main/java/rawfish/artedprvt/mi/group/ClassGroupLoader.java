package rawfish.artedprvt.mi.group;

import rawfish.artedprvt.core.ClassGroupSystem;

public class ClassGroupLoader {
    public static void reg(){
        ClassGroupSystem.reg(new ClassGroupMi());
        ClassGroupSystem.reg(new ClassGroupMiClient());
        ClassGroupSystem.reg(new ClassGroupMiServer());
    }
}

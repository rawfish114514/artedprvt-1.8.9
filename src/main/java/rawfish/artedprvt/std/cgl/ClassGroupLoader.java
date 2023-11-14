package rawfish.artedprvt.std.cgl;

import rawfish.artedprvt.core.script.ClassGroupSystem;

public class ClassGroupLoader {
    public static void load(){
        ClassGroupSystem.add(ClassGroupStd.INSTANCE);
        ClassGroupSystem.add(ClassGroupStdClient.INSTANCE);
        ClassGroupSystem.add(ClassGroupStdServer.INSTANCE);
    }
}

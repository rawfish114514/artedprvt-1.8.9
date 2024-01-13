package rawfish.artedprvt.core.app;

import java.io.File;

public class Home {
    private static String home = System.getProperties().get("user.dir").toString() + "/artedprvt";

    public static String home() {
        return home;
    }

    public static String app() {
        return home + "/app";
    }

    public static String data() {
        return home + "/data";
    }

    public static String log() {
        return home + "/log";
    }

    public static void init() {
        new File(home()).mkdir();
        new File(app()).mkdir();
        new File(data()).mkdir();
        new File(log()).mkdir();
    }
}

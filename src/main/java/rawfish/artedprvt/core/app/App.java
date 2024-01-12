package rawfish.artedprvt.core.app;

import rawfish.artedprvt.core.app.java.JavaAppType;
import rawfish.artedprvt.core.app.script.ScriptAppType;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void init() {
        Home.init();

        add(new ScriptAppType());
        add(new JavaAppType());
    }

    public static void add(AppType<?> appType) {
        appTypes.add(appType);
    }

    private static List<AppType<?>> appTypes = new ArrayList<>();

    public static List<AppType<?>> getAppTypes() {
        return appTypes;
    }
}

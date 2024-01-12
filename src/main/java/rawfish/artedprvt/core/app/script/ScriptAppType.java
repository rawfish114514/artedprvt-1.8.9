package rawfish.artedprvt.core.app.script;

import rawfish.artedprvt.core.app.AppTarget;
import rawfish.artedprvt.core.app.AppType;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ScriptAppType implements AppType<ScriptProcess> {
    private static final List<String> extensions = Arrays.asList("asp");

    @Override
    public List<String> extensions() {
        return extensions;
    }

    @Override
    public AppTarget<ScriptProcess> target(URL url) throws Exception {
        return new ScriptAppTarget(this, url);
    }
}

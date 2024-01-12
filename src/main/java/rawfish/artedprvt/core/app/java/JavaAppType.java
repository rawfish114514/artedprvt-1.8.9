package rawfish.artedprvt.core.app.java;

import rawfish.artedprvt.core.app.AppTarget;
import rawfish.artedprvt.core.app.AppType;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class JavaAppType implements AppType<JavaProcess> {
    private static final List<String> extensions = Arrays.asList("jar");

    @Override
    public List<String> extensions() {
        return extensions;
    }

    @Override
    public AppTarget<JavaProcess> target(URL url) throws Exception {
        return new JavaAppTarget(this, url);
    }
}

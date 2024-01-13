package rawfish.artedprvt.std.cgl.cli.info;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.BaseClassGroup;
import rawfish.artedprvt.std.cli.info.InfoHandlerEmpty;
import rawfish.artedprvt.std.cli.info.InfoHandlerMap;
import rawfish.artedprvt.std.cli.info.InfoHandlerString;

@Solvable
public class ClassGroupCliInfo extends BaseClassGroup {
    public static final ClassGroupCliInfo INSTANCE = new ClassGroupCliInfo("cli.info");

    @Solvable
    public ClassGroupCliInfo(Object name) {
        super(name);
        init();
    }

    private void init() {
        add(InfoHandlerEmpty.class);
        add(InfoHandlerMap.class);
        add(InfoHandlerString.class);
    }
}
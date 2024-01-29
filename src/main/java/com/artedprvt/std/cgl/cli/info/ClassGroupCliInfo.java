package com.artedprvt.std.cgl.cli.info;

import com.artedprvt.core.BaseClassGroup;
import com.artedprvt.std.cli.info.InfoHandlerEmpty;
import com.artedprvt.std.cli.info.InfoHandlerMap;
import com.artedprvt.std.cli.info.InfoHandlerString;
import com.artedprvt.api.Solvable;

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
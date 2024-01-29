package com.artedprvt.std.cli.util;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.info.InfoHandlerString;

import java.util.HashMap;

@Solvable
public class InfoHandlerMapBuilder extends HashMap<String, InfoHandler> {
    @Solvable
    public InfoHandlerMapBuilder puts(String string, InfoHandler infoHandler) {
        put(string, infoHandler);
        return this;
    }

    public InfoHandlerMapBuilder string(String string, String info) {
        return puts(string, new InfoHandlerString(info));
    }
}

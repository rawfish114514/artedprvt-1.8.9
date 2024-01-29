package com.artedprvt.std.cli.util;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.info.InfoHandlerEmpty;
import com.artedprvt.std.cli.info.InfoHandlerMap;
import com.artedprvt.std.cli.info.InfoHandlerString;

import java.util.Map;

@Solvable
public class InfoHandlerFactory {
    @Solvable
    public InfoHandler empty() {
        return new InfoHandlerEmpty();
    }

    @Solvable
    public InfoHandler map(
            Map<String, InfoHandler> map,
            InfoHandler falseHandler) {
        return new InfoHandlerMap(map, falseHandler);
    }

    @Solvable
    public InfoHandler string(String string) {
        return new InfoHandlerString(string);
    }
}

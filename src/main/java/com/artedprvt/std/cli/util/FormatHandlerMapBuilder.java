package com.artedprvt.std.cli.util;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.FormatHandler;

import java.util.HashMap;

@Solvable
public class FormatHandlerMapBuilder extends HashMap<String, FormatHandler> {
    @Solvable
    public FormatHandlerMapBuilder puts(String string, FormatHandler formatHandler) {
        put(string, formatHandler);
        return this;
    }
}

package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.FormatHandler;

import java.util.HashMap;

@InterfaceView
public class FormatHandlerMapBuilder extends HashMap<String, FormatHandler> {
    @InterfaceView
    public FormatHandlerMapBuilder puts(String string, FormatHandler formatHandler) {
        put(string, formatHandler);
        return this;
    }
}

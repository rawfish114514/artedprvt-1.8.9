package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.info.InfoHandlerEmpty;
import com.artedprvt.std.cli.info.InfoHandlerMap;
import com.artedprvt.std.cli.info.InfoHandlerString;

import java.util.Map;

@InterfaceView
public class InfoHandlerFactory {
    @InterfaceView
    public InfoHandler empty() {
        return new InfoHandlerEmpty();
    }

    @InterfaceView
    public InfoHandler map(
            Map<String, InfoHandler> map,
            InfoHandler falseHandler) {
        return new InfoHandlerMap(map, falseHandler);
    }

    @InterfaceView
    public InfoHandler string(String string) {
        return new InfoHandlerString(string);
    }
}

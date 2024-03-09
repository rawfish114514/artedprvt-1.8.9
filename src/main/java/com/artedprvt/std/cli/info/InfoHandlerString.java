package com.artedprvt.std.cli.info;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.InfoHandler;

@InterfaceView
public class InfoHandlerString implements InfoHandler {
    private String string;

    @InterfaceView
    public InfoHandlerString(String string) {
        this.string = string;
    }

    @Override
    @InterfaceView
    public String handleInfo(String source) {
        return string;
    }
}

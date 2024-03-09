package com.artedprvt.std.cli.info;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.InfoHandler;

@InterfaceView
public class InfoHandlerEmpty implements InfoHandler {
    @Override
    @InterfaceView
    public String handleInfo(String source) {
        return "";
    }
}

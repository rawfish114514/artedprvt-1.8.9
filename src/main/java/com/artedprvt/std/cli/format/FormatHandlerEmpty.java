package com.artedprvt.std.cli.format;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.FormatHandler;

/**
 * 无
 */
@InterfaceView
public class FormatHandlerEmpty implements FormatHandler {
    @Override
    @InterfaceView
    public String handleFormat(String source) {
        return source;
    }
}

package com.artedprvt.std.cli.format;

import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.FormatHandler;

/**
 * 附加
 */
@InterfaceView
public class FormatHandlerAppend implements FormatHandler {
    private final String append;

    /**
     * @param append 附加的格式代码
     */
    @InterfaceView
    public FormatHandlerAppend(String append) {
        this.append = Literals.fcToString(append);
    }

    @Override
    @InterfaceView
    public String handleFormat(String source) {
        return append + source;
    }
}

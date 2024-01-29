package com.artedprvt.std.cli.format;

import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.FormatHandler;

/**
 * 附加
 */
@Solvable
public class FormatHandlerAppend implements FormatHandler {
    private final String append;

    /**
     * @param append 附加的格式代码
     */
    @Solvable
    public FormatHandlerAppend(String append) {
        this.append = Literals.fcToString(append);
    }

    @Override
    @Solvable
    public String handleFormat(String source) {
        return append + source;
    }
}

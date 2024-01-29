package com.artedprvt.std.cli.format;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.FormatHandler;

/**
 * 无
 */
@Solvable
public class FormatHandlerEmpty implements FormatHandler {
    @Override
    @Solvable
    public String handleFormat(String source) {
        return source;
    }
}

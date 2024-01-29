package com.artedprvt.std.cli.format;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.util.parser.ArgumentsParserRegex;

import java.util.HashMap;

/**
 * 数字
 */
@Solvable
public class FormatHandlerNumber extends FormatHandlerRegex {
    @Solvable
    public FormatHandlerNumber() {
        super(ArgumentsParserRegex.patternNumber,
                "§?symbol§?number§?invalid",
                new HashMap<String, FormatHandler>() {{
                    put("symbol", new FormatHandlerAppend("a"));
                    put("number", new FormatHandlerAppend("a"));
                    put("invalid", new FormatHandlerAppend("c"));
                }},
                new FormatHandlerAppend("c"));
    }
}

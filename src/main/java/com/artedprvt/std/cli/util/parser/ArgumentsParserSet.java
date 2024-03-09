package com.artedprvt.std.cli.util.parser;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.util.ArgumentsParserInterface;
import com.artedprvt.std.cli.util.ParseResult;

import java.util.Collection;

@ArgumentsParserInterface
@InterfaceView
public class ArgumentsParserSet {
    @InterfaceView
    public static ParseResult parse(
            Collection<String> sets,
            String source) {
        if (sets.contains(source)) {
            return new ParseResult(true);
        }
        return new ParseResult(false);
    }
}

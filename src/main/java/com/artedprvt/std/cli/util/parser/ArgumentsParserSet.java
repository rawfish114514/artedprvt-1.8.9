package com.artedprvt.std.cli.util.parser;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.util.ArgumentsParserInterface;
import com.artedprvt.std.cli.util.ParseResult;

import java.util.Collection;

@ArgumentsParserInterface
@Solvable
public class ArgumentsParserSet {
    @Solvable
    public static ParseResult parse(
            Collection<String> sets,
            String source) {
        if (sets.contains(source)) {
            return new ParseResult(true);
        }
        return new ParseResult(false);
    }
}

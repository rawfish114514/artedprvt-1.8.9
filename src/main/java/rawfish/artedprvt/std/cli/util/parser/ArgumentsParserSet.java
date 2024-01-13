package rawfish.artedprvt.std.cli.util.parser;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.util.ArgumentsParserInterface;
import rawfish.artedprvt.std.cli.util.ParseResult;

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

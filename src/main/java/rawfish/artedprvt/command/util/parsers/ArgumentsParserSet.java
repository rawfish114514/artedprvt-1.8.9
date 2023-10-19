package rawfish.artedprvt.command.util.parsers;

import rawfish.artedprvt.command.util.ArgumentsParserInterface;
import rawfish.artedprvt.command.util.ParseResult;

import java.util.Collection;

@ArgumentsParserInterface
public class ArgumentsParserSet {
    public static ParseResult parse(
            Collection<String> sets,
            String source){
        if(sets.contains(source)){
            return new ParseResult(true);
        }
        return new ParseResult(false);
    }
}

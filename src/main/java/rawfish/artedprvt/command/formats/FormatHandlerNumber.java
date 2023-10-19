package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.util.parsers.ArgumentsParserRegex;

import java.util.HashMap;

/**
 * 数字
 */
public class FormatHandlerNumber extends FormatHandlerRegex {
    public FormatHandlerNumber() {
        super(ArgumentsParserRegex.patternNumber,
                "§?symbol§?number§?invalid",
                new HashMap<String, FormatHandler>(){{
                    put("symbol",new FormatHandlerAppend("a"));
                    put("number",new FormatHandlerAppend("a"));
                    put("invalid",new FormatHandlerAppend("c"));
                }},
                new FormatHandlerAppend("c"));
    }
}

package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.util.parsers.ArgumentsParseRegex;

import java.util.HashMap;

public class FormatHandlerNumber extends FormatHandlerRegex {
    public FormatHandlerNumber() {
        super(ArgumentsParseRegex.patternNumber,
                "§?symbol§?number§?invalid",
                new HashMap<String, FormatHandler>(){{
                    put("symbol",new FormatHandlerAppend("a"));
                    put("number",new FormatHandlerAppend("a"));
                    put("invalid",new FormatHandlerAppend("c"));
                }},
                new FormatHandlerAppend("c"));
    }
}

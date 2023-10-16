package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;

import java.util.HashMap;

public class FormatHandlerNumber extends FormatHandlerRegex {
    public FormatHandlerNumber() {
        super("(?<symbol>[+-]|)(?<number>[0-9]+((\\.[0-9]+)|))(?<invalid>.*)",
                "§?symbol§?number§?invalid",
                new HashMap<String, FormatHandler>(){{
                    put("symbol",new FormatHandlerAppend("a"));
                    put("number",new FormatHandlerAppend("a"));
                    put("invalid",new FormatHandlerAppend("c"));
                }},
                new FormatHandlerAppend("c"));
    }
}

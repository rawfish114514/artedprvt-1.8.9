package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;

import java.util.HashMap;

public class FormatHandlerNumber2 extends FormatHandlerRegex {
    public FormatHandlerNumber2() {
        super("(?<number1>.+)(?<char>\\|)(?<number2>.+)",
                "§?number1§?char§?number2",
                new HashMap<String, FormatHandler>(){{
                    put("number1",new FormatHandlerNumber());
                    put("char",new FormatHandlerAppend("d"));
                    put("number2",new FormatHandlerNumber());
                }},
                new FormatHandlerAppend("c"));
    }
}

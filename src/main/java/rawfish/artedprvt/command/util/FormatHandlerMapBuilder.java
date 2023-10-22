package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.FormatHandler;

import java.util.HashMap;

public class FormatHandlerMapBuilder extends HashMap<String, FormatHandler> {
    public FormatHandlerMapBuilder puts(String string,FormatHandler formatHandler){
        put(string,formatHandler);
        return this;
    }
}

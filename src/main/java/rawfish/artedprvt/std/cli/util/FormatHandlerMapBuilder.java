package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.FormatHandler;

import java.util.HashMap;

@Solvable
public class FormatHandlerMapBuilder extends HashMap<String, FormatHandler> {
    @Solvable
    public FormatHandlerMapBuilder puts(String string,FormatHandler formatHandler){
        put(string,formatHandler);
        return this;
    }
}

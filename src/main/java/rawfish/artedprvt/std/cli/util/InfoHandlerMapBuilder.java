package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.InfoHandler;

import java.util.HashMap;

@Solvable
public class InfoHandlerMapBuilder extends HashMap<String, InfoHandler> {
    @Solvable
    public InfoHandlerMapBuilder puts(String string,InfoHandler infoHandler){
        put(string,infoHandler);
        return this;
    }
}

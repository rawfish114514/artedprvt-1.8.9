package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.InfoHandler;

import java.util.HashMap;

public class InfoHandlerMapBuilder extends HashMap<String, InfoHandler> {
    public InfoHandlerMapBuilder puts(String string,InfoHandler infoHandler){
        put(string,infoHandler);
        return this;
    }
}

package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.infos.InfoHandlerEmpty;
import rawfish.artedprvt.command.infos.InfoHandlerMap;
import rawfish.artedprvt.command.infos.InfoHandlerString;

import java.util.Map;

public class InfoHandlerBuilder {
    public InfoHandler empty(){
        return new InfoHandlerEmpty();
    }

    public InfoHandler map(
            Map<String,InfoHandler> map,
            InfoHandler falseHandler){
        return new InfoHandlerMap(map,falseHandler);
    }

    public InfoHandler string(String string){
        return new InfoHandlerString(string);
    }
}

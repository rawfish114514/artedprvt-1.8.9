package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.info.InfoHandlerEmpty;
import rawfish.artedprvt.std.cli.info.InfoHandlerMap;
import rawfish.artedprvt.std.cli.info.InfoHandlerString;

import java.util.Map;

@Solvable
public class InfoHandlerFactory {
    @Solvable
    public InfoHandler empty(){
        return new InfoHandlerEmpty();
    }

    @Solvable
    public InfoHandler map(
            Map<String,InfoHandler> map,
            InfoHandler falseHandler){
        return new InfoHandlerMap(map,falseHandler);
    }

    @Solvable
    public InfoHandler string(String string){
        return new InfoHandlerString(string);
    }
}

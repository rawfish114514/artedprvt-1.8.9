package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.infos.InfoHandlerString;

public class InfoHandlerBuilder {
    public InfoHandler string(String string){
        return new InfoHandlerString(string);
    }
}

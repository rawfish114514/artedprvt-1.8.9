package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.formats.FormatHandlerAppend;

import java.util.ArrayList;


public class FormatHandlerList extends ArrayList<FormatHandler> {
    public boolean add(String s){
        return add(new FormatHandlerAppend(s));
    }
}

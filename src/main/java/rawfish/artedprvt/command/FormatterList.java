package rawfish.artedprvt.command;

import rawfish.artedprvt.command.formatterimpls.FormatterAppend;

import java.util.ArrayList;


public class FormatterList extends ArrayList<Formatter> {
    public boolean add(String s){
        return add(new FormatterAppend(s));
    }
}

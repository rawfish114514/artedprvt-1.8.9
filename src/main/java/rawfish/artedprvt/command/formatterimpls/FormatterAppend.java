package rawfish.artedprvt.command.formatterimpls;

import rawfish.artedprvt.command.Formatter;

import java.util.ArrayList;

public class FormatterAppend implements Formatter {
    private final String append;

    public FormatterAppend(String append){
        this.append= Formatter.toFormatCode(append);
    }
    @Override
    public String formatting(String source) {
        return append+source;
    }
}

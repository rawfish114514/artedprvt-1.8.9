package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;

public class FormatHandlerAppend implements FormatHandler {
    private final String append;

    public FormatHandlerAppend(String append){
        this.append= FormatHandler.toFormatCode(append);
    }
    @Override
    public String handleFormat(String source) {
        return append+source;
    }
}

package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;

public class FormatHandlerEmpty implements FormatHandler {
    @Override
    public String handleFormat(String source) {
        return source;
    }
}

package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;

/**
 * 无
 */
public class FormatHandlerEmpty implements FormatHandler {
    @Override
    public String handleFormat(String source) {
        return source;
    }
}

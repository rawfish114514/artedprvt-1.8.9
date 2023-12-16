package rawfish.artedprvt.std.cli.format;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.FormatHandler;

/**
 * 无
 */
@Solvable
public class FormatHandlerEmpty implements FormatHandler {
    @Override
    @Solvable
    public String handleFormat(String source) {
        return source;
    }
}

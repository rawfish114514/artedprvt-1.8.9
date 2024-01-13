package rawfish.artedprvt.std.cli.format;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.util.Literals;

/**
 * 附加
 */
@Solvable
public class FormatHandlerAppend implements FormatHandler {
    private final String append;

    /**
     * @param append 附加的格式代码
     */
    @Solvable
    public FormatHandlerAppend(String append) {
        this.append = Literals.fcToString(append);
    }

    @Override
    @Solvable
    public String handleFormat(String source) {
        return append + source;
    }
}

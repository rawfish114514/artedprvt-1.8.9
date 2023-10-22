package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.util.Literals;

/**
 * 附加
 */
public class FormatHandlerAppend implements FormatHandler {
    private final String append;

    /**
     *
     * @param append 附加的格式代码
     */
    public FormatHandlerAppend(String append){
        this.append= Literals.fcToString(append);
    }
    @Override
    public String handleFormat(String source) {
        return append+source;
    }
}

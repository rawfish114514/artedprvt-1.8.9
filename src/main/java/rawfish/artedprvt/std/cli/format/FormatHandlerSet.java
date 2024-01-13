package rawfish.artedprvt.std.cli.format;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.util.ParseResult;
import rawfish.artedprvt.std.cli.util.parser.ArgumentsParserSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 集合
 */
@Solvable
public class FormatHandlerSet implements FormatHandler {
    private Set<String> set;
    private FormatHandler trueHandler;
    private FormatHandler falseHandler;

    /**
     * @param set          集合
     * @param trueHandler  成功处理程序
     * @param falseHandler 失败处理程序
     */
    @Solvable
    public FormatHandlerSet(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler) {
        this.set = new HashSet<>(set);
        this.trueHandler = trueHandler;
        this.falseHandler = falseHandler;
    }

    @Override
    @Solvable
    public String handleFormat(String source) {
        ParseResult result = ArgumentsParserSet.parse(set, source);
        if (result.isCorrect()) {
            return trueHandler.handleFormat(source);
        }
        return falseHandler.handleFormat(source);
    }
}

package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.util.ParseResult;
import rawfish.artedprvt.command.util.parsers.ArgumentsParserSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 集合
 */
public class FormatHandlerSet implements FormatHandler {
    private Set<String> set;
    private FormatHandler trueHandler;
    private FormatHandler falseHandler;

    /**
     *
     * @param set 集合
     * @param trueHandler 成功处理程序
     * @param falseHandler 失败处理程序
     */
    public FormatHandlerSet(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler){
        this.set = new HashSet<>(set);
        this.trueHandler=trueHandler;
        this.falseHandler=falseHandler;
    }

    @Override
    public String handleFormat(String source) {
        ParseResult result= ArgumentsParserSet.parse(set,source);
        if(result.isCorrect()){
            return trueHandler.handleFormat(source);
        }
        return falseHandler.handleFormat(source);
    }
}

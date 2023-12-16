package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.format.*;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

@Solvable
public class FormatHandlerFactory {
    @Solvable
    public FormatHandler append(String string){
        return new FormatHandlerAppend(string);
    }

    @Solvable
    public FormatHandler empty(){
        return new FormatHandlerEmpty();
    }

    @Solvable
    public FormatHandler number(){
        return new FormatHandlerNumber();
    }

    @Solvable
    public FormatHandler regex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler){
        return new FormatHandlerRegex(regex,template,groupHandlerMap,falseHandler);
    }

    @Solvable
    public FormatHandler regex(
            Pattern regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler){
        return new FormatHandlerRegex(regex,template,groupHandlerMap,falseHandler);
    }

    @Solvable
    public FormatHandler set(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler){
        return new FormatHandlerSet(set,trueHandler,falseHandler);
    }
}

package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.formats.*;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class FormatHandlerFactory {
    public FormatHandler append(String string){
        return new FormatHandlerAppend(string);
    }

    public FormatHandler empty(){
        return new FormatHandlerEmpty();
    }

    public FormatHandler number(){
        return new FormatHandlerNumber();
    }

    public FormatHandler regex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler){
        return new FormatHandlerRegex(regex,template,groupHandlerMap,falseHandler);
    }

    public FormatHandler regex(
            Pattern regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler){
        return new FormatHandlerRegex(regex,template,groupHandlerMap,falseHandler);
    }

    public FormatHandler set(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler){
        return new FormatHandlerSet(set,trueHandler,falseHandler);
    }
}

package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.formats.*;
import rawfish.artedprvt.core.engine.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;


public class FormatHandlerListBuilder extends ArrayList<FormatHandler> {
    public FormatHandlerListBuilder adds(FormatHandler formatHandler){
        add(formatHandler);
        return this;

    }
    public FormatHandlerListBuilder append(String string){
        add(new FormatHandlerAppend(string));
        return this;
    }

    public FormatHandlerListBuilder append(String... strings){
        for(String string:strings){
            append(string);
        }
        return this;
    }

    public FormatHandlerListBuilder empty(){
        add(new FormatHandlerEmpty());
        return this;
    }

    public FormatHandlerListBuilder number(){
        add(new FormatHandlerNumber());
        return this;
    }

    public FormatHandlerListBuilder regex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler){
        add(new FormatHandlerRegex(regex,template,groupHandlerMap,falseHandler));
        return this;
    }

    public FormatHandlerListBuilder regex(
            Pattern regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler){
        add(new FormatHandlerRegex(regex,template,groupHandlerMap,falseHandler));
        return this;
    }

    public FormatHandlerListBuilder set(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler){
        add(new FormatHandlerSet(set,trueHandler,falseHandler));
        return this;
    }
}

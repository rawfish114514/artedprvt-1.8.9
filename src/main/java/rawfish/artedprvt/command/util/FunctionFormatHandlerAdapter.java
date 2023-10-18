package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.FormatHandler;

import java.util.function.Function;

public class FunctionFormatHandlerAdapter implements FormatHandler {
    private Function function;
    public FunctionFormatHandlerAdapter(Function function){
        this.function=function;
    }
    @Override
    public String handleFormat(String source) {
        //proxy
        return String.valueOf(function.apply(source));
    }
}

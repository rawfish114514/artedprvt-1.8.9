package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.InfoHandler;

import java.util.function.Function;

public class FunctionInfoHandlerAdapter implements InfoHandler {
    private Function function;
    public FunctionInfoHandlerAdapter(Function function){
        this.function=function;
    }
    @Override
    public String handleInfo(String source) {
        //proxy
        return String.valueOf(function.apply(source));
    }
}

package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;

import java.util.function.Function;

/**
 * 封装使用处理程序的方法
 */
public class Literals {
    /**
     * 适配器
     */
    public static class Adapter{
        public static FormatHandler format(Function function){
            return new FunctionFormatHandlerAdapter(function);
        }

        public static InfoHandler info(Function function){
            format(String::valueOf);
            return new FunctionInfoHandlerAdapter(function);
        }
    }
}

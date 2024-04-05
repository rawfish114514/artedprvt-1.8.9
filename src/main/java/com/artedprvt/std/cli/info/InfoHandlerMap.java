package com.artedprvt.std.cli.info;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.util.ParseResult;
import com.artedprvt.std.cli.util.parser.ArgumentsParserSet;

import java.util.Map;

/**
 * 映射
 */
@InterfaceView
public class InfoHandlerMap implements InfoHandler {
    private Map<String, InfoHandler> map;
    private InfoHandler falseHandler;

    @InterfaceView
    public InfoHandlerMap(
            Map<String, InfoHandler> map,
            InfoHandler falseHandler) {
        this.map = map;
        this.falseHandler = falseHandler;
    }

    @Override
    @InterfaceView
    public String handleInfo(String source) {
        ParseResult result = ArgumentsParserSet.parse(map.keySet(), source);
        if (result.isCorrect()) {
            return map.get(source).handleInfo(source);
        }
        return falseHandler.handleInfo(source);
    }
}

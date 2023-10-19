package rawfish.artedprvt.command.infos;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.ParseResult;
import rawfish.artedprvt.command.util.parsers.ArgumentsParserSet;

import java.util.Map;

/**
 * 映射
 */
public class InfoHandlerMap implements InfoHandler {
    private Map<String,InfoHandler> map;
    private InfoHandler falseHandler;
    public InfoHandlerMap(
            Map<String,InfoHandler> map,
            InfoHandler falseHandler){
        this.map=map;
        this.falseHandler=falseHandler;
    }

    @Override
    public String handleInfo(String source) {
        ParseResult result= ArgumentsParserSet.parse(map.keySet(),source);
        if(result.isCorrect()){
            return map.get(source).handleInfo(source);
        }
        return falseHandler.handleInfo(source);
    }
}

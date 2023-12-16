package rawfish.artedprvt.std.cli.info;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.ParseResult;
import rawfish.artedprvt.std.cli.util.parser.ArgumentsParserSet;

import java.util.Map;

/**
 * 映射
 */
@Solvable
public class InfoHandlerMap implements InfoHandler {
    private Map<String,InfoHandler> map;
    private InfoHandler falseHandler;

    @Solvable
    public InfoHandlerMap(
            Map<String,InfoHandler> map,
            InfoHandler falseHandler){
        this.map=map;
        this.falseHandler=falseHandler;
    }

    @Override
    @Solvable
    public String handleInfo(String source) {
        ParseResult result= ArgumentsParserSet.parse(map.keySet(),source);
        if(result.isCorrect()){
            return map.get(source).handleInfo(source);
        }
        return falseHandler.handleInfo(source);
    }
}

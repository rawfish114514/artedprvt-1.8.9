package rawfish.artedprvt.command.infos;

import rawfish.artedprvt.command.util.ParseResult;
import rawfish.artedprvt.command.util.parsers.ArgumentsParserRegex;

public class InfoHandlerNumberInspect extends InfoHandlerInspect{
    public InfoHandlerNumberInspect() {
        super("number");
    }

    @Override
    public String handleInfo(String source) {
        ParseResult result= ArgumentsParserRegex.parse(
                ArgumentsParserRegex.patternNumber,
                ArgumentsParserRegex.groupsNumber,
                source);
        if(result.isCorrect()) {
            if (!result.get("invalid").isEmpty()) {
                return inspect(result.get("invalid"), false);
            }
        }
        return inspect(source,result.isCorrect());
    }
}

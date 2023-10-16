package rawfish.artedprvt.command.infos;

import rawfish.artedprvt.command.util.ParseResult;
import rawfish.artedprvt.command.util.parsers.ArgumentsParseRegex;

public class InfoHandlerNumberInspect extends InfoHandlerInspect{
    public InfoHandlerNumberInspect() {
        super("number");
    }

    @Override
    public String handleInfo(String source) {
        ParseResult result= ArgumentsParseRegex.parse(
                ArgumentsParseRegex.patternNumber,
                ArgumentsParseRegex.groupsNumber,
                source);
        if(result.isCorrect()) {
            if (!result.get("invalid").isEmpty()) {
                return inspect(result.get("invalid"), false);
            }
        }
        return inspect(source,result.isCorrect());
    }
}

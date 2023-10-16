package rawfish.artedprvt.command.util;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentsParser {
    public static ParseResult parseRegex(
            Pattern pattern,
            Set<String> groups,
            String source){
        Matcher matcher=pattern.matcher(source);
        if(matcher.matches()){
            ParseResult result=new ParseResult(true);
            String value;
            for(String group:groups){
                value=matcher.group(group);
                if(value!=null){
                    result.put(group,value);
                }

                //System.out.println(group+": "+matcher.group(group));
            }
            return result;
        }
        return new ParseResult(false);
    }
}

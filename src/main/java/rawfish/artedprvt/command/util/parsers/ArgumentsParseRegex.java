package rawfish.artedprvt.command.util.parsers;

import rawfish.artedprvt.command.util.ArgumentsParserInterface;
import rawfish.artedprvt.command.util.ParseResult;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ArgumentsParserInterface
public class ArgumentsParseRegex {
    public static ParseResult parse(
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


    public static final Pattern patternNumber= Pattern.compile(
            "(?<symbol>[+-]|)(?<number>[0-9]+((\\.[0-9]+)|))(?<invalid>.*)");

    public static final Set<String> groupsNumber=new HashSet<String>(){{
        add("symbol");
        add("number");
        add("invalid");
    }};
}

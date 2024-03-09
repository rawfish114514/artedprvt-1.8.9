package com.artedprvt.std.cli.util.parser;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.util.ArgumentsParserInterface;
import com.artedprvt.std.cli.util.ParseResult;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ArgumentsParserInterface
@InterfaceView
public class ArgumentsParserRegex {
    @InterfaceView
    public static ParseResult parse(
            Pattern pattern,
            Collection<String> groups,
            String source) {
        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
            ParseResult result = new ParseResult(true);
            String value;
            for (String group : groups) {
                value = matcher.group(group);
                if (value != null) {
                    result.put(group, value);
                }

                //System.out.println(group+": "+matcher.group(group));
            }
            return result;
        }
        return new ParseResult(false);
    }


    @InterfaceView
    public static final Pattern patternNumber = Pattern.compile(
            "(?<symbol>[+-]|)(?<number>[0-9]+((\\.[0-9]+)|))(?<invalid>.*)");

    @InterfaceView
    public static final Set<String> groupsNumber = new HashSet<String>() {{
        add("symbol");
        add("number");
        add("invalid");
    }};
}

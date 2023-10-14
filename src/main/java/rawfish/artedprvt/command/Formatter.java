package rawfish.artedprvt.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Formatter {
    String formatting(String source);

    Pattern format= Pattern.compile("[0-9a-fkm-or]*");
    static String toFormatCode(String value){
        Matcher matcher=format.matcher(value);
        if(matcher.matches()) {
            char[] chars = value.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                sb.append('§');
                sb.append(c);
            }
            return sb.toString();
        }
        return "";
    }
}

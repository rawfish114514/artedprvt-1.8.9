package rawfish.artedprvt.core;

import rawfish.artedprvt.mi.group.ClassGroupMi;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassGroupSystem {
    private static Map<String, List<Class>> map=new LinkedHashMap<>();

    public static List<Class> get(String name){
        return map.get(name);
    }

    private static Pattern pattern= Pattern.compile("(([a-zA-Z_][0-9a-zA-Z_]*\\.)*)([a-zA-Z_][0-9a-zA-Z_]*)");
    public static void reg(ClassGroup classGroup){
        String name=classGroup.getName();
        Matcher matcher= pattern.matcher(name);
        if(matcher.matches()){
            map.put(name, classGroup.getClasses());
        }
    }

    public static String getValue(){
        Set<String> nameList=map.keySet();
        return String.join(", ",nameList);
    }
}

package rawfish.artedprvt.core;

import rawfish.artedprvt.mi.ClassGroupMi;

import java.util.*;

public class ClassGroupSystem {
    private static Map<String, List<Class>> map=new LinkedHashMap<>();

    public static List<Class> get(String name){
        return map.get(name);
    }

    public static void reg(ClassGroup classGroup){
        map.put(classGroup.getName(), classGroup.getClasses());
    }

    public static String getValue(){
        Set<String> nameList=map.keySet();
        return String.join(", ",nameList);
    }

    static {
        reg(new ClassGroupMi());
    }
}

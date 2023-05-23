package org.mozilla.javascript.mapping;


import org.mozilla.javascript.Scriptable;

import java.util.HashMap;
import java.util.Map;

public class ClassRegisterer {
    public static Map<String,MemberMapping> classMap=new HashMap<>();;

    public static void registerClass(String className,MemberMapping mapping){
        classMap.put(className,mapping);
    }

    public static MappingMatcher matcher=null;

    public static void setMatcher(MappingMatcher matcher){
        ClassRegisterer.matcher=matcher;
    }

    public static boolean isMapping(Scriptable scope, Class clas){
        if(matcher==null){
            return false;
        }
        return matcher.match(scope,clas);
    }

    public interface MappingMatcher{
        public boolean match(Scriptable scope, Class clas);
    }
}

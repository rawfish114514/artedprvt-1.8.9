package rawfish.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 语言
 */
public class ScriptLanguage {
    private static List<ScriptLanguage> languages=new ArrayList<>();
    public static void reg(ScriptLanguage scriptLanguage){
        languages.add(scriptLanguage);
    }

    /**
     * 我认为 语言缩写与代码文件后缀是相等的
     */
    final String abbr;

    public ScriptLanguage(String abbr){
        this.abbr=abbr;
    }

    public String getAbbr(){
        return abbr;
    }

    public static ScriptLanguage abbrOf(String abbr){
        ScriptLanguage scriptLanguage;
        for(int i=0;i<languages.size();i++){
            scriptLanguage=languages.get(i);
            if(scriptLanguage.abbr.equals(abbr)){
                return scriptLanguage;
            }
        }
        return null;
    }

    public static List<ScriptLanguage> values(){
        return languages;
    }
}

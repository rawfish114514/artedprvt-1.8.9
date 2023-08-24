package rawfish.artedprvt.core;

/**
 * 语言
 */
public enum ScriptLanguage {
    JAVASCRIPT("js"),
    ;

    /**
     * 我认为 语言缩写与代码文件后缀是相等的
     */
    final String abbr;

    ScriptLanguage(String abbr){
        this.abbr=abbr;
    }

    public String getAbbr(){
        return abbr;
    }

    public static ScriptLanguage abbrOf(String abbr){
        ScriptLanguage scriptLanguage;
        ScriptLanguage[] values=values();
        for(int i=0;i<values.length;i++){
            scriptLanguage=values[i];
            if(scriptLanguage.abbr.equals(abbr)){
                return scriptLanguage;
            }
        }
        return null;
    }
}

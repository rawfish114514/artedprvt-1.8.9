package rawfish.artedprvt.core;

/**
 * 语言
 */
public enum ScriptLanguage {
    JAVASCRIPT("js"),
    ;

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

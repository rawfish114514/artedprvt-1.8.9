package rawfish.artedprvt.core.script;

/**
 * 语言
 */
public class ScriptLanguage {
    /**
     * 我认为 语言缩写与代码文件后缀是相等的
     */
    public final String abbr;

    public ScriptLanguage(String abbr) {
        this.abbr = abbr;
    }

    public String getAbbr() {
        return abbr;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ScriptLanguage && ((ScriptLanguage) obj).abbr.equals(abbr);
    }

    public boolean equals(String abbr) {
        return abbr.equals(this.abbr);
    }
}

package rawfish.artedprvt.core;

public class ScriptStackElement {
    private String moduleName;
    private int line;
    public ScriptStackElement(String moduleName, int line){
        this.moduleName=moduleName;
        this.line=line;
    }

    @Override
    public String toString(){
        return "  §bat §a"+moduleName+"§7:§c"+line;
    }
}

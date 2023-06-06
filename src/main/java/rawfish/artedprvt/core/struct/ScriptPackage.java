package rawfish.artedprvt.core.struct;

/**
 * 包
 */
public class ScriptPackage {
    private String packageName;
    public ScriptPackage(String packageName){
        this.packageName=packageName;
    }

    public String getPackageName(){
        return packageName;

    }

    @Override
    public String toString(){
        return "ScriptPackage(name="+(packageName.equals("")?"default":packageName)+")";
    }
}

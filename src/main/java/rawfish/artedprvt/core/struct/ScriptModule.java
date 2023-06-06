package rawfish.artedprvt.core.struct;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptLanguage;

/**
 * 模块
 * 一个脚本文件表示一个模块
 * 每个模块只能执行一次
 */
public class ScriptModule {
    private ScriptPackage scriptPackage;
    private String moduleName;
    private String moduleFullName;
    private String source;
    private ScriptLanguage scriptLanguage;
    private Object export=null;
    private int status=LOAD;

    /**
     * 创建模块
     * @param scriptPackage 包
     * @param moduleName 模块名
     * @param source 源码
     * @param scriptLanguage 语言
     */
    public ScriptModule(
            ScriptPackage scriptPackage,
            String moduleName,
            String source,
            ScriptLanguage scriptLanguage){
        this.scriptPackage = scriptPackage;
        this.moduleName=moduleName;
        this.source = source;
        this.scriptLanguage = scriptLanguage;

        moduleFullName=scriptLanguage.getAbbr()+":"+
                (scriptPackage.getPackageName().equals("")?"":scriptPackage.getPackageName()+".")+
                moduleName;
    }

    public ScriptPackage getScriptPackage() {
        return scriptPackage;
    }

    public String getModuleName(){
        return moduleName;
    }

    public String getModuleFullName(){
        return moduleFullName;
    }

    public String getSource() {
        return source;
    }

    public ScriptLanguage getScriptLanguage() {
        return scriptLanguage;
    }

    public Object getExport(){
        return export;
    }

    public void setExport(Object object){
        export=object;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        if(status==LOAD||status==RUN||status==END){
            this.status=status;
        }else{
            ScriptExceptions.exception("错误的状态码: "+status);
        }
    }

    @Override
    public String toString(){
        return "ScriptModule(name="+moduleFullName+")";
    }


    public static final int
    LOAD=0,
    RUN=1,
    END=2;
}

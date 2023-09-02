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
    private String moduleFullNameLiteral;
    private String source;
    private ScriptLanguage scriptLanguage;
    private Object export=null;
    private int status=LOAD;

    /**
     * 创建模块
     * @param scriptPackage 包
     * @param moduleName 模块名 module
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

        String p;
        if(scriptPackage.getPackageName().startsWith("default")){
            p=scriptPackage.getPackageName().substring(7);
        }else {
            p = scriptPackage.getPackageName();
        }
        if(p.length()>0&&p.charAt(0)=='.'){
            p=p.substring(1);
        }

        moduleFullNameLiteral=scriptLanguage.getAbbr()+":"+
                (p.equals("")?"":p+".")+
                moduleName;
    }

    public ScriptPackage getScriptPackage() {
        return scriptPackage;
    }

    /**
     *
     * @return 模块名 module
     */
    public String getModuleName(){
        return moduleName;
    }

    /**
     *
     * @return 模块完整名 abbr:package.module
     */
    public String getModuleFullName(){
        return moduleFullName;
    }

    /**
     * 模块完整名字面 abbr:package.module (package ignore default)
     * @return
     */
    public String getModuleFullNameLiteral(){
        return moduleFullNameLiteral;
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

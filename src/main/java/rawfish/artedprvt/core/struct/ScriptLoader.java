package rawfish.artedprvt.core.struct;

import rawfish.artedprvt.core.ScriptExceptions;
import rawfish.artedprvt.core.ScriptLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 脚本加载器
 */
public class ScriptLoader {
    private static Pattern moduleFullNamePattern=Pattern.compile("([a-z]+:|)(([a-zA-Z_][0-9a-zA-Z_]*\\.)*)([a-zA-Z_][0-9a-zA-Z_]*)");
    private static List<String> packageIgnore=new ArrayList<String>(){{
        add("default");
    }};
    private FileLoader fileLoader;
    private Map<String, ScriptPackage> packageMap;
    private Map<String,ScriptModule> moduleMap;
    public ScriptLoader(FileLoader fileLoader){
        this.fileLoader=fileLoader;
        packageMap=new HashMap<>();
        moduleMap=new HashMap<>();
    }

    /**
     * 加载模块
     * a/b/c.js文件的模块名可以用以下格式
     * "js:a.b.c"
     * "a.b.c"
     * 其中冒号前的js是脚本语言缩写 一般是脚本文件后缀
     * 如果省略模块类型 则自动匹配
     * 冒号后的最后一个点之前的部分是包名 这里是"a.b"
     * 最后一个点后的部分是短模块名 这里是"c"
     *
     * 这个方法自动注册包和模块 并返回模块的唯一实例
     * @param moduleFullName 模块完整名
     * @return
     */
    public ScriptModule loadModule(String moduleFullName){
        Matcher matcher= moduleFullNamePattern.matcher(moduleFullName);
        if(matcher.matches()){
            String abbr=matcher.group(1);
            String packagee=matcher.group(2);
            String module=matcher.group(4);
            if(!packagee.equals("")){
                packagee=packagee.substring(0,packagee.length()-1);
            }
            ScriptPackage scriptPackage=packageMap.get(packagee);
            if(scriptPackage==null){
                String item0;
                int ind=packagee.indexOf(".");
                if(ind==-1){
                    item0=packagee;
                }else{
                    item0=packagee.substring(0,ind);
                }
                if(packageIgnore.contains(item0)){
                    ScriptExceptions.exceptionUseIgnorePackageName(packagee);
                }
                scriptPackage=new ScriptPackage(packagee);
                packageMap.put(packagee,scriptPackage);
            }
            ScriptModule scriptModule;
            if(abbr.equals("")){
                //自动加载
                ScriptLanguage scriptLanguage;
                ScriptLanguage[] values=ScriptLanguage.values();
                for(int i=0;i<values.length;i++){
                    scriptLanguage=values[i];
                    abbr=scriptLanguage.getAbbr();
                    String newModuleFullName=abbr+":"+moduleFullName;
                    scriptModule=moduleMap.get(newModuleFullName);
                    if(scriptModule==null){
                        String path;
                        if(packagee.equals("")){
                            path="script/"+module+"."+abbr;
                        }else{
                            path="script/"+packagee.replace('.','/')+"/"+module+"."+abbr;
                        }
                        String source=fileLoader.getString(path);
                        if(source!=null){
                            scriptModule=new ScriptModule(scriptPackage,module,source,scriptLanguage);
                            moduleMap.put(newModuleFullName,scriptModule);
                            return scriptModule;
                        }
                    }else{
                        return scriptModule;
                    }
                }
            }else{
                //指定加载
                abbr=abbr.substring(0,abbr.length()-1);
                ScriptLanguage scriptLanguage=ScriptLanguage.abbrOf(abbr);
                if(scriptLanguage!=null){
                    scriptModule=moduleMap.get(moduleFullName);
                    if(scriptModule==null){
                        String path;
                        if(packagee.equals("")){
                            path="script/"+module+"."+abbr;
                        }else{
                            path="script/"+packagee.replace('.','/')+"/"+module+"."+abbr;
                        }
                        String source=fileLoader.getString(path);
                        if(source==null){
                            ScriptExceptions.exceptionNoFoundModule(path);
                        }
                        scriptModule=new ScriptModule(scriptPackage,module,source,scriptLanguage);
                        moduleMap.put(moduleFullName,scriptModule);
                    }
                    return scriptModule;
                }else{
                    ScriptExceptions.exceptionNoDefindLanguageAbbr(abbr);
                }
            }
        }else{
            ScriptExceptions.exceptionModuleFullNameFormat(moduleFullName);
        }
        ScriptExceptions.exceptionNoFoundModule(moduleFullName);
        return null;
    }

    public Map<String, ScriptPackage> getPackageMap() {
        return packageMap;
    }

    public Map<String, ScriptModule> getModuleMap() {
        return moduleMap;
    }
}

package rawfish.artedprvt.core.script.struct;

import rawfish.artedprvt.core.script.ScriptExceptions;
import rawfish.artedprvt.core.script.ScriptLanguage;
import rawfish.artedprvt.core.script.engine.Engines;
import rawfish.artedprvt.core.localization.types.SES;

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
    private static Pattern packageNamePattern=Pattern.compile("([a-zA-Z_][0-9a-zA-Z_]*\\.)*([a-zA-Z_][0-9a-zA-Z_]*)");
    private static Pattern moduleNamePattern=Pattern.compile("[a-zA-Z_][0-9a-zA-Z_]*");

    private static List<String> packageIgnore=new ArrayList<String>(){{
        add("default");
    }};
    private FileLoader fileLoader;
    private Map<String, ScriptPackage> packageMap;
    public ScriptLoader(FileLoader fileLoader){
        this.fileLoader=fileLoader;
        packageMap=new HashMap<>();
    }

    public synchronized void loadAll(){
        List<String> paths=fileLoader.getPaths();
        for(String path:paths){
            if(path.startsWith("script/")){
                String sp=path.substring(7).replace('/','.');
                int lastIndexOf=sp.lastIndexOf('.');
                String abbr=sp.substring(lastIndexOf+1);
                if(lastIndexOf==-1){
                    abbr="?";
                }
                ScriptLanguage scriptLanguage=existScriptLanguage(abbr,sp);

                String packageAndModule=sp.substring(0,lastIndexOf);
                lastIndexOf=packageAndModule.lastIndexOf('.');
                String pack;
                String module;
                if(lastIndexOf==-1){
                    pack="default";
                    module=packageAndModule;
                }else{
                    pack=packageAndModule.substring(0,lastIndexOf);
                    module=packageAndModule.substring(lastIndexOf+1);
                    matcherPackageName(pack);
                    inspectPackageIgnore(pack);
                }
                matcherModuleName(module);

                ScriptPackage scriptPackage=packageMap.get(pack);
                if(scriptPackage==null){
                    scriptPackage=new ScriptPackage(pack);
                    packageMap.put(pack,scriptPackage);
                }

                ScriptModule scriptModule=new ScriptModule(
                        scriptPackage,
                        module,
                        fileLoader.getContent(path),
                        scriptLanguage);

                scriptPackage.add(scriptModule);
            }
        }
    }

    /**
     * 获取模块
     * @param moduleFullName 模块完整名 abbr?:package.module
     * @return
     */
    public synchronized ScriptModule getModule(String moduleFullName){
        Matcher matcher=matcherModuleFullName(moduleFullName);
        String abbr=matcher.group(1);
        String pack=matcher.group(2);
        String module=matcher.group(4);
        if(!pack.equals("")){
            pack=pack.substring(0,pack.length()-1);
            inspectPackageIgnore(pack);
        }else{
            pack="default";
        }
        ScriptPackage scriptPackage=existScriptPackage(pack);
        ScriptModule scriptModule;
        if(abbr.equals("")){
            //自动加载
            for(ScriptLanguage scriptLanguage:Engines.getLanguages()){
                scriptModule=scriptPackage.get(module,scriptLanguage);
                if(scriptModule!=null){
                    return scriptModule;
                }
            }
        }else{
            //指定加载
            abbr=abbr.substring(0,abbr.length()-1);
            ScriptLanguage scriptLanguage=existScriptLanguage(abbr,moduleFullName);
            scriptModule=scriptPackage.get(module,scriptLanguage);
            if(scriptModule!=null){
                return scriptModule;
            }
        }
        ScriptExceptions.exception(SES.ses1,moduleFullName);
        return null;
    }

    public ScriptLanguage existScriptLanguage(String abbr,String source){
        ScriptLanguage scriptLanguage= Engines.getLanguageOfAbbr(abbr);
        if(scriptLanguage==null){
            ScriptExceptions.exception(SES.ses2,abbr,source);
        }
        return scriptLanguage;
    }

    public ScriptPackage existScriptPackage(String pack){
        ScriptPackage scriptPackage=packageMap.get(pack);
        if(scriptPackage==null){
            ScriptExceptions.exception(SES.ses3,pack);
        }
        return scriptPackage;
    }

    public Matcher matcherPackageName(String pack){
        Matcher packageMatcher=packageNamePattern.matcher(pack);
        if(!packageMatcher.matches()){
            ScriptExceptions.exception(SES.ses4,pack);
        }
        return packageMatcher;
    }

    public Matcher matcherModuleName(String module){
        Matcher moduleMatcher=moduleNamePattern.matcher(module);
        if(!moduleMatcher.matches()){
            ScriptExceptions.exception(SES.ses5,module);
        }
        return moduleMatcher;
    }

    public Matcher matcherModuleFullName(String moduleFullName){
        Matcher matcher= moduleFullNamePattern.matcher(moduleFullName);
        if(!matcher.matches()){
            ScriptExceptions.exception(SES.ses6,moduleFullName);
        }
        return matcher;
    }

    public synchronized void inspectPackageIgnore(String pack){
        for(String ignore:packageIgnore){
            if(pack.startsWith(ignore)){
                ScriptExceptions.exception(SES.ses7,ignore,pack);
            }
        }
    }


    public Map<String, ScriptPackage> getPackageMap() {
        return packageMap;
    }
}

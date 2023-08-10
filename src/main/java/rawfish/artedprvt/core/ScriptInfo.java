package rawfish.artedprvt.core;


import com.moandjiezana.toml.Toml;

import java.util.Map;

/**
 * 脚本配置
 */
public class ScriptInfo {
    private ScriptInfo(){}

    private String name="Example";
    private String id="example";
    private String version="1.0";
    private String mcversion="all";
    private String module="main";
    private String author="steve";
    private String description="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMcversion() {
        return mcversion;
    }

    public void setMcversion(String mcversion) {
        this.mcversion = mcversion;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 解析脚本配置
     * @param str
     * @return
     */
    public static ScriptInfo parse(String str){
        ScriptInfo scriptInfo=null;
        Map<String,Object> result=new Toml().read(str).toMap();
        Object infoVersion=result.get("info");
        if(infoVersion==null){
            infoVersion="1";
        }
        if(infoVersion.equals("1")){
            scriptInfo=parse1(result);
        }
        return scriptInfo;
    }

    /**
     * 检查脚本配置是否合法
     * @param scriptInfo
     * @throws Exception
     */
    public static void inspect(ScriptInfo scriptInfo) throws Exception{
        String name=scriptInfo.getName();
        if(name.length()>24){
            ScriptExceptions.exceptionInfoFieldFormat("name",name);
        }
    }

    /**
     * 用配置版本1解析
     * @param map
     * @return
     */
    public static ScriptInfo parse1(Map<String,Object> map){
        ScriptInfo scriptInfo=new ScriptInfo();
        scriptInfo.name=String.valueOf(map.get("name"));
        scriptInfo.id=String.valueOf(map.get("id"));
        scriptInfo.version=String.valueOf(map.get("version"));
        scriptInfo.mcversion=String.valueOf(map.get("mcversion"));
        scriptInfo.module=String.valueOf(map.get("module"));
        scriptInfo.author=String.valueOf(map.get("author"));
        scriptInfo.description=String.valueOf(map.get("description"));
        return scriptInfo;
    }
}

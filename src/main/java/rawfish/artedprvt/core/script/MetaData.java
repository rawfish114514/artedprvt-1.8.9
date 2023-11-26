package rawfish.artedprvt.core.script;


import com.electronwill.toml.Toml;
import rawfish.artedprvt.core.localization.types.SES;

import java.util.Map;

/**
 * 脚本配置
 */
public class MetaData {
    private MetaData(){}

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
    public static MetaData parse(String str){
        MetaData metadata =null;
        Map<String,Object> result=Toml.read(str);
        Object infoVersion=result.get("info");
        if(infoVersion==null){
            infoVersion="1";
        }
        if(infoVersion.equals("1")){
            metadata =parse1(result);
        }
        return metadata;
    }

    /**
     * 检查脚本配置是否合法
     * @param metadata
     * @throws Exception
     */
    public static void inspect(MetaData metadata) throws Exception{
        String name= metadata.getName();
        if(name.length()>24){
            ScriptExceptions.exception(SES.ses8, "name");
        }
    }

    /**
     * 用配置版本1解析
     * @param map
     * @return
     */
    public static MetaData parse1(Map<String,Object> map){
        MetaData metadata =new MetaData();
        metadata.name=String.valueOf(map.get("name"));
        metadata.id=String.valueOf(map.get("id"));
        metadata.version=String.valueOf(map.get("version"));
        metadata.mcversion=String.valueOf(map.get("mcversion"));
        metadata.module=String.valueOf(map.get("module"));
        metadata.author=String.valueOf(map.get("author"));
        metadata.description=String.valueOf(map.get("description")).replace("\r","").trim();
        return metadata;
    }
}

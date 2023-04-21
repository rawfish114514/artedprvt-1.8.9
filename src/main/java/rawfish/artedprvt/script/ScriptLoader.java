package rawfish.artedprvt.script;

/**
 * 脚本加载器
 */
public class ScriptLoader {
    public FileLoader fileLoader;
    public ScriptLoader(FileLoader fileLoader){
        this.fileLoader=fileLoader;
    }

    public String readScript(String pack){
        return fileLoader.readFile("script/"+pack.replace('.','/')+".js");
    }
}

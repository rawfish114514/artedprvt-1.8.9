package rawfish.artedprvt.script;

/**
 * 文件加载器
 */
public interface FileLoader {
    /**
     * 加载文件
     * @param appendable
     * @return
     */
    public String readFile(String appendable);
}
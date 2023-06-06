package rawfish.artedprvt.core.struct;

/**
 * 文件加载器
 */
public interface FileLoader {
    /**
     * 加载文件
     * 要求加载失败时返回null
     * @param appendable
     * @return
     */
    public String readFile(String appendable);
}
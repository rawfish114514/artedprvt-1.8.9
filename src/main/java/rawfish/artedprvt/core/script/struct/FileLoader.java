package rawfish.artedprvt.core.script.struct;

import java.io.InputStream;
import java.util.List;

/**
 * 文件加载器
 */
public interface FileLoader {
    /**
     * 获取文件内容
     * @param path 路径
     * @return 返回这个文件的文本 使用UTF-8编码 获取失败则返回null
     */
    String getContent(String path);

    /**
     * 获取文件输入流
     * @param path 路径
     * @return 返回这个文件的输入流 获取失败则返回null
     */
    InputStream getInputStream(String path);

    /**
     * 获取文件是否存在
     * @param path 路径
     * @return 返回这个文件或目录是否存在
     */
    boolean isExists(String path);

    /**
     * 获取所有文件的路径
     * 不包括目录
     * @return
     */
    List<String> getPaths();
}
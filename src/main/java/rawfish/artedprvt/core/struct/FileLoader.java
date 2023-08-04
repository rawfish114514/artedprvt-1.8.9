package rawfish.artedprvt.core.struct;

import java.io.InputStream;

/**
 * 文件加载器
 */
public interface FileLoader {
    /**
     * 获取文件内容
     * 要求获取失败时返回null
     * @param appendable
     * @return
     */
    String getString(String appendable);

    /**
     * 获取文件内容输入流
     * 要求获取失败时返回null
     * @param appendable
     * @return
     */
    InputStream getInputStream(String appendable);
}
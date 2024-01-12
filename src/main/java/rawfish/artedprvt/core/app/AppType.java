package rawfish.artedprvt.core.app;

import java.net.URL;
import java.util.List;

/**
 * 应用程序类型
 *
 * @param <T> 应用程序进程
 */
public interface AppType<T extends AppProcess<T>> {
    /**
     * 可用的扩展名
     *
     * @return
     */
    List<String> extensions();

    /**
     * 创建应用程序目标
     *
     * @param url
     * @return
     * @throws Exception
     */
    AppTarget<T> target(URL url) throws Exception;
}

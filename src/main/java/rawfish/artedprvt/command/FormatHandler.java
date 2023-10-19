package rawfish.artedprvt.command;

/**
 * 格式处理程序
 */
public interface FormatHandler {
    /**
     * 处理格式
     * @param source 参数
     * @return 返回处理后的参数 要求可见长度和参数长度相等
     */
    String handleFormat(String source);
}

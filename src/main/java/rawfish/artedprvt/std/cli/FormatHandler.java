package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;

/**
 * 格式处理程序
 */
@Solvable
public interface FormatHandler {
    /**
     * 处理格式
     * @param source 参数
     * @return 返回处理后的参数 要求可见长度和参数长度相等
     */
    @Solvable
    String handleFormat(String source);
}

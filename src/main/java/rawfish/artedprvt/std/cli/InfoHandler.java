package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;

/**
 * 信息处理程序
 */
@Solvable
public interface InfoHandler{
    /**
     * 处理信息
     * @param source 参数
     * @return 返回这个参数的信息
     */
    @Solvable
    String handleInfo(String source);
}

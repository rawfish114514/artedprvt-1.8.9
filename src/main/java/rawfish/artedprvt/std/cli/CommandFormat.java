package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;

import java.util.List;

@Solvable
public interface CommandFormat {
    /**
     * 参数格式
     * 在参数前添加格式代码
     * 只能包含格式代码 0-f kmnor
     * 否则被解释为无格式
     * 不写 小节
     *
     * @param args 完整的参数列表
     * @return 返回参数的格式列表 不能为null
     */
    @Solvable
    List<? extends FormatHandler> format(List<String> args);
}

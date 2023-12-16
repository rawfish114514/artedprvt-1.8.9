package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;

import java.util.List;

@Solvable
public interface CommandProcess {
    /**
     * 执行这个命令
     * @param args 参数列表
     */
    @Solvable
    void process(List<String> args);
}

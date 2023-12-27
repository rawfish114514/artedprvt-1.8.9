package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;

import java.util.List;

@Solvable
public interface ProcessInterface {
    /**
     * 执行这个命令
     *
     * @param args     参数列表
     * @param messager
     */
    @Solvable
    void process(List<String> args, Messager messager);
}

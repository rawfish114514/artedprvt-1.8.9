package com.artedprvt.std.cli;

import com.artedprvt.api.Solvable;

import java.util.List;

@Solvable
public interface InfoInterface {
    /**
     * 参数信息
     *
     * @param args 光标之前的参数列表
     * @return 对参数的解释或提示 不能为null
     */
    @Solvable
    InfoHandler info(List<String> args);
}

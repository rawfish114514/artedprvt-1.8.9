package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.List;

@InterfaceView
public interface CompleteInterface {
    /**
     * 补全参数
     *
     * @param args 光标之前的参数列表
     * @return 最后一个参数的补全参数列表 不能为null
     */
    @InterfaceView
    List<String> complete(List<String> args);
}

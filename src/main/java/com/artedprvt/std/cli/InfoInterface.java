package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.List;

@InterfaceView
public interface InfoInterface {
    /**
     * 参数信息
     *
     * @param args 光标之前的参数列表
     * @return 对参数的解释或提示 不能为null
     */
    @InterfaceView
    InfoHandler info(List<String> args);
}

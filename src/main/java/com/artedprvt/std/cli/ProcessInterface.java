package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.List;

@InterfaceView
public interface ProcessInterface {
    /**
     * 执行这个命令
     *
     * @param args     参数列表
     * @param messager
     */
    @InterfaceView
    void process(List<String> args, Messager messager);
}

package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 信息处理程序
 */
@InterfaceView
public interface InfoHandler {
    /**
     * 处理信息
     *
     * @param source 参数
     * @return 返回这个参数的信息
     */
    @InterfaceView
    String handleInfo(String source);
}

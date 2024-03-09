package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 格式处理程序
 */
@InterfaceView
public interface FormatHandler {
    /**
     * 处理格式
     *
     * @param source 参数
     * @return 返回处理后的参数 要求可见长度和参数长度相等
     */
    @InterfaceView
    String handleFormat(String source);
}

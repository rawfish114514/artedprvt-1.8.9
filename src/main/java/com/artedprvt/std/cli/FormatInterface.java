package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

import java.util.List;

@InterfaceView
public interface FormatInterface {
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
    @InterfaceView
    List<? extends FormatHandler> format(List<String> args);
}

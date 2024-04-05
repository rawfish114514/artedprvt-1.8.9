package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 命令
 */
@InterfaceView
public abstract class Command implements ProcessInterface, CompleteInterface, FormatInterface, InfoInterface {
    @InterfaceView
    public Command(String commandName) {
        this.commandName = commandName;
    }

    /**
     * 重置
     * 表示命令有机会更新自身的数据
     * 在用户使用聊天栏时 命令通常无法在合适的时机更新自身
     * 此方法保证在合适的时机被聊天控件被关闭时调用
     */
    @InterfaceView
    public void reset() {
    }

    @InterfaceView
    public boolean frequent() {
        return false;
    }


    private final String commandName;

    @InterfaceView
    public String getName() {
        return commandName;
    }

}

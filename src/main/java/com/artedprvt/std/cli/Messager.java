package com.artedprvt.std.cli;

import com.artedprvt.api.Solvable;

/**
 * 信使?
 * 作为命令的实现者 并不关心使用何种交互界面(控制台 游戏聊天栏 其他控制台实现)
 */
@Solvable
public interface Messager {
    /**
     * 发送消息
     *
     * @param message 消息
     */
    @Solvable
    void send(String message);

    /**
     * 发送消息
     * 和悬浮消息(如果支持)
     *
     * @param message 消息
     * @param hover   悬浮消息
     */
    @Solvable
    void send(String message, String hover);

    /**
     * 可以悬浮
     *
     * @return if true is can hover
     */
    @Solvable
    boolean canHover();

    /**
     * 提供临时的交互的对话框和按钮
     * 要求阻塞当前线程直到得到结果
     * 对话框应该一直显示在上层
     * 返回一个字符串表示结果
     * 返回buttons的索引表示用户按下的按钮
     * 如果返回-1则表示用户没有选择按下任何按钮
     *
     * @param message
     * @param buttons
     * @return
     */
    @Solvable
    int dialog(String message, String... buttons);

    /**
     * 可以对话框
     *
     * @return
     */
    @Solvable
    boolean canDialog();
}

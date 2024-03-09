package com.artedprvt.std.cli;

import com.artedprvt.iv.anno.InterfaceView;

/**
 * 传递消息
 */
@InterfaceView
public interface Messager {
    /**
     * 发送消息
     *
     * @param message 消息
     */
    @InterfaceView
    void send(String message);

    /**
     * 发送消息
     * 和悬浮消息(如果支持)
     *
     * @param message 消息
     * @param hover   悬浮消息
     */
    @InterfaceView
    void send(String message, String hover);

    /**
     * 可以悬浮
     *
     * @return if true is can hover
     */
    @InterfaceView
    boolean canHover();

    /**
     * 提供临时的交互的对话框和按钮
     * 实现方法应该阻塞当前线程直到得到结果
     * 对话框应该一直显示在上层
     * 返回buttons的索引表示用户按下的按钮
     * 如果返回-1则表示用户没有选择按下任何按钮
     *
     * @param message
     * @param buttons
     * @return
     */
    @InterfaceView
    int dialog(String message, String... buttons);

    /**
     * 可以对话框
     *
     * @return
     */
    @InterfaceView
    boolean canDialog();
}

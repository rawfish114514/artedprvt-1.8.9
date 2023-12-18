package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;

/**
 * 信使?
 * 作为命令的实现者 并不关心使用何种交互界面(控制台 游戏聊天栏 其他控制台实现)
 */
@Solvable
public interface Messager {
    /**
     * 发送消息
     * @param message 消息
     */
    @Solvable
    void send(String message);

    /**
     * 发送消息
     * 和悬浮消息(如果支持)
     * @param message 消息
     * @param hover 悬浮消息
     */
    @Solvable
    void send(String message,String hover);

    /**
     * 可以悬浮
     * @return if true is can hover
     */
    @Solvable
    boolean canHover();
}

package rawfish.artedprvt.mi;

import rawfish.artedprvt.core.ProgramUsable;

/**
 * 聊天消息提供
 * 主要用于实现字符串的变化
 */
@FunctionalInterface
@ProgramUsable
public interface ChatProvider {
    String getChat();
}

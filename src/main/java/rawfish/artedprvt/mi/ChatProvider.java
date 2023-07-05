package rawfish.artedprvt.mi;

import rawfish.artedprvt.core.ProcedureUsable;

/**
 * 聊天消息提供
 * 主要用于实现字符串的变化
 */
@FunctionalInterface
@ProcedureUsable
public interface ChatProvider {
    String getChat();
}

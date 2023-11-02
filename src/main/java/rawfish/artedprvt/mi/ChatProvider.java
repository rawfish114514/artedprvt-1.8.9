package rawfish.artedprvt.mi;

import rawfish.artedprvt.api.Solvable;

/**
 * 聊天信息提供
 * 主要用于实现字符串的变化
 */
@FunctionalInterface
@Solvable
public interface ChatProvider {
    String getChat();
}

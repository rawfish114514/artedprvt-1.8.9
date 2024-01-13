package rawfish.artedprvt.core.app;

import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.minecraft.chat.ChatConsole;

/**
 * 提供各种应用程序常用的基础设施
 */
public abstract class BaseSystem {
    private static BaseSystem baseSystem = new BaseSystem() {
        @Override
        public Messager messager() {
            return new ChatConsole();
        }
    };


    public abstract Messager messager();


    public static Messager getMessager() {
        return baseSystem.messager();
    }
}

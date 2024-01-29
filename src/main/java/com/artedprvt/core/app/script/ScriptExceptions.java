package com.artedprvt.core.app.script;

import java.text.MessageFormat;

/**
 * 脚本异常
 * 一般只出现在脚本的线程
 * 这些异常应该立刻被处理并杀死脚本进程
 */
public class ScriptExceptions {
    public static void exception(String str) {
        Thread t = Thread.currentThread();
        t.getUncaughtExceptionHandler().uncaughtException(t, new RuntimeException(str));
    }

    public static void exception(String key, Object... args) {
        exception(MessageFormat.format(key, args));
    }
}

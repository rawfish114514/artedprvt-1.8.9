package com.artedprvt.core;

import com.artedprvt.core.app.LogFileController;

public class CoreInitializer {
    public static void init() {
        processController = new ProcessController().toStart();
        logFileController = new LogFileController().toStart();
    }

    private static ProcessController processController;

    public static ProcessController getProcessController() {
        return processController;
    }

    private static LogFileController logFileController;

    public static LogFileController getLogFileController() {
        return logFileController;
    }
}

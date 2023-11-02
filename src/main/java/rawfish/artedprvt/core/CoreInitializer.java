package rawfish.artedprvt.core;

import rawfish.artedprvt.core.script.LogFileController;

public class CoreInitializer {
    public static void init(){
        processController=new ProcessController().toStart();
        logFileController=new LogFileController().toStart();
    }

    private static ProcessController processController;
    public static ProcessController getProcessController(){
        return processController;
    }

    private static LogFileController logFileController;
    public static LogFileController getLogFileController(){
        return logFileController;
    }
}

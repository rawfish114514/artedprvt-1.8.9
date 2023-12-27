package rawfish.artedprvt.core;

public abstract class AppProcess extends Process{
    @Override
    public ProcessIdLevel pidLevel() {
        return ProcessController.APP_PROCESS_ID_LEVEL;
    }
}

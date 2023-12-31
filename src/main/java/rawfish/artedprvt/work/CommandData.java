package rawfish.artedprvt.work;

public class CommandData {
    String className;
    String commandName;

    @Override
    public String toString() {
        return className + "/Command " + commandName;
    }
}

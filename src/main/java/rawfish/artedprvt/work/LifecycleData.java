package rawfish.artedprvt.work;

class LifecycleData {
    String className;
    String lifecycleName;
    String[] phaseNames;

    @Override
    public String toString() {
        return className + "/Lifecycle " + lifecycleName + "(" + String.join(", ", phaseNames) + ")";
    }
}

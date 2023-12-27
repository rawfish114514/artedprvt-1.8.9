package rawfish.artedprvt.work;

class LifecycleData {
    String className;
    String lifecycleName;
    String[] phaseNames;

    public String toString() {
        return className + "/Lifecycle " + lifecycleName + "(" + String.join(", ", phaseNames) + ")";
    }
}

package rawfish.artedprvt.work;

class GoalData {
    String className;
    String goalName;
    String phaseName;

    public String toString() {
        return className + "/Goal " + goalName + "(" + phaseName + ")";
    }
}

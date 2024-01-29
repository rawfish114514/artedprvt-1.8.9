package com.artedprvt.work;

class GoalData {
    String className;
    String goalName;
    String phaseName;

    @Override
    public String toString() {
        return className + "/Goal " + goalName + "(" + phaseName + ")";
    }
}

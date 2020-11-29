package com.sychronisation.coverage.analyzer.model;

/**
 * Model Class to store the Method name, and whether the method is synchronised
 */

public class MethodStatistics {

    private String methodName;

    private boolean isSynchronised;

    public MethodStatistics() {}

    public MethodStatistics(String methodName, boolean isSynchronised) {
        this.methodName = methodName;
        this.isSynchronised = isSynchronised;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isSynchronised() {
        return isSynchronised;
    }

    public void setSynchronised(boolean synchronised) {
        isSynchronised = synchronised;
    }

/*    @Override
    public String toString() {
        return "MethodStatistics{" +
                "methodName='" + methodName + '\'' +
                ", isSynchronised=" + isSynchronised +
                '}';
    }*/
}

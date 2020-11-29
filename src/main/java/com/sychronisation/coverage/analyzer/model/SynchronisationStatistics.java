package com.sychronisation.coverage.analyzer.model;

/**
 * Model Class to store the ClassStatistics, and whether the class passed the thread safety checks
 */
public class SynchronisationStatistics {
    public SynchronisationStatistics() {
    }

    public SynchronisationStatistics(ClassStatistics classStatistics, String threadSafetyCheck) {
        this.classStatistics = classStatistics;
        this.threadSafetyCheck = threadSafetyCheck;
    }

    private ClassStatistics classStatistics;
    private String threadSafetyCheck;

    public ClassStatistics getClassStatistics() {
        return classStatistics;
    }

    public void setClassStatistics(ClassStatistics classStatistics) {
        this.classStatistics = classStatistics;
    }

    public String getThreadSafetyCheck() {
        return threadSafetyCheck;
    }

    public void setThreadSafetyCheck(String threadSafetyCheck) {
        this.threadSafetyCheck = threadSafetyCheck;
    }
}

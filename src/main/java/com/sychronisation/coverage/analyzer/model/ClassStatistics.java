package com.sychronisation.coverage.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class ClassStatistics {

    private String className;
    private boolean isRunnable;
    private List<MethodStatistics> methodStatistics = new ArrayList<>();

    public ClassStatistics() {
    }

    public ClassStatistics(String className, boolean isRunnable, List<MethodStatistics> methodStatistics) {
        this.className = className;
        this.isRunnable = isRunnable;
        this.methodStatistics = methodStatistics;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<MethodStatistics> getMethodStatistics() {
        return methodStatistics;
    }

    public void setMethodStatistics(List<MethodStatistics> methodStatistics) {
        this.methodStatistics = methodStatistics;
    }

 /*   @Override
    public String toString() {
        return "ClassStatistics{" +
                "className='" + className + '\'' +
                ", isRunnable=" + isRunnable +
                ", methodStatistics=" + methodStatistics.toString() +
                '}';
    }*/

    public Boolean getRunnable() {
        return isRunnable;
    }

    public void setRunnable(boolean runnable) {
        isRunnable = runnable;
    }
}

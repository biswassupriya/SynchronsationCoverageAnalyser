package com.sychronisation.coverage.analyzer.model;

/** model class holds  information about the test execution results in single threaded,
 * multi threaded and the final percentaage outcome */

public class Coverage {
    private int single;
    private int multi;
    private int percentage;

    @Override
    public String toString() {
        return "Coverage{" +
                "single=" + single +
                ", multi=" + multi +
                ", percentage=" + percentage +
                '}';
    }

    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}

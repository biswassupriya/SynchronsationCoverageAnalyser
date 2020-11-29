package com.sychronisation.coverage.analyzer.instrumenter;

public class RunnableInstrumentationExample implements Runnable {

    public static void main(String[] args) {
        sayHello();
        sayHelloSynchronised();

    }

    private static synchronized void sayHelloSynchronised() {

    }

    private static void sayHello() {

    }

    @Override
    public void run() {

    }
}

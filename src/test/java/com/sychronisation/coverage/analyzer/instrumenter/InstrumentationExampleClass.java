package com.sychronisation.coverage.analyzer.instrumenter;

public class InstrumentationExampleClass implements Runnable {

    public static void main(String[] args) {
        sayHello();
        sayHelloSynchronised();

    }

    private static synchronized void sayHelloSynchronised() {
        System.out.println("sayHelloSynchronised");

    }

    private static void sayHello() {
        System.out.println("sayHello");

    }

    @Override
    public void run() {
        System.out.println("sayRun");

    }
}

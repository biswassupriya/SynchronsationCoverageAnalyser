package com.sychronisation.coverage.analyzer;

import com.sychronisation.coverage.analyzer.instrumenter.AspectClassTransformer;
import javassist.NotFoundException;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

/** SynchronisationAnalyzerAgent class provides services needed to instrument java code.*/

public class SynchronisationAnalyzerAgent {
    private static final Logger log = Logger.getLogger(SynchronisationAnalyzerAgent.class.getName());
    /**
     * As soon as the JVM initializes, This  method will be called.
     * Configs for intercepting will be read and added to Transformer so that Transformer will intercept when the
     * corresponding Java Class and Method is loaded.
     *
     * @param agentArgs       The list of packages to scan
     * @param instrumentation The instrumentation object
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        log.info("Starting Java Agent......");
        System.setProperty("packages", agentArgs.split("=")[1]);
        String packages = System.getProperty("packages");
        System.out.println("PackagesToScan: " + packages);

        AspectClassTransformer aspectClassTransformer = new AspectClassTransformer();
        aspectClassTransformer.init();
        instrumentation.addTransformer(aspectClassTransformer);
    }
}

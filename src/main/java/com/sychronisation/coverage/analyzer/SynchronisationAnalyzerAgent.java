package com.sychronisation.coverage.analyzer;

import com.sychronisation.coverage.analyzer.instrumenter.AspectClassTransformer;
import javassist.NotFoundException;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

public class SynchronisationAnalyzerAgent {
    private static final Logger log = Logger.getLogger(SynchronisationAnalyzerAgent.class.getName());
    /**
     * As soon as the JVM initializes, This  method will be called.
     * Configs for intercepting will be read and added to Transformer so that Transformer will intercept when the
     * corresponding Java Class and Method is loaded.
     *
     * @param agentArgs       The list of agent arguments
     * @param instrumentation The instrumentation object
     * @throws InstantiationException While  an instantiation of object cause an error.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) throws InstantiationException, NotFoundException {
        log.info("Starting Java Agent......");
        AspectClassTransformer aspectClassTransformer = new AspectClassTransformer();
        aspectClassTransformer.init();
        instrumentation.addTransformer(aspectClassTransformer);
    }
}

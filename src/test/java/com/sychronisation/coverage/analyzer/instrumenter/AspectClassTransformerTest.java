package com.sychronisation.coverage.analyzer.instrumenter;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.charset.StandardCharsets;

public class AspectClassTransformerTest {

    private AspectClassTransformer testee;

    private ClassLoader classLoader = new ClassLoader() {
    };


    @Before
    public void setUp() {
        testee = new AspectClassTransformer();
    }

    @Test
    public void testTransform_whenClassNameContainsTest_noInstrumentationHappens() throws IllegalClassFormatException, IOException {

        System.setProperty("packages","com.sychronisation.coverage.analyzer");

        InputStream resourceAsStream = AspectClassTransformer.class.getResourceAsStream("com.sychronisation.coverage.analyzer.instrumenter.TestInstrumentationClass.class");

        byte[] classfileBuffer = IOUtils.toByteArray(resourceAsStream);
        byte[] testInstrumentations = testee.transform(classLoader, "com.sychronisation.coverage.analyzer.instrumenter.TestInstrumentationClass", null, null, classfileBuffer);

        String output = new String(testInstrumentations, StandardCharsets.UTF_8);
        Assert.assertFalse(output.contains("Logging using Agent"));

    }

    @Test
    public void testTransform_whenClassNameDoesNotContainTest_instrumentTheClass() throws IllegalClassFormatException, IOException {

        System.setProperty("packages","com.sychronisation.coverage.analyzer");

        InputStream resourceAsStream = AspectClassTransformer.class.getResourceAsStream("InstrumentationExampleClass.class");

        byte[] classfileBuffer = IOUtils.toByteArray(resourceAsStream);
        byte[] testInstrumentations = testee.transform(classLoader, "com/sychronisation/coverage/analyzer/instrumenter/InstrumentationExampleClass", null, null, classfileBuffer);

        String output = new String(testInstrumentations, StandardCharsets.UTF_8);
        Assert.assertTrue(output.contains("Logging using Agent"));

    }

    @Test
    public void testTransform_whenClassNameImplementsRunnable_instrumentTheClass() throws IllegalClassFormatException, IOException {

        System.setProperty("packages","com.sychronisation.coverage.analyzer");

        InputStream resourceAsStream = AspectClassTransformer.class.getResourceAsStream("RunnableInstrumentationExample.class");

        byte[] classfileBuffer = IOUtils.toByteArray(resourceAsStream);
        byte[] testInstrumentations = testee.transform(classLoader, "com/sychronisation/coverage/analyzer/instrumenter/RunnableInstrumentationExample", null, null, classfileBuffer);

        String output = new String(testInstrumentations, StandardCharsets.UTF_8);
        Assert.assertTrue(output.contains("sleep"));

    }
}
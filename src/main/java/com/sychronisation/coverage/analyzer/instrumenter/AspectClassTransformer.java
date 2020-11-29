package com.sychronisation.coverage.analyzer.instrumenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sychronisation.coverage.analyzer.model.ClassStatistics;
import com.sychronisation.coverage.analyzer.model.MethodStatistics;
import javassist.*;
import javassist.runtime.Desc;
import javassist.scopedpool.ScopedClassPoolFactoryImpl;
import javassist.scopedpool.ScopedClassPoolRepositoryImpl;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AspectClassTransformer implements the ClassFileTransformer interface to
 * transform class files to byte code level before they are loaded into the JVM
 */
public class AspectClassTransformer implements ClassFileTransformer {

    private static final Logger log = Logger.getLogger(AspectClassTransformer.class.getName());
    public static final String JAVA_LANG_RUNNABLE = "java.lang.Runnable";
    private ScopedClassPoolFactoryImpl scopedClassPoolFactory = new ScopedClassPoolFactoryImpl();

    private ClassPool rootPool;

    public void init() {

        Desc.useContextClassLoader = true;
        rootPool = ClassPool.getDefault();
    }

    /**
     * An agent gives an implementation of an interface method so that we can change the class files.
     * changing the mention class file and returns a new substitute class file.
     * verifies the config with classes and intercept if matches found for Class Name, Method Name, Method
     * Signature.
     *
     * @param loader              The mentioned loader of the class to be modified, can be {@code null}
     *                            if the bootstrap loader.
     * @param className           The name of the class which is to be instrumented.
     * @param classBeingRedefined If this is initiated by a re-definition or transformation.
     * @param protectionDomain    The protection domain of the class entity being transformed
     * @param classfileBuffer     input byte buffer in classfile format to be instrumented.
     * @return The modified byte code.
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        byte[] byteCode = classfileBuffer;

        String packages = System.getProperty("packages");

        ObjectMapper objectMapper = new ObjectMapper();


        String packageName = convertDotToSlash(packages);
        if (className.contains(packageName) && !className.contains("Test")) {
            ClassStatistics classStatistics = new ClassStatistics();

            try {
                ClassPool classPool = scopedClassPoolFactory.create(loader, rootPool,
                        ScopedClassPoolRepositoryImpl.getInstance());
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] methods = ctClass.getDeclaredMethods();
                classStatistics.setClassName(className);
                CtClass[] interfaces = ctClass.getInterfaces();
                boolean isRunnable = false;
                for (CtClass anInterface : interfaces) {
                    if (JAVA_LANG_RUNNABLE.equals(anInterface.getName())) {
                        isRunnable = true;
                    }
                }
                classStatistics.setRunnable(isRunnable);


                for (CtMethod method : methods) {

                    if (method.getName().equals("main")) {
                        method.insertAfter("System.out.println(\"Logging using Agent\");");
                    }
                    boolean isSychronised = false;
                    if (Modifier.isSynchronized(method.getModifiers())) {
                        isSychronised = true;
                    }
                    if (method.getName().equals("run")) {
                        method.insertBefore("        try {\n" +
                                "            Thread.sleep(50000l);\n" +
                                "        } catch (InterruptedException e) {\n" +
                                "            // TODO Auto-generated catch block\n" +
                                "            e.printStackTrace();\n" +
                                "        }");
                    }
                    MethodStatistics methodStatistics = new MethodStatistics(method.getName(), isSychronised);
                    classStatistics.getMethodStatistics().add(methodStatistics);
                }

                System.out.println(objectMapper.writeValueAsString(classStatistics));
                byteCode = ctClass.toBytecode();
                ctClass.detach();
            } catch (Throwable ex) {
                log.log(Level.SEVERE, "Error in transforming the class: " + className, ex);
            }
        }
        return byteCode;
    }

    private static String convertDotToSlash(final String srcName) {
        return srcName.replace('.', '/');
    }
}

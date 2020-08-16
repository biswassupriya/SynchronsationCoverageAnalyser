package com.sychronisation.coverage.analyzer.instrumenter;
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
 *  AspectClassTransformer implements the Class transformer transform class files on byte code level before they are loaded into the Java Virtual Machin
 */
public class AspectClassTransformer implements ClassFileTransformer {

    private static final Logger log = Logger.getLogger(AspectClassTransformer.class.getName());
    private ScopedClassPoolFactoryImpl scopedClassPoolFactory = new ScopedClassPoolFactoryImpl();

    private ClassPool rootPool;

    public void init() throws NotFoundException {

        Desc.useContextClassLoader = true;
        rootPool = ClassPool.getDefault();
    }

    /**
     * An agent gives an implementation of an interface method so that we can change the class files.
     * changing the mention class file and returns a new substitute class file.
     * verify the config with classes and intercept if matches the Communicating Class Name, Method Name, Method
     * Signature.
     *
     * @param loader              The mentioned loader of the class to be modified, can be {@code null}
     *                            if the bootstrap loader.
     * @param className           The nameof the class which is to be instrumented.
     * @param classBeingRedefined If this is initiated by a redifintion or transformation.
     * @param protectionDomain    The protection domain of the class entity being transformed
     * @param classfileBuffer     input byte buffer in classfile format to be instrumented.
     * @return The modified byte code.
     * @throws IllegalClassFormatException IllegalClassFormat Exception.
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {

        byte[] byteCode = classfileBuffer;
        log.info("Transforming the class " + className);
        try {
            ClassPool classPool = scopedClassPoolFactory.create(loader, rootPool,
                    ScopedClassPoolRepositoryImpl.getInstance());
            CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for (CtMethod method : methods) {
                if (method.getName().equals("main")) {
                    method.insertAfter("System.out.println(\"Logging using Agent\");");
                }
                if (Modifier.isSynchronized(method.getModifiers())) {
                    method.insertAfter("System.out.println(\"Synchronised Method Called\");");
                }
            }
            byteCode = ctClass.toBytecode();
            ctClass.detach();
        } catch (Throwable ex) {
            log.log(Level.SEVERE, "Error in transforming the class: " + className, ex);
        }
        return byteCode;
    }
}

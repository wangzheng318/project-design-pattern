package com.wz.designpatterns.behavioral.proxy.demo1own.proxydemo.proxy_dynamic_v1;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class TimedProxy {
    private static final String BR = "\r\n";
    //classpath一定要以/分隔符结尾，否则找不到文件
    private static final String CLASS_PATH = System.getProperty("user.dir").concat("\\src\\main\\java\\");
    private static final String CLASS_SHORT_NAME = "$BasicProxy0";
    private static final String PACKAGE = ClientV1.class.getPackage().getName();
    private static final String CLASS_FULL_NAME =PACKAGE.concat(".").concat(CLASS_SHORT_NAME);

    public static Movable getInstance(Movable movable) {
        File javaFile = null;
        try {
            //1.生成类文件字符串
            String javaFileString = generateJavaFileString();
            //2.将生成的字符串保存成.java文件
            javaFile = genertateJavaFile(javaFileString);
            //3.将类文件编译成.class文件
            compileJavaFile(javaFile);
            //4.将.class文件加载到java虚拟机
//            Class<?> clazz = loadClassByMyClassLoader(classpath, className);
            Class<?> clazz = loadClassByURLClassLoader(CLASS_PATH, CLASS_FULL_NAME);

            //5.调用类中的方法
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(Movable.class);
            Movable o = (Movable) declaredConstructor.newInstance(movable);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //6.删除.java文件 .class文件
            //javaFile = null;//控制删不删文件,便于调试问题使用
            if (null != javaFile) {
                // System.out.println("javaFile.getPath()=" + javaFile.getPath());
                String javaFilePath = javaFile.getPath();
                File classFile = new File(javaFilePath.substring(0, javaFilePath.lastIndexOf(".")).concat(".class"));
//                System.out.println(classFile.getPath());
                classFile.delete();
                javaFile.delete();
                System.out.println("删除临时文件.java,.class");
            }
        }
    }



    public static Class loadClassByURLClassLoader(String classpath, String className) throws MalformedURLException, ClassNotFoundException {
//        System.out.println("classpath:"+classpath);
//        System.out.println("className:"+className);
        URL[] urls = new URL[]{new URL("file:/" + classpath)};
        System.out.println(Arrays.asList(urls));
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        return urlClassLoader.loadClass(className);
    }

    private static void compileJavaFile(File javaFile) throws IOException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        ) {
            Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(javaFile);
            JavaCompiler.CompilationTask compilerTask = javaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
            Boolean call = compilerTask.call();
            standardFileManager.close();
        } catch (Exception e) {
            throw e;
        }
    }

    private static File genertateJavaFile(String javaFileString) throws Exception {
        String filePath = CLASS_PATH.concat(PACKAGE.replaceAll("[.]", "/").concat("/").concat(CLASS_SHORT_NAME).concat(".java"));
        File proxyFile = new File(filePath);
        System.out.println(filePath);
        if (proxyFile.exists()) {
            proxyFile.delete();
        }
        proxyFile.createNewFile();

        try (PrintWriter printWriter = new PrintWriter(proxyFile);) {
            printWriter.write(javaFileString);
            printWriter.flush();
        }
        return proxyFile;
    }

    private static String generateJavaFileString() {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v1;import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable;public class $BasicProxy0 implements Movable {    private Movable movable;    public $BasicProxy0(Movable movable) {        this.movable = movable;    }    @Override    public void move() {        long begin = System.currentTimeMillis();        System.out.println(\"tank in StaticProxyByReference is moving\");        this.movable.move();        long end = System.currentTimeMillis();        System.out.printf(\"the time of the StaticProxyByReference takes is:{%d}\\r\\n\", (end - begin));    }}");
        return sb.toString();
    }
}

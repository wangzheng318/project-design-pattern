package com.wz.designpatterns.behavioral.proxy.demo1own.proxydemo.proxy_dynamic_v2.framework;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class WZProxy {
    //换行符
    public static final String BR = "\r\n";
    private static final String classPath = "d:/src/";
    private static final String packageName = "com";
    private static final String className = "$Proxy2";

    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h) {
        Object proxyObject = null;
        File javaFile = null;
        try {
            //1.根据接口生成java 字符串
            String javaFileString = generateJavaFileString(packageName, className, interfaces);
            //2.将Java字符串保存成.java文件
            javaFile = genertateJavaFile(packageName, className,javaFileString);
            //3.将.java文件编译成.class文件
            compileJavaFile(javaFile);
            //4.将.class文件加载到java虚拟机
            Class<?> aClass = loader.loadClass(packageName.concat(".").concat(className));
            //5.方法调用
            Constructor<?> constructor = aClass.getDeclaredConstructor(InvocationHandler.class);
            proxyObject = constructor.newInstance(h);
            return proxyObject;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除代理对象对应的.java文件，.class文件
            javaFile = null;
            if(null != javaFile){
                // System.out.println("javaFile.getPath()=" + javaFile.getPath());
                String javaFilePath = javaFile.getPath();
                File classFile = new File(javaFilePath.substring(0, javaFilePath.lastIndexOf(".")).concat(".class"));
//                System.out.println(classFile.getPath());
                classFile.delete();
                javaFile.delete();
                System.out.println("删除临时文件.java,.class");
            }
        }
        return null;
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

    private static File genertateJavaFile(String packageName, String className,String javaFileString) throws Exception {

        String packagePath = classPath.concat(packageName.replaceAll("[.]", "/"));
        File packageFile = new File(packagePath);
        if(!packageFile.exists()){
            packageFile.mkdirs();
        }
        String filePath = packagePath.concat("/").concat(className).concat(".java");
        File proxyFile = new File(filePath);
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

    public static String generateJavaFileString(String packageName, String className, Class[] interfaces) {
        Class interfaceClazz = interfaces[0];
        Method[] declaredMethods = interfaceClazz.getDeclaredMethods();

        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(packageName).append(";").append(BR);
        sb.append("import java.lang.reflect.Method;").append(BR);
        sb.append("import com.wz.designpatterns.behavioral.proxy.demo1own.proxydemo.proxy_dynamic_v2.framework.InvocationHandler;").append(BR);
        sb.append("public class ").append(className).append(" implements ").append(interfaceClazz.getName()).append(" {").append(BR);
        sb.append(" private InvocationHandler h;").append(BR);
        sb.append("public "+className+"(InvocationHandler h) {this.h = h;}").append(BR);
        for (Method method : declaredMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            sb.append("public ").append(method.getReturnType().getName()).append(" ").append(method.getName()).append(" ").append("(");
            if (null != parameterTypes && 0 < parameterTypes.length) {
                for (int i = 0; i < parameterTypes.length; i++) {
                    sb.append(parameterTypes[i].getName()).append(" p" + i);
                    if (i != parameterTypes.length - 1) {
                        sb.append(",");
                    }
                }
            }
            sb.append(")").append(BR);
            sb.append("{").append(BR);
            sb.append("       try {").append(BR);
            sb.append("Method method = "+interfaceClazz.getName()+".class.getDeclaredMethod(").append("\"").append(method.getName()).append("\"").append(",").append("new Class[]{");
            for (int i = 0; i < parameterTypes.length; i++) {
                sb.append(parameterTypes[i].getName()).append(".class ");
                if (i != parameterTypes.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("}").append(");").append(BR);
            sb.append("method.setAccessible(true);").append(BR);
            sb.append("Object[] args = new Object[]{");
            for (int i = 0; i < parameterTypes.length; i++) {
                sb.append("p").append(i);
                if (i != parameterTypes.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("};").append(BR);
            if (!"void".equals(method.getReturnType().getName())) {
                sb.append("return h.invoke(this,method,args);").append(BR);
            }else{
                sb.append("h.invoke(this,method,args);").append(BR);
            }

            sb.append("}catch (Exception e) {").append(BR);
            sb.append("e.printStackTrace();").append(BR);
            if (!"void".equals(method.getReturnType().getName())) {
               return null;
            }
            sb.append("}").append(BR);
            sb.append("}").append(BR);
            sb.append("}").append(BR);
        }

        //System.out.println(sb.toString());
        return sb.toString();
    }


    public class MyClassLoader extends ClassLoader {

        private String classpath;

        public MyClassLoader(String classpath) {
            this.classpath = classpath;
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] bytes = loadClassData(name);
            return super.defineClass(name, bytes, 0, bytes.length);
        }

        private byte[] loadClassData(String name) {
            byte [] resultBytes = null;
            String filePath = this.getClasspath() + "/" + name.replaceAll("[.]", "/") + ".class";
            try (FileInputStream fileInputStream = new FileInputStream(filePath);
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
                byte[] bytes = new byte[1021];
                int count = -1;
                while (-1 != (count = fileInputStream.read(bytes))) {
                    byteArrayOutputStream.write(bytes,0,count );
                }
                resultBytes = byteArrayOutputStream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultBytes;
        }

        public String getClasspath() {
            return classpath;
        }

        public void setClasspath(String classpath) {
            this.classpath = classpath;
        }
    }
}

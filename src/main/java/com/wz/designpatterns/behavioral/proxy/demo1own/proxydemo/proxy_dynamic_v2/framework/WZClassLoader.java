package com.wz.designpatterns.behavioral.proxy.demo1own.proxydemo.proxy_dynamic_v2.framework;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WZClassLoader extends ClassLoader {

    private String classpath;

    public WZClassLoader(String classpath) {
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

package com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2;


import com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2.framework.WZClassLoader;
import com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2.framework.WZProxy;

public class WZProxyFactory {
    public static <T> T getInstance(T target) {
        String classpath = "d:/src/";
        WZClassLoader classLoader = new WZClassLoader(classpath);
        return (T) WZProxy.newProxyInstance(classLoader, target.getClass().getInterfaces(),new WZInvocationHandler(target));
    }
}

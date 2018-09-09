package com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2.framework;

import java.lang.reflect.Method;

public interface InvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args) throws Exception;
}

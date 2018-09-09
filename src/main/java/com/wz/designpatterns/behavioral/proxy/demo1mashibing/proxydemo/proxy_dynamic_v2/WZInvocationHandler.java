package com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2;



import com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2.framework.InvocationHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

public class WZInvocationHandler implements InvocationHandler {
    private Object target;

    public WZInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        Object result = null;
        long begin = System.currentTimeMillis();
        System.out.printf("parameter:{%s}\r\n", Arrays.asList(args));
        System.out.printf("the method {%s}is calling...\r\n", method.getName());
        result = method.invoke(target, args);
        long end = System.currentTimeMillis();
        System.out.printf("the time of the method cost is:{%d}\r\n", (end - begin));
        System.out.printf("return:{%s}\r\n", result);
        return result;
    }
}

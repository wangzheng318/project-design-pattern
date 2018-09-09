package com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2;

import java.lang.reflect.Method;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2.framework.InvocationHandler;

public class $Proxy2 implements com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable {
    private InvocationHandler h;

    public $Proxy2(InvocationHandler h) {
        this.h = h;
    }

    public void move() {
        try {
            Method method = com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable.class.getDeclaredMethod("move", new Class[]{});
            method.setAccessible(true);
            Object[] args = new Object[]{};
            h.invoke(this, method, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

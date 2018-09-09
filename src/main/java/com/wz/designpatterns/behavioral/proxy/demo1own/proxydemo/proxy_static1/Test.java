package com.wz.designpatterns.behavioral.proxy.demo1own.proxydemo.proxy_static1;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.impl.Tank;

public class Test {
    public static void main(String[] args) {
        //1.通过集成实现静态代理
       StaticProxyByExtend staticProxyByExtend = new StaticProxyByExtend();
        staticProxyByExtend.move();

        //2.通过组合|聚合实现静态代理
       StaticProxyByReference proxy = new StaticProxyByReference(new Tank());
        proxy.move();

    }
}

package com.wz.designpatterns.behavioral.proxy.demo1own.proxydemo.proxy_static1;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable;

/**
 * 通过组合|聚合的方式实现代理---多态-重写
 */
public class StaticProxyByReference implements Movable {

    private Movable movable;

    public StaticProxyByReference(Movable movable) {
        this.movable = movable;
    }

    @Override
    public void move() {
        long begin = System.currentTimeMillis();
        System.out.println("tank in StaticProxyByReference is moving");
        this.movable.move();
        long end = System.currentTimeMillis();
        System.out.printf("the time of the StaticProxyByReference takes is:{%d}\r\n", (end - begin));
    }
}

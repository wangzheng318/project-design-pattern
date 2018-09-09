package com.wz.designpatterns.behavioral.proxy.demo1own.proxydemo.proxy_static1;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.impl.Tank;

/**
 * 通过继承的方式显示代理---多态-重写
 */
public class StaticProxyByExtend extends Tank {
    @Override
    public void move(){
        long begin = System.currentTimeMillis();
        System.out.println("tank in StaticProxyByExtend is moving");
        super.move();
        long end = System.currentTimeMillis();
        System.out.printf("the time of the StaticProxyByExtend takes is:{%d}\r\n",(end - begin));
    }
}

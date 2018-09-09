package com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v2;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable;
import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.impl.Tank;

public class Client {
    public static void main(String[] args) {
        Tank tank = new Tank();
        Movable movable = (Movable) WZProxyFactory.getInstance(tank);
        movable.move();
    }
}

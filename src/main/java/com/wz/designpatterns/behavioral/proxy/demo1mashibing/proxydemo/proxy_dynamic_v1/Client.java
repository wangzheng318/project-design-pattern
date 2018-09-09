package com.wz.designpatterns.behavioral.proxy.demo1mashibing.proxydemo.proxy_dynamic_v1;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable;
import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.impl.Tank;

/**
 * 动态生成代理类demo1_代理类中的代码是写死的
 */
public class Client {

    public static void main(String[] args) {
        Movable timedProxy = TimedProxy.getInstance(new Tank());
        timedProxy.move();
    }


}

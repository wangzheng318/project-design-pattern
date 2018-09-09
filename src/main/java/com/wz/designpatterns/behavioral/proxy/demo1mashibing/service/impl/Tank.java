package com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.impl;

import com.wz.designpatterns.behavioral.proxy.demo1mashibing.service.Movable;

import java.util.Random;

public class Tank implements Movable {
    @Override
    public void move() {
//        long begin = System.currentTimeMillis();
//        System.out.println("the tank is moving");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        long end = System.currentTimeMillis();
//        System.out.printf("the time of the tank takes is:{%d}\r\n",(end - begin));
    }
}

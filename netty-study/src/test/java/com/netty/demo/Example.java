package com.netty.demo;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Example {

    /**
     * 判断一个数字是不是2的n次方
     */
    @Test
    public void testOne(){
        for(int i = 1; i < 10 ; i ++){
            if((i & -i) == i){//判断是一个数字是不是2次方
                System.out.println("i = "+ i);
            }
        }
    }

    @Test
    public void testTwo(){
        for(int i = 0; i < 10; i++){
            System.out.println(i & (4-1));
        }
    }

    @Test
    public void testThree(){
        for(int i = 0 ; i <10; i++){
            System.out.println(Math.abs(i % 4));
        }
    }

    /**
     * 1s=1000毫秒=1000000微秒=1000000000纳秒
     * System.nanoTime()返回最准确的可用的系统计时器的当前值，以纳秒为单位。
     * 此方法只能用于测量已过的时间，与系统或钟表时间的其他任何时间概念无关。返回值表示从某一固定但任意的时间算起的纳秒(或许从以后算起，所以该值可能为负)。此方法提供纳秒的精度，但不是必要的纳秒的准确度
     */
    @Test
    public void testFour(){
        long time = TimeUnit.SECONDS.toNanos(1);
        System.out.println(time);
        //返回正在运行的Java虚拟机的高分辨率时间源的当前值，单位为纳秒。
        long nanoTime = System.nanoTime();
        System.out.println(nanoTime);
    }




}

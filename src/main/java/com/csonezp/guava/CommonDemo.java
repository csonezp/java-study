package com.csonezp.guava;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;

import java.util.concurrent.TimeUnit;

/**
 * Created by zp on 2018/4/25.
 */
public class CommonDemo {
    public static void main(String[] args) throws InterruptedException {
        stopwatchDemo();

    }
    private static void timerDemo() throws InterruptedException {
        Ticker ticker = Ticker.systemTicker();
        Long time1 = ticker.read();
        Thread.sleep(1000);
        Long time2 = ticker.read();
        System.out.println(time2-time1);
        Thread.sleep(2000);
        Long time3 = ticker.read();
        System.out.println(time3-time2);
    }
    private static void stopwatchDemo() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Long time1 =stopwatch.elapsed(TimeUnit.MICROSECONDS);
        Thread.sleep(1000);
        Long time2 = stopwatch.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(time2-time1);
        Thread.sleep(2000);
        Long time3 = stopwatch.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(time3-time2);
    }

}

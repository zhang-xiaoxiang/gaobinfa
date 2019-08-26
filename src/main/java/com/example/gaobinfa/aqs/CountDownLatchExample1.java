package com.example.gaobinfa.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
/**
 * CountDownLatchExample1:闭锁,计数器倒数
 * @author zhangxiaoxiang
 * @date 2019/8/13
 */

public class CountDownLatchExample1 {
    /**
     * 线程数量
     */
    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {

        // 创建线程池,这里阿里插件会报警告,代码规范或者潜在的安全性问题,
        // 这里创建线程池方式不是重点,主要展示CountDownLatch
        ExecutorService exec = Executors.newCachedThreadPool();
        //初始化次数
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    //countDown()方法用于使计数器减一
                    countDownLatch.countDown();
                }
            });
        }
        //await()方法则使调用该方法的线程处于等待状态，其一般是主线程调用
        countDownLatch.await();
        log.info("执行完毕finish");
        //关闭线程池
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("线程{}", threadNum);
        Thread.sleep(100);
    }
}

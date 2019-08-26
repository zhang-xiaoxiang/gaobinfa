package com.example.gaobinfa.aqs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * CyclicBarrierExample1:Java中关于线程的计数器,等待线程(循环操作)
 * 可以用于多线程计算数据，最后合并计算结果的场景(类似秋后算总账)
 * @author zhangxiaoxiang
 * @date 2019/8/13
 */
@Slf4j
public class CountDownLatchExample2 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {

           ExecutorService exec = Executors.newCachedThreadPool();
        // ExecutorService exec = new ScheduledThreadPoolExecutor(threadCount, new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-d%").daemon(true).build());


        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        //指定等待时间
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}", threadNum);
    }
}

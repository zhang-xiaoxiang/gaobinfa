package com.example.gaobinfa.atomic;

import com.example.gaobinfa.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@ThreadSafe
public class AtomicExample6 {

    private static AtomicBoolean isHappened = new AtomicBoolean(false);

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("isHappened:{}", isHappened.get());
    }

    private static void test() {
        //1. 比较AtomicBoolean和expect的值，如果一致，执行方法内的语句。其实就是一个if语句
        //2. 把AtomicBoolean的值设成update         比较最要的是这两件事是一气呵成的，
        //   这连个动作之间不会被打断，任何内部或者外部的语句都不可能在两个动作之间运行。为多线程的控制提供了解决的方案。
        if (isHappened.compareAndSet(false, true)) {
            log.info("execute");
        }
    }
}

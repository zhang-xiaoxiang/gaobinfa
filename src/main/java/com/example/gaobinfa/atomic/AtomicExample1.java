package com.example.gaobinfa.atomic;

import com.example.gaobinfa.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class AtomicExample1 {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        //这个创建线程池的方式不推荐,这里只是为了简便,后续再改进,它暂时不是讨论的重点先凑合这用
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        final Semaphore semaphore = new Semaphore(threadTotal);
        /**
         * 将5000个请求扔进线程池中
         */
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    //获取许可
                    semaphore.acquire();
                    //执行线程主方法
                    add();
                    //释放许可
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                //每次执行后减一操作
                countDownLatch.countDown();
            });
        }
        //countDownLatch减到那么就往下面执行
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        //打印结果
        log.info("count:{}", count.get());
    }

    /**
     * 对AtomicInteger对象进行+1操作
     */
    private static void add() {
        //加一操作(这里使用的是方法进行+1,而不是++执行+1)
        count.incrementAndGet();
        // count.getAndIncrement();
    }
}

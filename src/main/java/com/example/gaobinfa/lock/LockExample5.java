package com.example.gaobinfa.lock;

import com.example.gaobinfa.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;

@Slf4j
@ThreadSafe
/**
 * LockExample5:https://segmentfault.com/a/1190000015808032?utm_source=tag-newest
 * @author zhangxiaoxiang
 * @date 2019/8/27
 */
public class LockExample5 {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;
    /**
     * StampedLock类，在JDK1.8时引入，是对读写锁ReentrantReadWriteLock的增强，
     * 该类提供了一些功能，优化了读锁、写锁的访问，同时使读写锁之间可以互相转换，更细粒度控制并发。
     */
    private final static StampedLock lock = new StampedLock();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        //专门获取锁定，如有必要，阻塞。
        long stamp = lock.writeLock();
        try {
            count++;
        } finally {
            //如果锁定状态与给定的标记匹配，则释放相应的锁定模式。
            lock.unlock(stamp);
        }
    }
}

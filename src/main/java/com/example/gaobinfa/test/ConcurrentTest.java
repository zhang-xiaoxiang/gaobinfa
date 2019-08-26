package com.example.gaobinfa.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * ConcurrentTest:代码实现并发安全测试(当然也可以使用Jmeter压力测试工具进行测试)
 *
 * @author zhangxiaoxiang
 * @date: 2019/08/11
 */
@Slf4j
public class ConcurrentTest {

    /**
     * 请求总数
     */
    public static int clientTotal = 5000;

    /**
     *  同时并发执行的线程数
     */
    public static int threadTotal = 200;

    public static int count = 0;

    public static void main(String[] args) throws Exception {
        //常规创建(这里使用这种方式体现线程不安全)
         ExecutorService executorService = Executors.newCachedThreadPool();
        // ExecutorService executorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-d%").daemon(true).build());
        //计数信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        /**
         * JDK Lambda表达式
         */
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    // 从此信号量获取许可，阻塞直到一个可用
                    semaphore.acquire();
                    //执行线程方法
                    add();
                    // 发布许可证，将其返回信号量
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        //当前线程等到锁存器倒计数到零
        countDownLatch.await();
        //启动有序关闭，其中先前提交的任务将被执行，但不会接受任何新任务。 如果已经关闭，调用没有其他影响
        executorService.shutdown();
        log.info("count:{}", count);


    }

    private static void add() {
        count++;
    }



}

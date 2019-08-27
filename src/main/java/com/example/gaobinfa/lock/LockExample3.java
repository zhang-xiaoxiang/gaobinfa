package com.example.gaobinfa.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
/**
 * LockExample3:ReentrantReadWriteLock实例  https://www.cnblogs.com/zaizhoumo/p/7782941.html
 * @author zhangxiaoxiang
 * @date 2019/8/27
 */
public class LockExample3 {
    /**
     * 待操作(读写的对象)资源
     */
    private final Map<String, Data> map = new TreeMap<>();
    /**
     * 在实际应用中，大部分情况下对共享数据（如缓存）的访问都是读操作远多于写操作，
     * 这时ReentrantReadWriteLock能够提供比排他锁更好的并发性和吞吐量。
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private final Lock readLock = lock.readLock();
    /**
     * 写锁
     */
    private final Lock writeLock = lock.writeLock();

    /**
     * 获取方法--读锁
     *
     * @param key
     * @return
     */
    public Data get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 读取所有的可以方法---读锁
     *
     * @return
     */
    public Set<String> getAllKeys() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 获取方法--写锁
     *
     * @param key
     * @param value
     * @return
     */
    public Data put(String key, Data value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 构造方法
     */
    class Data {

    }
}

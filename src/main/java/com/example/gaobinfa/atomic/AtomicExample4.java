package com.example.gaobinfa.atomic;

import com.example.gaobinfa.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ThreadSafe
/**
 * AtomicExample4:使用的是compareAndSwapObject实现CAS，比较的是两个对象的地址是否相等。也就是它可以保证你在修改对象引用时的线程安全性
 * @author zhangxiaoxiang
 * @date 2019/8/26
 */
public class AtomicExample4 {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        //如果是0就更新成2
        count.compareAndSet(0, 2); // 2
        count.compareAndSet(0, 1); // no
        count.compareAndSet(1, 3); // no
        count.compareAndSet(2, 4); // 4
        count.compareAndSet(3, 5); // no
        log.info("count:{}", count.get());
    }
}

package com.example.gaobinfa.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ConcurrentApi:测试JUC并发容器改善后的测试
 *
 * @author zhangxiaoxiang
 * @date: 2019/08/11
 */
@RestController
@RequestMapping("/get")
@Slf4j
public class ConcurrentApi {
    Integer i = 0;
     List<Integer> list = new CopyOnWriteArrayList<>();

    /**
     * 调用一次接口对集合add操作
     *
     * @return
     */
    @RequestMapping("/copyonwritearraylist")
    public Object testArrayList() {
        list.add(i);
        i++;
        //在没有高并发下正常请求5000次是从1到5000的
        log.info("使用并发集合JUC改善后数组长度=" + list.size());
        return "使用并发集合JUC改善后数组长度=" + list.size();
    }
}

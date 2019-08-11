package com.example.gaobinfa.api;

        import lombok.extern.slf4j.Slf4j;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        import java.util.ArrayList;
        import java.util.List;

/**
 * CommonApi:这里模拟常用的集合类,在高并发下体现不安全的演示示例
 *
 * @author zhangxiaoxiang
 * @date: 2019/08/11
 */
@RestController
@RequestMapping("/get")
@Slf4j
public class CommonApi {

    Integer i = 0;
    List<Integer> list = new ArrayList<>();

    /**
     * 调用一次接口对集合add操作
     *
     * @return
     */
    @RequestMapping("/testarraylist")
    public Object testArrayList() {
        list.add(i);
        i++;
        //在没有高并发下正常请求5000次是从1到5000的
        log.info("数组长度=" + list.size());
        return "数组长度=" + list.size();
    }
}

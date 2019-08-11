package com.example.gaobinfa.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Recommend:自定义用来标记【推荐】的类或者写法
 * @author zhangxiaoxiang
 * @date 2019/8/11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Recommend {

    String value() default "";
}

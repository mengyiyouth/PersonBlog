package com.mengyi.annotation;

import java.lang.annotation.*;

/**
 * @author mengyiyouth
 * @date 2020/7/16 12:54
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearRedisCache {
    /**
     * 需要级联删除的缓存
     * @return
     */
    Class<?>[] cascade() default {};
}

package com.homepaas.sls.data.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 2016/10/5.
 * Author: fmisser
 * Description: key-value对象缓存注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    /**
     * key-value 存储的key值,确保唯一性
     */
    String key();

    /**
     * 缓存的有效生命周期,默认则认为永久存储
     */
    long ttlMillis() default Long.MAX_VALUE;
}

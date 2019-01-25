package com.homepaas.sls.data.validator;

/**
 * Created by Administrator on 2015/12/21.
 * 数据验证器
 */

public interface Validator<T> {

    /**
     * 验证对象的有效性
     * @param t 验证的对象
     * @return 对象有效时返回true，否则返回false
     */
    boolean isValid(T t);
}

package com.homepaas.sls.data.validator.nullsafe;

import com.homepaas.sls.data.validator.Validator;

import javax.inject.Inject;

/**
 * Created by Administrator on 2015/12/21.
 * 空值校验器
 */

public class NullSafeValidator<T> implements Validator<T> {

    @Inject
    public NullSafeValidator() {

    }

    /**
     * 验证对象的有效性
     *
     * @param t 验证的对象
     * @return 对象有效时返回true，否则返回false
     */
    @Override
    public boolean isValid(T t) {
        return t != null;
    }
}

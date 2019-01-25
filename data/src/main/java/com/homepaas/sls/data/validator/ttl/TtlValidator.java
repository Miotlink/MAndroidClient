package com.homepaas.sls.data.validator.ttl;

import com.homepaas.sls.data.validator.Validator;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by Administrator on 2015/12/21.
 * 含有生存周期的对象数据校验器
 */

public class TtlValidator<T extends TtlObject> implements Validator<T> {

    private final long ttlMillis;

    /**
     * 构造生存周期对象校验器
     * @param ttl 该对象生存周期的时间长度
     * @param timeUnit 时间单位
     */
    @Inject
    public TtlValidator(long ttl, TimeUnit timeUnit) {
        this.ttlMillis = timeUnit.toMillis(ttl);
    }

    @Override
    public boolean isValid(T t) {
        return (t.getPersistedTime() + ttlMillis) > System.currentTimeMillis();
    }
}

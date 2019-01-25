package com.homepaas.sls.data.validator.ttl;

/**
 * Created by Administrator on 2015/12/21.
 * 如果使用TtlValidator对某个对象进行生存周期检验，则需要让该对象实现该接口
 */

public interface TtlObject {

    /**
     * 获取对象持久化的时刻,该对象应该在持久化的时候保存当时的时刻,以便在其他时刻正确的获得时间
     * @return 对象的持久化时刻
     */
    long getPersistedTime();
}

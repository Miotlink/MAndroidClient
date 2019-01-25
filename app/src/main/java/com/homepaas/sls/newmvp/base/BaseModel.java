package com.homepaas.sls.newmvp.base;

import rx.Subscription;

/**
 *  on 2017/6/4.
 */

public interface BaseModel {
    void dispose();

    void addSubscription(Subscription subscription);
}

package com.homepaas.sls.newmvp.base;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.network.RestApiHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * on 2017/6/10.
 * 封装网络订阅关系，解绑操作
 */

public class IBaseModel implements BaseModel {

//    @Inject
//    public RestApiHelper apiHelper= MainApplication.getMainApplication().getApplicationComponent().restA();
    public RestApiHelper apiHelper= SimpleTinkerInApplicationLike.getApplicationComponent().restA();
    //    保存观察者和订阅者的订阅关系对象
    public List<Subscription> subscriptionArrayList = new ArrayList<>();


    @Override
    public void dispose() {
        //解除compositeDisposable中所有保存的Disposable对象中的订阅关系
        if (subscriptionArrayList != null && subscriptionArrayList.size() > 0) {
            for (int i = 0; i < subscriptionArrayList.size(); i++) {
                Subscription subscription = subscriptionArrayList.get(i);
                if (!subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }
            }
            subscriptionArrayList.clear();
        }
    }

    @Override
    public void addSubscription(Subscription subscription) {
        subscriptionArrayList.add(subscription);
    }
}

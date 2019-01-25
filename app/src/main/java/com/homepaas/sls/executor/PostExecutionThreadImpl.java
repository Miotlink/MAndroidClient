package com.homepaas.sls.executor;

import com.homepaas.sls.domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

@Singleton
public class PostExecutionThreadImpl implements PostExecutionThread {

    @Inject
    PostExecutionThreadImpl() {}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();

    }
}

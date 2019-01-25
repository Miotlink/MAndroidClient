package com.homepaas.sls.domain.executor;

import rx.Scheduler;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

public interface PostExecutionThread {
    Scheduler getScheduler();
}

package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Administrator on 2015/12/22.
 */

public abstract class Interactor<T> {

    private JobExecutor jobExecutor;

    private PostExecutionThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    public Interactor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        this.jobExecutor = jobExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Observable<T> buildObservable();

    public void execute(Subscriber<T> subscriber) {
        this.subscription = buildObservable()
                .observeOn(postExecutionThread.getScheduler())
                .subscribeOn(Schedulers.from(jobExecutor))
                .subscribe(subscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}

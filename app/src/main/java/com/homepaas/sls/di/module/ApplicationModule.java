package com.homepaas.sls.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.Global;
import com.homepaas.sls.data.executor.JobExecutorImpl;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.executor.PostExecutionThreadImpl;
import com.homepaas.sls.location.LocationHelper;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.util.ToastUtil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2015/12/22.
 */
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }
    @Provides
    @Singleton
    ToastUtil toastUtil() {
        return new ToastUtil(application);
    }
    /**
     * 任务并行队列
     *
     * @return JobExecutor
     */
    @Provides
    @Singleton
    @Named("concurrent")
    JobExecutor provideConcurrentJobExecutor() {
        return new JobExecutorImpl(BuildConfig.CONCURRENT_JOB_EXECUTOR_CORE_POOL_SIZE,
                BuildConfig.CONCURRENT_JOB_EXECUTOR_MAXIMUM_POOL_SIZE,
                BuildConfig.JOB_EXECUTOR_KEPP_ALIVE_TIME_SEC,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 任务序列队列
     *
     * @return JobExecutor
     */
    @Provides
    @Singleton
    @Named("sequence")
    JobExecutor provideSequenceJobExecutor() {
        return new JobExecutorImpl(1,
                1,
                BuildConfig.JOB_EXECUTOR_KEPP_ALIVE_TIME_SEC,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 任务执行结果投递线程
     *
     * @param postExecutionThread PostExecutionThreadImpl 对象
     * @return PostExecutionThread
     */
    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(PostExecutionThreadImpl postExecutionThread) {
        return postExecutionThread;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    LocationHelper provideLocationHelper() {
        return new LocationHelper(application);
    }

    @Singleton
    @Provides
    Global provideGlobal() {
        return new Global();
    }
}

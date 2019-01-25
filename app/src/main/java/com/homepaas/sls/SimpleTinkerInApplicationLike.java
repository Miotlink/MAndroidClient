package com.homepaas.sls;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.webkit.WebView;

import com.baidu.mapapi.SDKInitializer;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.common.SPManager;
import com.homepaas.sls.di.component.ApplicationComponent;
import com.homepaas.sls.di.component.DaggerApplicationComponent;
import com.homepaas.sls.di.module.ApplicationModule;
import com.homepaas.sls.tinker.TinkerResultService;
import com.homepaas.sls.ui.imchating.util.DemoHelper;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PlatformUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.umeng.socialize.PlatformConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.homepaas.sls.BuildConfig.WECHAT_APP_ID;
import static com.homepaas.sls.BuildConfig.WECHAT_APP_SECRET;

/**
 * Created by zhanghongyang01 on 17/2/4.
 */

@DefaultLifeCycle(application = ".SimpleTinkerInApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class SimpleTinkerInApplicationLike extends ApplicationLike {
    private static List<Activity> activityList = new ArrayList<>();
    private static ApplicationComponent applicationComponent;
    private static Application mainApplication;
    public static boolean isDebug = BuildConfig.APP_IS_DEBUG;

    public SimpleTinkerInApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        //微信热更新
        TinkerInstaller.install(this, new DefaultLoadReporter(getApplication()), new DefaultPatchReporter(getApplication()), new DefaultPatchListener(getApplication()), TinkerResultService.class, new UpgradePatch());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    //此处写自己的Application逻辑
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("TAG", "application:onCreate");
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());//注意区分进程初始化不同的东西【避免其他无关进程不必要的初始化】
        if (!TextUtils.isEmpty(processName) && processName.equals(getApplication().getPackageName())) { //main Process 当前app进程
            clearActivity();
            mainApplication = getApplication();
            //webview内容是否可以调试
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
            }
            //Android7.0的照片问题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            initializeInjector();
            SPManager.getInstance().register(getApplication());
            //友盟分享
            share();
            //GrowingIO 初始化统计平台，并设置当前渠道名称PlatformUtils.getChannel(getApplication()))
            GrowingIO.startWithConfiguration(getApplication(), new Configuration()
                    .useID()
                    .trackAllFragments()
                    .setChannel(PlatformUtils.getChannel(getApplication())));
//        //环信
            DemoHelper.getInstance().init(getApplication(), isDebug);
            Context context = getApplication();
            // 获取当前包名
            String packageName = context.getPackageName();
            // 设置是否为上报进程
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
            strategy.setUploadProcess(processName == null || processName.equals(packageName));
            // 初始化Bugly
            CrashReport.initCrashReport(context, BuildConfig.Bugly, isDebug, strategy);
            //百度地图
            SDKInitializer.initialize(getApplication());
        } else {
            //极光
            JPushInterface.setDebugMode(isDebug);
            JPushInterface.init(getApplication());
        }
        LogUtils.i("TAG", "Application:onCreate");
    }

    private void share() {
        //weChat
        PlatformConfig.setWeixin(WECHAT_APP_ID, WECHAT_APP_SECRET);
        //PlatformConfig.setTencentWeibo();
        //QQ和QQ空间
//        PlatformConfig.setQQZone(QQ_ZONE_APP_ID, QQ_ZONE_APP_KEY);
        //新浪微博
//        PlatformConfig.setSinaWeibo(WEIBO_APP_KEY, WEIBO_APP_SECRET);
    }

    public static Application getMainApplication() {
        return mainApplication;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .build();
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static void addActivity(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    public static void getActivity(int index) {
        if (index > 0 && index < activityList.size() - 1) {
            activityList.get(index);
        }
    }

    public static Activity getCurrentActivity() {
        if (activityList != null && activityList.size() >= 1) {
            return activityList.get(activityList.size() - 1);
        } else {
            return null;
        }
    }

    public static Activity getActivity(Class clazz) {
        if (activityList != null && activityList.size() > 1) {
            for (int i = 0; i < activityList.size(); i++) {
                Activity activity = activityList.get(i);
                if (activity != null && (activity.getClass() == clazz)) {
                    return activity;
                }
            }
        }
        return null;
    }

    public static void startHome(Class clazz) {
        if (activityList != null && activityList.size() > 1) {
            List<Activity> activityIndex = new ArrayList<>();
            //
            for (int i = 0; i < activityList.size(); i++) {
                Activity activity = activityList.get(i);
                if (activity != null && !(activity.getClass() == clazz)) {
                    activityIndex.add(activity);
                }
            }
            for (int i = 0; i < activityIndex.size(); i++) {
                removeActivity(activityIndex.get(i));
            }
        }
    }

    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityList.remove(activity);
        }
    }

    public static void clearActivity() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityList.clear();
    }

    public static void clearOtherActivity(Activity activity0) {
        for (Activity activity : activityList) {
            if (activity != null && activity != activity0) {
                activity.finish();
            }
        }
        activityList.clear();
        activityList.add(activity0);
    }
}

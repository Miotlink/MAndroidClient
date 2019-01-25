package com.homepaas.sls.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import com.homepaas.sls.Global;
import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.util.LogUtils;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * An example full-screen currentAction that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends CommonBaseActivity {

    //首次载入
    boolean isFirstIn = false;
    //用pref记录是否为首次载入
//    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    @Inject
    Global global;

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟1.5秒
    private static final long SPLASH_DELAY_MILLIS = 1500;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Bind(R.id.splash)
    FrameLayout splash;
    private Bundle pushBundle;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        splash.setBackgroundResource(R.mipmap.newsplash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        LogUtils.i("SplashActivity:initView");
        //取出推送的参数
        pushBundle = getIntent().getExtras();
        init();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

    private void init() {
        // 使用SharedPreferences来记录程序的是否为首次载入的状态
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(global.getFirstPref(), MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        //直接写入
        preferences.edit().putBoolean("isFirstIn", false).apply();

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {
            // 使用Handler的postDelayed方法，1.5秒后执行跳转
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }

    }

    //跳转到主页
    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        if (pushBundle != null)
            intent.putExtras(pushBundle);
        SplashActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        SplashActivity.this.finish();
    }

    //跳转到引导页
    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        SplashActivity.this.finish();
    }

}

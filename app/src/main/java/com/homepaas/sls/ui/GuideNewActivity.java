package com.homepaas.sls.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideNewActivity extends CommonBaseActivity {

    @Bind(R.id.btnStartMainActivity)
    Button btnStartMainActivity;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_new;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btnStartMainActivity)
    public void startMainActivity() {
        // 设置已经引导
        goHome();
    }
    /**
     * 跳转到主页面
     */
    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //currentAction.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        finish();
    }
}

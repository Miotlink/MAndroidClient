package com.homepaas.sls.ui.personalcenter.more;

import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerFeedbackComponent;
import com.homepaas.sls.di.module.FeedbackModule;
import com.homepaas.sls.mvp.presenter.personalcenter.FeedbackPresenter;
import com.homepaas.sls.mvp.view.personalcenter.FeedbackView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class FeedbackActivity extends CommonBaseActivity implements FeedbackView {

    private static final String TAG = "FeedbackActivity";

    @Inject
    FeedbackPresenter mPresenter;

    @Bind(R.id.content)
    EditText mContent;

    @Bind(R.id.textCounter)
    TextView mTextCounter;

    @Bind(R.id.submit)
    View mSubmit;

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerFeedbackComponent.builder()
                .applicationComponent(getApplicationComponent())
                .feedbackModule(new FeedbackModule())
                .build()
                .inject(this);
    }


    @OnClick(R.id.submit)
    void submitFeedback() {
        String feedbackContent = mContent.getText().toString().trim();
        if (TextUtils.isEmpty(feedbackContent)) {
            showMessage("请填写反馈内容");
            return;
        }
        if (feedbackContent.length() >= 505) {
            showMessage("字数过多,请控制在500个字符以内");
            return;
        }
        mPresenter.submitFeedback(feedbackContent);
    }

    @OnTextChanged(R.id.content)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        mTextCounter.setText((start + count) + "/500");
        mSubmit.setEnabled((start + count) != 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPresenter.setFeedbackView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void afterFeedbackSubmitted() {
        showMessage("提交成功");
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

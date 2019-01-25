package com.homepaas.sls.ui.personalcenter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import butterknife.Bind;

public class EmptyMessageActivity extends CommonBaseActivity {

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.content)
    TextView mContent;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty_message;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra(Navigator.MESSAGE_TITLE);
        String content = getIntent().getStringExtra(Navigator.MESSAGE_CONTENT);
        mTitle.setText(title);
        mContent.setText(content);
    }

    @Override
    protected void initData() {

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

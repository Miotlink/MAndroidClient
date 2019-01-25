package com.homepaas.sls.ui.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.DaggerWorkerDetailComponent;
import com.homepaas.sls.di.component.WorkerDetailComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonDialog;

/**
 * 工人详情页面
 *
 * @author zhudongjie
 */
public class WorkerDetailActivity extends CommonBaseActivity implements HasComponent<WorkerDetailComponent>, WorkerDetailFragment.OnShowMessageListener {


    private static final String TAG = "WorkerDetailActivity";

    private static final String WD_TAG = "WorkerDetailFragment";

    private WorkerDetailComponent mComponent;

    private boolean mShowingMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView() {
        String id = getIntent().getStringExtra(Navigator.WORKER_ID);
        WorkerDetailFragment fragment = WorkerDetailFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment,WD_TAG)
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        mComponent = DaggerWorkerDetailComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setVisible(!mShowingMessage);
        menu.getItem(1).setVisible(!mShowingMessage);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.report:
                CommonDialog dialog = new CommonDialog.Builder()
                        .setTitle("无效电话举报")
                        .setCancelButton(getString(android.R.string.cancel), null)
                        .setConfirmButton(getString(android.R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WorkerDetailFragment fragment = (WorkerDetailFragment) getSupportFragmentManager().findFragmentByTag(WD_TAG);
                                fragment.report();
                            }
                        })
                        .setContent("纠错，电话号码有问题!")
                        .create();
                dialog.show(getSupportFragmentManager(), null);
                return true;
            case R.id.share:
                WorkerDetailFragment fragment = (WorkerDetailFragment) getSupportFragmentManager().findFragmentByTag(WD_TAG);
                fragment.share();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mShowingMessage)
            mShowingMessage = false;
        super.onBackPressed();
    }

    @Override
    public WorkerDetailComponent getComponent() {
        return mComponent;
    }

    @Override
    public void onShowMessage() {
        mShowingMessage = true;
        String id = getIntent().getStringExtra(Navigator.WORKER_ID);
        WorkerMessageFragment fragment = WorkerMessageFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}

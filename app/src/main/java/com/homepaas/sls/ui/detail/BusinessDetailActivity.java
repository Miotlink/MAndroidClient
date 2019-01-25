package com.homepaas.sls.ui.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.BusinessDetailComponent;
import com.homepaas.sls.di.component.DaggerBusinessDetailComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonDialog;

/**
 * 商户详情
 *
 * @author zhudongjie
 */
@Deprecated
public class BusinessDetailActivity extends CommonBaseActivity implements BusinessDetailFragment.OnShowMessageListener
        , HasComponent<BusinessDetailComponent> {

    private static final String TAG = "ShopDetailActivity";

    private static final String BD_TAG = "BusinessDetailFragment";

    private BusinessDetailComponent mComponent;

    private boolean mShowingMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView() {
        String id = getIntent().getStringExtra(Navigator.BUSINESS_ID);
        BusinessDetailFragment fragment = BusinessDetailFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment,BD_TAG)
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        mComponent = DaggerBusinessDetailComponent
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
                                BusinessDetailFragment fragment = (BusinessDetailFragment) getSupportFragmentManager()
                                        .findFragmentByTag(BD_TAG);
                                fragment.report();
                            }
                        })
                        .setContent("纠错，电话号码有问题!")
                        .create();
                dialog.show(getSupportFragmentManager(), null);
                return true;
            case R.id.share:
                BusinessDetailFragment fragment = (BusinessDetailFragment) getSupportFragmentManager()
                        .findFragmentByTag(BD_TAG);
                fragment.share();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showMessage() {
        mShowingMessage = true;
        String id = getIntent().getStringExtra(Navigator.BUSINESS_ID);
        BusinessMessageFragment fragment = BusinessMessageFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (mShowingMessage) {
            mShowingMessage = false;
        }
        super.onBackPressed();
    }

    @Override
    public BusinessDetailComponent getComponent() {
        return mComponent;
    }

}

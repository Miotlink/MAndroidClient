package com.homepaas.sls.ui.newdetail;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMerchantWorkerComponet;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.MorePopuWindow;

public class MerchantWorkerActivity extends CommonBaseActivity {
    private static final String WD_TAG = "MerchantWorkerActivity";
    private static final String BD_TAG = "MerchantDetailFragment";
    private MenuItem menuItem;
    private MorePopuWindow morePopuWindow;

    public static void start(Context context, String type, String id ){
        Intent intent = new Intent(context, MerchantWorkerActivity.class);
        intent.putExtra(Navigator.MY_ID, id);
        intent.putExtra(Navigator.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView() {
        String id = getIntent().getStringExtra(Navigator.MY_ID);
        String type = getIntent().getStringExtra(Navigator.TYPE);
        MerchantDetailFragment fragment = MerchantDetailFragment.newInstance(type, id);
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
        DaggerMerchantWorkerComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    private boolean isShow = false;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.more:
                MerchantDetailFragment fragment = (MerchantDetailFragment)getSupportFragmentManager()
                        .findFragmentByTag(BD_TAG);
                //关闭/显示Popu
                    fragment.showMore();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

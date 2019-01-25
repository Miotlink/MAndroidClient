package com.homepaas.sls.ui.order.servicemerchant;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.imchating.ImLoginActivity;

import butterknife.OnClick;

/**
 * Created by mhy on 2017/12/26.
 * 商家界面 显示二级服务品类
 */

public class MerchantActivity extends CommonBaseActivity {

    private ServiceItem serviceItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchant;
    }

    public static void start(Context context, ServiceItem serviceItem) {
        Intent starter = new Intent(context, MerchantActivity.class);
        starter.putExtra("serviceItem", serviceItem);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        serviceItem = (ServiceItem) getIntent().getSerializableExtra("serviceItem");
        showFragment(R.id.fl_content, MerchantFragment.newInstance(serviceItem,true,""));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.back, R.id.call_secretary})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.call_secretary:
                //进入客服页面
                ImLoginActivity.start(this, serviceItem);
                break;
        }
    }
}

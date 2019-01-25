package com.homepaas.sls.ui.order.history;

import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import butterknife.Bind;

/**
 * Created by mhy on 2017/8/26.
 * 历史订单
 * 显示“已取消、已完成(且已评价)”状态的订单
 */

public class HistoryOrderActivity extends CommonBaseActivity {
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_order;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        replaceFragment(R.id.fl_content, HistoryFragment.newInstance());
    }

    @Override
    protected void initData() {
    }

}

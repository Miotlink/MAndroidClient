package com.homepaas.sls.ui.order.detail;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;

import com.homepaas.sls.R;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import butterknife.Bind;

/**
 * Created by CJJ on 2016/7/25.
 * 对订单详情页考虑用RecyclerView进行重写，便于每个板块部分重用，Activity只对订单操作进行处理
 */
public class DetailActivity extends CommonBaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    OrderPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_backup;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        presenter = new OrderPresenter();
    }

    @Override
    protected void initData() {

    }

}

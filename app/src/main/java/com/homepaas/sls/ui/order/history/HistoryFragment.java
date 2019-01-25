package com.homepaas.sls.ui.order.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.di.component.DaggerHistoryOrderComponent;
import com.homepaas.sls.di.module.HistoryModule;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.newmvp.contract.HistoryContract;
import com.homepaas.sls.newmvp.presenter.HistoryPresenter;
import com.homepaas.sls.ui.adapter.HistoryAdapter;
import com.homepaas.sls.ui.common.fragment.BaseListFragment;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mhy on 2017/8/28.
 */

public class HistoryFragment extends BaseListFragment<OrderEntity> implements HistoryContract.View {

    @Inject
    HistoryPresenter mHistoryPresenter;

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private HistoryAdapter historyAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setMoreLoadable(true);
        setEmptyView("当前没有订单，赶快去看看需要什么服务吧");
    }

    @Override
    public void networkErrorRefresh() {
        super.networkErrorRefresh();
        //让宿主去进行网络刷新数据
        onRefresh();
    }

    @Override
    protected void initializeInjector() {
        DaggerHistoryOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .historyModule(new HistoryModule(this))
                .build()
                .inject(this);
    }

    //刷新
    @Override
    public void onRefresh() {

    }

    //加载
    @Override
    public void onLoadMore() {

    }

    @Override
    public void showError(Throwable e) {
        if (historyAdapter == null || historyAdapter.getData() == null || historyAdapter.getData().size() == 0)  //没有缓存数据提示网络错误布局，如果有缓存数据就统一通过toast进行提示
        {
            OrderInfo orderInfo = PreferencesUtil.getObject(StaticData.HISTORY_ORDER, OrderInfo.class);
            if (orderInfo != null)
                render(orderInfo.getOrders());
        }
        super.showError(e);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List list) {
        historyAdapter = new HistoryAdapter(mContext);
        return historyAdapter;
    }

    @Override
    public void render(@Nullable List list) {
        super.render(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHistoryPresenter.unView();
    }

    @Override
    public void initHistoryData(List<OrderEntity> orderEntities) {

    }
}

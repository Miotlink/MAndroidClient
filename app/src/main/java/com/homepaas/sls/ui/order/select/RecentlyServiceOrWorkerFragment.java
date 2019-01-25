package com.homepaas.sls.ui.order.select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.SelectServiceOrWorkerEntity;
import com.homepaas.sls.event.EventChangeViewPagerStatus;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.newmvp.contract.RecentlyServiceOrWorkerContract;
import com.homepaas.sls.newmvp.presenter.RecentlyServiceOrWorkerPresenter;
import com.homepaas.sls.ui.adapter.RecentlyServiceWorkerAdapter;
import com.homepaas.sls.ui.common.fragment.CommonMvpLazyLoadFragment;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.SelectWorkerOrServiceNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 最近商户/工人
 */

public class RecentlyServiceOrWorkerFragment extends CommonMvpLazyLoadFragment<RecentlyServiceOrWorkerContract.Presenter> implements HeaderViewLayout.OnRefreshListener, RecentlyServiceOrWorkerContract.View, RecentlyServiceWorkerAdapter.OnSelectClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    //TabType：1：最近Tab，2：附近Tab；必填
    public int TabType = 1;
    private int currentPage = 1;
    private int pageSize = 10;
    private String IsEnablePaging = "1";
    private String address, serviceId;
    private Subscription subscribe;

    private SelectWorkerOrServiceNetWorkErrorViewReplacer selectWorkerOrServiceNetWorkErrorViewReplacer;
    private RecentlyServiceWorkerAdapter recentlyServiceWorkerAdapter;

    public static RecentlyServiceOrWorkerFragment newInstance(String address, String serviceId) {
        Bundle args = new Bundle();
        RecentlyServiceOrWorkerFragment fragment = new RecentlyServiceOrWorkerFragment();
        args.putString(StaticData.ADDRESS, address);
        args.putString(StaticData.SERVICE_ID, serviceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recently_service_or_worker;
    }

    @Override
    protected void initView() {
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recentlyServiceWorkerAdapter = new RecentlyServiceWorkerAdapter(mContext);
        recentlyServiceWorkerAdapter.setOnSelectClickListener(this);
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(recentlyServiceWorkerAdapter);

        selectWorkerOrServiceNetWorkErrorViewReplacer = new SelectWorkerOrServiceNetWorkErrorViewReplacer(mContext, refreshLayout, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                updateList();
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (subscribe != null && !subscribe.isUnsubscribed())
            subscribe.unsubscribe();
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        super.onDestroyView();
    }

    @Override
    protected void initData() {
        address = getArguments().getString(StaticData.ADDRESS);
        serviceId = getArguments().getString(StaticData.SERVICE_ID);
        updateList();
    }


    public void updateList() {
        currentPage = 1;
        mPresenter.getListData(TabType + "", serviceId, address, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    public void loadList() {
        currentPage++;
        mPresenter.getListData(TabType + "", serviceId, address, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    @Override
    protected RecentlyServiceOrWorkerContract.Presenter getPresenter() {
        return new RecentlyServiceOrWorkerPresenter();
    }

    @Override
    public void initListData(SelectServiceOrWorkerEntity selectServiceOrWorkerEntity) {
        refreshLayout.stopRefresh();
        if ((selectServiceOrWorkerEntity == null || selectServiceOrWorkerEntity.getChooseWorkerOrMerchantInfos() == null || selectServiceOrWorkerEntity.getChooseWorkerOrMerchantInfos().size() == 0) && (recentlyServiceWorkerAdapter.getData() == null || recentlyServiceWorkerAdapter.getData().size() == 0)) {
            selectWorkerOrServiceNetWorkErrorViewReplacer.showEmptyView(R.string.empty_hint2);
            refreshLayout.setCanLoadMore(false);

//            Observable.from(getUri()).timer(2,TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(uri
//                    -> {
//                Log.e("uri",uri+"");
//                Glide.with(this).load(uri).into(img);});
//            ｝
            //最近这个界面没有工人时并没有1s之后跳到附近商户下
            subscribe = Observable.timer(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            EventBus.getDefault().post(new EventChangeViewPagerStatus());
                        }
                    });
            return;
        }
        selectWorkerOrServiceNetWorkErrorViewReplacer.showOriginalLayout();
        List<SelectServiceOrWorkerEntity.ChooseWorkerOrMerchantInfosBean> chooseWorkerOrMerchantInfos = selectServiceOrWorkerEntity.getChooseWorkerOrMerchantInfos();
        if (currentPage == 1)//刷新
        {
            recentlyServiceWorkerAdapter.updateList(chooseWorkerOrMerchantInfos);
        } else {//加载
            recentlyServiceWorkerAdapter.append(chooseWorkerOrMerchantInfos);
        }

        //是否可以加载
        if (chooseWorkerOrMerchantInfos.size() < pageSize) {
            refreshLayout.setCanLoadMore(false);
        } else {
            refreshLayout.setCanLoadMore(true);
        }
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        refreshLayout.stopRefresh();
        if (recentlyServiceWorkerAdapter.getData() == null || recentlyServiceWorkerAdapter.getData().size() == 0) {
            selectWorkerOrServiceNetWorkErrorViewReplacer.showErrorLayout();
        }
    }

    /**
     * 预约
     *
     * @param position
     */
    @Override
    public void onSelectClick(int position) {
        //UserId (string, optional): 工人或者商户的UserId 选择某个工人或者商户后，使用UserId参数传递,
        SelectServiceOrWorkerEntity.ChooseWorkerOrMerchantInfosBean chooseWorkerOrMerchantInfosBean = recentlyServiceWorkerAdapter.getData().get(position);
        String userId = chooseWorkerOrMerchantInfosBean.getUserId();
        Intent intent = new Intent();
        intent.putExtra(StaticData.USER_ID, userId);
        intent.putExtra(StaticData.USER_SELECT_SERVICE_OR_WORKER_NAME, chooseWorkerOrMerchantInfosBean.getName());
        getActivity().setResult(Activity.RESULT_OK, intent);
        ActivityCompat.finishAfterTransition(getActivity());
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    @Override
    public void onLoadMore() {
        loadList();
    }

    @Override
    public void onModeChanged(@HeaderViewLayout.Mode int mode) {

    }


}

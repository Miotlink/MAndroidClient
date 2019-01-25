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
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.newmvp.contract.NearbyServiceOrWorkerContract;
import com.homepaas.sls.newmvp.presenter.NearbyServiceOrWorkerPresenter;
import com.homepaas.sls.ui.adapter.NearbyServiceWorkerAdapter;
import com.homepaas.sls.ui.common.fragment.CommonMvpLazyLoadFragment;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.SelectWorkerOrServiceNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.StaticData;

import java.util.List;

import butterknife.Bind;


/**
 * 附近商户/工人
 */

public class NearbyServiceOrWorkerFragment extends CommonMvpLazyLoadFragment<NearbyServiceOrWorkerContract.Presenter> implements HeaderViewLayout.OnRefreshListener, NearbyServiceOrWorkerContract.View, NearbyServiceWorkerAdapter.OnSelectClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    public int TabType = 2;
    private int currentPage = 1;
    private int pageSize = 10;
    private String IsEnablePaging = "1";
    private String address, serviceId;

    private SelectWorkerOrServiceNetWorkErrorViewReplacer selectWorkerOrServiceNetWorkErrorViewReplacer;
    private NearbyServiceWorkerAdapter nearbyServiceWorkerAdapter;

    public static NearbyServiceOrWorkerFragment newInstance(String address, String serviceId) {
        Bundle args = new Bundle();
        NearbyServiceOrWorkerFragment fragment = new NearbyServiceOrWorkerFragment();
        args.putString(StaticData.ADDRESS, address);
        args.putString(StaticData.SERVICE_ID, serviceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby_service_or_worker;
    }

    @Override
    protected void initView() {
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        nearbyServiceWorkerAdapter = new NearbyServiceWorkerAdapter(mContext);
        nearbyServiceWorkerAdapter.setOnSelectClickListener(this);
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(nearbyServiceWorkerAdapter);

        selectWorkerOrServiceNetWorkErrorViewReplacer = new SelectWorkerOrServiceNetWorkErrorViewReplacer(mContext, refreshLayout, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                updateList();
            }
        });
    }

    @Override
    protected void initData() {
        address = getArguments().getString(StaticData.ADDRESS);
        serviceId = getArguments().getString(StaticData.SERVICE_ID);
        updateList();
    }


    public void updateList() {
        currentPage = 1;
        if (mPresenter != null)
            mPresenter.getListData(TabType + "", serviceId, address, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    public void loadList() {
        currentPage++;
        mPresenter.getListData(TabType + "", serviceId, address, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    @Override
    protected NearbyServiceOrWorkerContract.Presenter getPresenter() {
        return new NearbyServiceOrWorkerPresenter();
    }

    @Override
    public void initListData(SelectServiceOrWorkerEntity selectServiceOrWorkerEntity) {
        refreshLayout.stopRefresh();
        if ((selectServiceOrWorkerEntity == null || selectServiceOrWorkerEntity.getChooseWorkerOrMerchantInfos() == null || selectServiceOrWorkerEntity.getChooseWorkerOrMerchantInfos().size() == 0) && (nearbyServiceWorkerAdapter.getData() == null || nearbyServiceWorkerAdapter.getData().size() == 0)) {
            selectWorkerOrServiceNetWorkErrorViewReplacer.showEmptyView(R.string.empty_hint1);
            refreshLayout.setCanLoadMore(false);
            return;
        }
        selectWorkerOrServiceNetWorkErrorViewReplacer.showOriginalLayout();
        List<SelectServiceOrWorkerEntity.ChooseWorkerOrMerchantInfosBean> chooseWorkerOrMerchantInfos = selectServiceOrWorkerEntity.getChooseWorkerOrMerchantInfos();
        if (currentPage == 1)//刷新
        {
            nearbyServiceWorkerAdapter.updateList(chooseWorkerOrMerchantInfos);
        } else {//加载
            nearbyServiceWorkerAdapter.append(chooseWorkerOrMerchantInfos);
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
        if (nearbyServiceWorkerAdapter.getData() == null || nearbyServiceWorkerAdapter.getData().size() == 0) {
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
        SelectServiceOrWorkerEntity.ChooseWorkerOrMerchantInfosBean chooseWorkerOrMerchantInfosBean = nearbyServiceWorkerAdapter.getData().get(position);
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

    @Override
    public void onDestroyView() {
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        super.onDestroyView();
    }
}

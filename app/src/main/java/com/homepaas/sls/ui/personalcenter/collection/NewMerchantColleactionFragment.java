package com.homepaas.sls.ui.personalcenter.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.data.network.dataformat.CollectedBusinessBody;
import com.homepaas.sls.event.ColleActionTabEvent;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.newmvp.contract.ColleactionMerchantContract;
import com.homepaas.sls.newmvp.presenter.ColleactionMerchainPresenter;
import com.homepaas.sls.ui.common.fragment.CommonMvpLazyLoadFragment;
import com.homepaas.sls.ui.newdetail.MerchantWorkerActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.personalcenter.adapter.BusinessCollectionsAdapter;
import com.homepaas.sls.ui.personalcenter.adapter.OnCollectedItemClickListener;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.ColleactionReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;


/**
 * 商户收藏
 */

public class NewMerchantColleactionFragment extends CommonMvpLazyLoadFragment<ColleactionMerchantContract.Presenter> implements HeaderViewLayout.OnRefreshListener, ColleactionMerchantContract.View, OnCollectedItemClickListener {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private ColleactionReplacer colleactionReplacer;
    private BusinessCollectionsAdapter businessCollectionsAdapter;

    public static NewMerchantColleactionFragment newInstance() {
        Bundle args = new Bundle();
        NewMerchantColleactionFragment fragment = new NewMerchantColleactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_colleaction_service_or_worker;
    }

    @Override
    protected void initView() {
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        businessCollectionsAdapter = new BusinessCollectionsAdapter(null);
        businessCollectionsAdapter.setOnCollectedItemClickListener(this);
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(businessCollectionsAdapter);

        colleactionReplacer = new ColleactionReplacer(mContext, refreshLayout, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                updateList();
            }
        });

        //不可加载
        refreshLayout.setCanLoadMore(false);
    }

    @Override
    protected void initData() {
        updateList();
        CollectedBusinessBody collectedBusinessBody = PreferencesUtil.getObject(StaticData.COLLEACTION_MERCHANT, CollectedBusinessBody.class);
        if (collectedBusinessBody != null) {
            setListData(collectedBusinessBody);
        }
    }


    public void updateList() {
        mPresenter.getListData();
    }

    @Override
    protected ColleactionMerchantContract.Presenter getPresenter() {
        return new ColleactionMerchainPresenter();
    }


    @Override
    public void showError(Throwable e) {
        super.showError(e);
        refreshLayout.stopRefresh();
        if (businessCollectionsAdapter.getmBusinessInfoModelList() == null || businessCollectionsAdapter.getmBusinessInfoModelList().size() == 0) {
            colleactionReplacer.showErrorLayout();
        }
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onModeChanged(@HeaderViewLayout.Mode int mode) {

    }


    @Override
    public void onItemClick(int position, String id, boolean isWorker) {
        Intent intent = new Intent(mContext, MerchantWorkerActivity.class);
        intent.putExtra(Navigator.MY_ID, id);
        intent.putExtra(Navigator.TYPE, Constant.SERVICE_TYPE_BUSINESS);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onLikeChecked(int position, boolean checked, IServiceInfo item) {

    }

    @Override
    public void onItemDelete(int position, String id, boolean isWorker) {
        mPresenter.cancelCollectWorker(id, false);
    }

    @Override
    public void onDestroyView() {
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        super.onDestroyView();
    }

    @Override
    public void initListData(CollectedBusinessBody cllectedBusinessBody) {
        refreshLayout.stopRefresh();
        if ((cllectedBusinessBody == null
                || cllectedBusinessBody.getCollectedBusinesses() == null
                || cllectedBusinessBody.getCollectedBusinesses().size() == 0)) {
            colleactionReplacer.showEmptyView(getResources().getString(R.string.collection_empty2));
            EventBus.getDefault().post(new ColleActionTabEvent(1, "0"));
            return;
        }
        setListData(cllectedBusinessBody);
    }

    @Override
    public void cancelCollectBusinessSuccess() {
        showMessage("删除成功");
        if (businessCollectionsAdapter.getmBusinessInfoModelList() == null || businessCollectionsAdapter.getmBusinessInfoModelList().size() == 0) {
            colleactionReplacer.showEmptyView(getResources().getString(R.string.collection_empty2));
        }
        EventBus.getDefault().post(new ColleActionTabEvent(1, businessCollectionsAdapter.getmBusinessInfoModelList().size() + ""));
        //更新緩存數據
        PreferencesUtil.saveObject(StaticData.COLLEACTION_MERCHANT, new CollectedBusinessBody(businessCollectionsAdapter.getmBusinessInfoModelList()));

    }

    private void setListData(CollectedBusinessBody collectedBusinessBody) {
        PreferencesUtil.saveObject(StaticData.COLLEACTION_MERCHANT, collectedBusinessBody);
        colleactionReplacer.showOriginalLayout();
        businessCollectionsAdapter.setBusinessInfoModelList(collectedBusinessBody.getCollectedBusinesses());
        EventBus.getDefault().post(new ColleActionTabEvent(1, collectedBusinessBody.getCollectedBusinesses().size() + ""));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateList();
    }
}

package com.homepaas.sls.ui.personalcenter.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.data.network.dataformat.CollectedWorkerBody;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.ColleActionTabEvent;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.newmvp.contract.ColleactionWorkerContract;
import com.homepaas.sls.newmvp.presenter.ColleactionWorkerPresenter;
import com.homepaas.sls.ui.common.fragment.CommonMvpLazyLoadFragment;
import com.homepaas.sls.ui.newdetail.MerchantWorkerActivity;
import com.homepaas.sls.ui.personalcenter.adapter.OnCollectedItemClickListener;
import com.homepaas.sls.ui.personalcenter.adapter.WorkerCollectionsAdapter;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.ColleactionReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;


/**
 * 工人收藏
 */

public class NewWorkerColleactionFragment extends CommonMvpLazyLoadFragment<ColleactionWorkerContract.Presenter> implements HeaderViewLayout.OnRefreshListener, ColleactionWorkerContract.View, OnCollectedItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private ColleactionReplacer colleactionReplacer;
    private WorkerCollectionsAdapter workerCollectionsAdapter;

    public static NewWorkerColleactionFragment newInstance() {
        Bundle args = new Bundle();
        NewWorkerColleactionFragment fragment = new NewWorkerColleactionFragment();
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
        workerCollectionsAdapter = new WorkerCollectionsAdapter(null);
        workerCollectionsAdapter.setOnCollectedItemClickListener(this);
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(workerCollectionsAdapter);

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
        CollectedWorkerBody collectedWorkerBody = PreferencesUtil.getObject(StaticData.COLLEACTION_WORKER, CollectedWorkerBody.class);
        if (collectedWorkerBody != null) {
            setListData(collectedWorkerBody);
        }
    }


    public void updateList() {
        mPresenter.getListData();
    }

    @Override
    protected ColleactionWorkerContract.Presenter getPresenter() {
        return new ColleactionWorkerPresenter();
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        refreshLayout.stopRefresh();
        if (workerCollectionsAdapter.getmWorkerInfoModelList() == null || workerCollectionsAdapter.getmWorkerInfoModelList().size() == 0) {
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
    public void initListData(CollectedWorkerBody collectedWorkerBody) {
        refreshLayout.stopRefresh();
        if ((collectedWorkerBody == null
                || collectedWorkerBody.getCollectedWorkers() == null
                || collectedWorkerBody.getCollectedWorkers().size() == 0)) {
            colleactionReplacer.showEmptyView(getResources().getString(R.string.collection_empty1));
            EventBus.getDefault().post(new ColleActionTabEvent(0, "0"));
            return;
        }
        setListData(collectedWorkerBody);
    }

    @Override
    public void cancelCollectWorkerSuccess() {
        showMessage("删除成功");
        if (workerCollectionsAdapter.getmWorkerInfoModelList() == null || workerCollectionsAdapter.getmWorkerInfoModelList().size() == 0) {
            colleactionReplacer.showEmptyView(getResources().getString(R.string.collection_empty1));
        }
        EventBus.getDefault().post(new ColleActionTabEvent(0, workerCollectionsAdapter.getmWorkerInfoModelList().size() + ""));
        //更新緩存數據
        PreferencesUtil.saveObject(StaticData.COLLEACTION_WORKER, new CollectedWorkerBody(workerCollectionsAdapter.getmWorkerInfoModelList()));
    }

    private void setListData(CollectedWorkerBody collectedWorkerBody) {
        PreferencesUtil.saveObject(StaticData.COLLEACTION_WORKER, collectedWorkerBody);
        colleactionReplacer.showOriginalLayout();
        workerCollectionsAdapter.setWorkerInfoModelList(collectedWorkerBody.getCollectedWorkers());
        EventBus.getDefault().post(new ColleActionTabEvent(0, collectedWorkerBody.getCollectedWorkers().size() + ""));
    }

    @Override
    public void onItemClick(int position, String id, boolean isWorker) {
//        mNavigator.showMerchantWorkerDetail(mContext, Constant.SERVICE_TYPE_WORKER, id);
        Intent intent = new Intent(mContext, MerchantWorkerActivity.class);
        intent.putExtra(Navigator.MY_ID, id);
        intent.putExtra(Navigator.TYPE, Constant.SERVICE_TYPE_WORKER);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateList();
    }
}

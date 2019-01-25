package com.homepaas.sls.ui.newdetail;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerBusWorkerServiceComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;
import com.homepaas.sls.domain.entity.WorkerServiceTypePrice;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.presenter.newdetail.WorkerServicePriceListPresenter;
import com.homepaas.sls.mvp.view.newdetail.WorkerServicePriceListView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.newdetail.adapter.WorkerServiceItemAdapter;
import com.homepaas.sls.ui.order.directOrder.PlaceOrderActivity;
import com.homepaas.sls.ui.widget.ChooseServiceActionSheet;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by JWC on 2017/2/6.
 */

public class WorkerServiceFragment extends CommonBaseFragment implements WorkerServicePriceListView, WorkerServiceItemAdapter.OnWorkerThreeBuyClickListener, WorkerServiceItemAdapter.OnWorkerFourBuyClickListener, ChooseServiceActionSheet.OnServiceSelectClickListener {


    @Bind(R.id.worker_recyclerView)
    RecyclerView workerRecyclerView;
    @Bind(R.id.container)
    CoordinatorLayout container;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private String workerId;
    private String gender;

    WorkerServiceItemAdapter workerServiceItemAdapter;
    @Inject
    WorkerServicePriceListPresenter workerServicePriceListPresenter;

    private static final String WORKER_SERVICE_ID = "workerServiceId";
    private static final String GENDER = "gender";
    private ChooseServiceActionSheet chooseServiceActionSheet;

    public static WorkerServiceFragment newInstance(String id) {
        WorkerServiceFragment workerServiceFragment = new WorkerServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WORKER_SERVICE_ID, id);
        workerServiceFragment.setArguments(bundle);
        return workerServiceFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workerId = getArguments().getString(WORKER_SERVICE_ID);
            gender = getArguments().getString(GENDER);
        }
    }

    public void setgender(String gender) {
        this.gender = gender;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_worker_service;
    }

    @Override
    protected void initView() {
        workerServiceItemAdapter = new WorkerServiceItemAdapter(getActivity());
        workerServiceItemAdapter.setOnWorkerThreeBuyClickListener(this);
        workerServiceItemAdapter.setOnWorkerFourBuyClickListener(this);
        workerRecyclerView.addItemDecoration(new SimpleItemDecoration(getActivity(), SimpleItemDecoration.VERTICAL_LIST));
        workerRecyclerView.setAdapter(workerServiceItemAdapter);
        workerServicePriceListPresenter.setWorkerServicePriceListView(this);
        workerServicePriceListPresenter.getWorkerServicePriceList(workerId);
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onFourBuyClick(WorkerServiceTypePrice workerServiceTypePrice) {
        chooseServiceActionSheet = new ChooseServiceActionSheet.Builder()
                .date(workerServiceTypePrice)
                .build();
        chooseServiceActionSheet.setListener(this);
        chooseServiceActionSheet.show(getActivity());
    }

    @Override
    public void onThreeBuyClick(WorkerServiceTypePrice workerServiceTypePrice) {
        if (!TextUtils.isEmpty(gender) && workerServiceTypePrice != null) {
            PlaceOrderActivity.start(getActivity(), Constant.SERVICE_TYPE_WORKER_INT, workerId, gender, workerServiceTypePrice);
        }
    }

    @Override
    public void render(WorkerServicePriceListEntity workerServicePriceListEntity) {
        if (workerServicePriceListEntity != null) {
            workerServiceItemAdapter.setList(workerServicePriceListEntity.getTotalList());
        }
    }

    @Override
    public void onServiceSelect(WorkerServiceTypePrice workerServiceTypePrice) {
        PlaceOrderActivity.start(getActivity(), Constant.SERVICE_TYPE_WORKER_INT, workerId, gender, workerServiceTypePrice);
    }

    @Override
    protected void initializeInjector() {
        DaggerBusWorkerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(getActivity()))
                .build().inject(this);
    }
}

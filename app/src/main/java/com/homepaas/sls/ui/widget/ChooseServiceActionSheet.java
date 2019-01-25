package com.homepaas.sls.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.WorkerServiceTypePrice;
import com.homepaas.sls.ui.newdetail.adapter.WorkerAcionChooseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by JWC on 2017/2/10.
 */

public class ChooseServiceActionSheet extends ActionSheet implements WorkerAcionChooseAdapter.onItemClickListener {


    private static final String WORKER_SERVICE = "workerService";
    @Bind(R.id.service_name)
    TextView serviceName;
    @Bind(R.id.choose_service_recycleView)
    RecyclerView chooseServiceRecycleView;
    @Bind(R.id.action_cancel)
    Button actionCancel;
    private View sheetView;
    private OnServiceSelectClickListener onServiceSelectClickListener;
    private WorkerAcionChooseAdapter workerAcionChooseAdapter;
    private WorkerServiceTypePrice workerServiceTypePrice;

    public static ChooseServiceActionSheet newInstance() {
        Bundle args = new Bundle();
        ChooseServiceActionSheet fragment = new ChooseServiceActionSheet();
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnServiceSelectClickListener onServiceSelectClickListener) {
        this.onServiceSelectClickListener = onServiceSelectClickListener;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.action_sheet_choose_service;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        sheetView = getSheetView();
        ButterKnife.bind(this, sheetView);
        Bundle bundle = null;
        if (getArguments() != null) {
            bundle = getArguments();
        }
        workerServiceTypePrice = (WorkerServiceTypePrice) bundle.getSerializable(WORKER_SERVICE);
        if (workerServiceTypePrice != null) {
            serviceName.setText(workerServiceTypePrice.getName());
            workerAcionChooseAdapter = new WorkerAcionChooseAdapter(workerServiceTypePrice.getChildList());
            workerAcionChooseAdapter.setonItemClickListener(this);
            chooseServiceRecycleView.addItemDecoration(new SimpleItemDecoration(getActivity(), SimpleItemDecoration.VERTICAL_LIST));
            chooseServiceRecycleView.setAdapter(workerAcionChooseAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(WorkerServiceTypePrice workerServiceTypePrice) {
        if (onServiceSelectClickListener != null) {
            onServiceSelectClickListener.onServiceSelect(workerServiceTypePrice);
            dismiss();
        }
    }


    public static class Builder {
        WorkerServiceTypePrice workerServiceTypePrice;
        OnServiceSelectClickListener onServiceSelectClickListener;

        public Builder date(WorkerServiceTypePrice workerServiceTypePrice) {
            this.workerServiceTypePrice = workerServiceTypePrice;
            return this;
        }

        public ChooseServiceActionSheet build() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(WORKER_SERVICE, workerServiceTypePrice);
            ChooseServiceActionSheet chooseServiceActionSheet = ChooseServiceActionSheet.newInstance();
            chooseServiceActionSheet.setArguments(bundle);
            chooseServiceActionSheet.setListener(onServiceSelectClickListener);
            return chooseServiceActionSheet;
        }

        public Builder cb(OnServiceSelectClickListener onServiceSelectClickListener) {
            this.onServiceSelectClickListener = onServiceSelectClickListener;
            return this;

        }
    }

    @OnClick({R.id.action_cancel})
    void cancel(){
        dismiss();
    }

    public interface OnServiceSelectClickListener {
        void onServiceSelect(WorkerServiceTypePrice workerServiceTypePrice);
    }
}

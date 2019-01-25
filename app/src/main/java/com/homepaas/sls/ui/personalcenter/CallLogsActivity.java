package com.homepaas.sls.ui.personalcenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerCallLogComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.CallLogModel;
import com.homepaas.sls.mvp.presenter.personalcenter.CallLogPresenter;
import com.homepaas.sls.mvp.view.personalcenter.CallLogView;
import com.homepaas.sls.ui.personalcenter.adapter.CallLogsAdapter;
import com.homepaas.sls.ui.widget.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 通话记录【客户端数据库有保存该数据，客户端有提交通话记录给后台，但是后台暂时没有提供借口查询通话记录】
 */
public class CallLogsActivity extends BaseListActivity<CallLogModel> implements CallLogView {

    private static final int REQUEST_PERMISSION = 0x12;


    @Inject
    CallLogPresenter mPresenter;

    CallLogsAdapter.OnItemOperateListener mOnItemOperateListener = new CallLogsAdapter.OnItemOperateListener() {
        @Override
        public void onItemCall(int position, CallLogModel item) {
            mPresenter.attemptCall(position);
        }

        @Override
        public void onDelete(int position, CallLogModel item) {
            mPresenter.deleteCallLog(item, position);
        }

        @Override
        public void onItemClick(int position, CallLogModel item) {
            if (item.getType() == Constant.SERVICE_TYPE_BUSINESS_INT) {
//                mNavigator.showBusinessDetail(CallLogsActivity.this, item.getId());
                mNavigator.showMerchantWorkerDetail(CallLogsActivity.this,Constant.SERVICE_TYPE_BUSINESS, item.getId());
            } else {
//                mNavigator.showWorkerDetail(CallLogsActivity.this, item.getId());
                mNavigator.showMerchantWorkerDetail(CallLogsActivity.this,Constant.SERVICE_TYPE_WORKER, item.getId());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("通话记录");
        setEmptyView(R.mipmap.call_log_no_item_bg,R.string.no_call_logs,R.string.no_call_logs_explanation);
        mPresenter.setCallLogView(this);
        mPresenter.getCallLogs();

    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerCallLogComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }


    @Override
    public RecyclerView.Adapter initAdapter(List<CallLogModel> logModels) {
        CallLogsAdapter  adapter = new CallLogsAdapter(logModels);
        adapter.setOnItemOperateListener(mOnItemOperateListener);
        return adapter;
    }

    @Override
    public void delete(int position) {
        getRecyclerView().getAdapter().notifyItemRemoved(position);
        if (position == 0) {
            showEmpty();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                dial(mPresenter.getAttemptCall().getPhoneNumber());
                break;
        }
    }

    public void dial(String phone) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            mPresenter.startDial();
            /*Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);*/
            mNavigator.call(this,phone);
        } else {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
        }
    }

    @Override
    public void disableCall(int position) {
        getRecyclerView().getAdapter().notifyItemChanged(position);
    }

    @Override
    public void onRefresh() {
        mPresenter.getCallLogs();
    }

    @Override
    protected void retrieveData() {
        super.retrieveData();
    }
}

package com.homepaas.sls.ui.order.orderplace;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerServiceTypeComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.ServiceTypeModule;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.CommonServiceType;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.mvp.presenter.order.ServiceTypePresenter;
import com.homepaas.sls.mvp.view.order.ServiceTypeListView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.FlatServicetTypeAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * 一级结构服务选择菜单页面
 */
public class FlatServiceTypeActivity extends CommonBaseActivity implements ServiceTypeListView, FlatServicetTypeAdapter.OnItemClickListener {
    private static final String TAG = "FlatServiceTypeActivity";
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.worker_servic_name)
    TextView workerServiceName;
    private List<Boolean> checkList;
    private List<CommonServiceType> datas;
    private FlatServicetTypeAdapter adapter;
    public static final String KEY_TYPE_ENTITY = "SERVICE_TYPE";

    @Inject
    ServiceTypePresenter serviceTypePresenter;
    private WorkerServiceTypeInfo workerServiceTypeInfo;
    private BusinessServiceTypeInfo businessServiceTypeInfo;
    private String serviceListType;
    private String id;//工人或者是商户的id号
    private String workerServiceNameStr;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_type_flat_select;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        initialize();
        serviceTypePresenter.setServiceTypeListView(this);
        serviceListType = getIntent().getStringExtra(Navigator.SERVICELISTTYPE);
        id = getIntent().getStringExtra(Navigator.ID);
        workerServiceNameStr=getIntent().getStringExtra(Navigator.SERVICE_NAME);
        workerServiceName.setText(matcherSearchTextSecond("选择需要"+workerServiceNameStr+"为您提供服务"));
        getServiceTypeList();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerServiceTypeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .serviceTypeModule(new ServiceTypeModule())
                .build().inject(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initialize() {
        checkList = new ArrayList<>();
        datas = new ArrayList<>();
        adapter = new FlatServicetTypeAdapter(datas, checkList, this);
        adapter.setItemClickListener(this);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取对应位置上的服务类型id
     * @param index
     * @return
     */
    private String getServiceTypeId(int index) {
        return workerServiceTypeInfo == null ?
                businessServiceTypeInfo.getList().get(index).getServiceTypeId()
                : workerServiceTypeInfo.getList().get(index).getServiceTypeId();
    }

    @Override
    public void renderWorker(WorkerServiceTypeInfo serviceTypeListInfo) {
        workerServiceTypeInfo = serviceTypeListInfo;
        adapter.setData(serviceTypeListInfo.getWorkerCommonList());
    }

    @Override
    public void renderBusiness(BusinessServiceTypeInfo serviceTypeListInfo) {
        businessServiceTypeInfo = serviceTypeListInfo;
        adapter.setData(serviceTypeListInfo.getbusinessCommonList());
    }


    /**
     * 获取服务类型列表
     */
    public void getServiceTypeList() {
        if ("2".equals(serviceListType))
        {
            serviceTypePresenter.getWorkerServiceTypeList(id);
        }else{
            serviceTypePresenter.getBusinessServiceList(id);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra(KEY_TYPE_ENTITY,  datas.get(position));
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }

    public SpannableString matcherSearchTextSecond(String text) {
        SpannableString sum = new SpannableString(text);
        int lenth=text.length();
        sum.setSpan(new TextAppearanceSpan(this, R.style.item_month_sum_text_style1), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sum.setSpan(new TextAppearanceSpan(this, R.style.item_month_sum_text_style2), 4,lenth-6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sum.setSpan(new TextAppearanceSpan(this, R.style.item_month_sum_text_style1), lenth-6, lenth, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sum;
    }
}

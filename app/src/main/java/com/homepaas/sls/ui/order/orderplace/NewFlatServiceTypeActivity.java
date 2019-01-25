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
import com.homepaas.sls.domain.entity.BusinessChooseEntity;
import com.homepaas.sls.domain.entity.BusinessServicePricesEntity;
import com.homepaas.sls.domain.entity.BusinessServiceType;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.ServicePriceRangeResponse;
import com.homepaas.sls.mvp.presenter.order.BusinessServicePricePresenter;
import com.homepaas.sls.mvp.view.order.BusinessServicePriceView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.NewFlatServicetTypeAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * 一级结构服务选择菜单页面
 */
public class NewFlatServiceTypeActivity extends CommonBaseActivity implements  NewFlatServicetTypeAdapter.OnItemClickListener,BusinessServicePriceView {
    private static final String TAG = "FlatServiceTypeActivity";
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;

    @Bind(R.id.business_name)
    TextView bussinessName;
    private List<Boolean> checkList;
    private List<BusinessServiceType>  datas;
    private NewFlatServicetTypeAdapter adapter;
    public static final String KEY_TYPE_ENTITY = "SERVICE_TYPE";
    public static final String KEY_SELECT_POSITION="key_select_position";

    private BusinessServiceTypeInfo businessServiceTypeInfo;
    private String serviceListType;
    private String id;//工人或者是商户的id号
    private String addressId;
    private List<BusinessChooseEntity> businessServicelist=new ArrayList<BusinessChooseEntity>();
    private String serviceName;
    private int selectPosition;


    @Inject
    BusinessServicePricePresenter businessServicePricePresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_service_type_flat_select;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        businessServicePricePresenter.setBusinessServicePriceView(this);
        selectPosition=getIntent().getIntExtra(Navigator.SELECT_POSITION,1);
        serviceName=getIntent().getStringExtra(Navigator.SERVICE_NAME);
        id = getIntent().getStringExtra(Navigator.ID);
        addressId=getIntent().getStringExtra(Navigator.ADDRESS_ID);
        businessServiceTypeInfo=(BusinessServiceTypeInfo)getIntent().getSerializableExtra(Navigator.BUSSINESS_LIST);
        initialize();
        businessServicePricePresenter.getServicePriceList(id,"3",addressId);
        datas=businessServiceTypeInfo.getList();
        bussinessName.setText(matcherSearchTextSecond("选择需要"+serviceName+"为您提供服务"));
    }

    @Override
    protected void initData() {

    }


    public SpannableString matcherSearchTextSecond(String text) {
        SpannableString sum = new SpannableString(text);
        int lenth=text.length();
        sum.setSpan(new TextAppearanceSpan(this, R.style.item_month_sum_text_style1), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sum.setSpan(new TextAppearanceSpan(this, R.style.item_month_sum_text_style2), 4,lenth-6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sum.setSpan(new TextAppearanceSpan(this, R.style.item_month_sum_text_style1), lenth-6, lenth, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sum;
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
        adapter = new NewFlatServicetTypeAdapter(businessServicelist, checkList, this);
        adapter.setItemClickListener(this);
        adapter.setSelectPosition(selectPosition);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra(KEY_TYPE_ENTITY,  datas.get(position));
        intent.putExtra(KEY_SELECT_POSITION,position);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }


    @Override
    public void onBusinessPriceResult(BusinessServicePricesEntity businessServicePricesEntity) {
        businessServicelist.clear();
        List<ServicePriceRangeResponse> servicePrices=businessServicePricesEntity.getServicePrices();
        for(BusinessServiceType businessServiceType:datas){
            String serviceId=businessServiceType.getServiceTypeId();
           for(ServicePriceRangeResponse servicePriceRangeResponse:servicePrices){
               if(servicePriceRangeResponse.getServiceTypeId().equals(serviceId)){
                   BusinessChooseEntity businessChooseEntity=new BusinessChooseEntity();
                   businessChooseEntity.setServiveName(businessServiceType.getServiceTypeName());
                   businessChooseEntity.setMax(servicePriceRangeResponse.getMax());
                   businessChooseEntity.setMin(servicePriceRangeResponse.getMin());
                   businessChooseEntity.setUnitName(servicePriceRangeResponse.getUnitName());
                   businessChooseEntity.setNegotiable(servicePriceRangeResponse.getNegotiable());
                   businessChooseEntity.setPriceOptions(servicePriceRangeResponse.getPriceOptions());
                   businessChooseEntity.setStartingPrice(servicePriceRangeResponse.getStartingPrice());
                   businessServicelist.add(businessChooseEntity);
               }
           }
        }
       adapter.setData(businessServicelist);
    }
}


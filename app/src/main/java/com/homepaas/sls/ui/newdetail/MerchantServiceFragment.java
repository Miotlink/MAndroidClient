package com.homepaas.sls.ui.newdetail;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerBusWorkerServiceComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;
import com.homepaas.sls.domain.entity.MerchantServiceTypePrice;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.presenter.newdetail.MerchantServicePriceListPresenter;
import com.homepaas.sls.mvp.view.newdetail.MerchantServicePriceListView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.newdetail.adapter.MerchantServiceItemAdapter;
import com.homepaas.sls.ui.newdetail.adapter.MerchantServiceMunuAdapter;
import com.homepaas.sls.ui.order.directOrder.BusinessOrderActivity;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/2/6.
 */

public class MerchantServiceFragment extends CommonBaseFragment implements MerchantServicePriceListView, MerchantServiceMunuAdapter.OnMenuItemOnClickListener, MerchantServiceItemAdapter.OnThreeBuyClickListener, MerchantServiceItemAdapter.OnFourBuyClickListener, MerchantServiceItemAdapter.OnGoDetailClickListener {

    @Bind(R.id.buy_in_store)
    TextView buyInStore;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.to_pay_btn)
    TextView toPayBtn;
    @Bind(R.id.menu_recyclerView)
    RecyclerView menuRecyclerView;
    @Bind(R.id.service_number)
    TextView serviceNumber;
    @Bind(R.id.item_recyclerView)
    RecyclerView itemRecyclerView;
    @Bind(R.id.pay_in_store_rel)
    RelativeLayout payInStoreRel;
    @Bind(R.id.has_date_lin)
    LinearLayout hasDateLin;
    @Bind(R.id.container)
    CoordinatorLayout container;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    MerchantServiceMunuAdapter merchantServiceMunuAdapter;
    MerchantServiceItemAdapter merchantServiceItemAdapter;
    private MerchantServicePriceListEntity merchantServicePriceListEntity;
    private int menuPosition = 0;//选择的是哪一个大类
    private String isStorePay;  //是否有到店支付，0：否，1：是
    private String storePayStr; //到店支付文案
    private String merchantName;
    private String merchantAddress;
    private String photoUrl;

    @Inject
    MerchantServicePriceListPresenter merchantServicePriceListPresenter;

    private String id;


    public void setJudgePayInStore(String isStorePay, String storePayStr, String merchantName, String merchantAddress, String photoUrl) {
        this.merchantName = merchantName;
        this.isStorePay = isStorePay;
        this.storePayStr = storePayStr;
        this.merchantAddress = merchantAddress;
        this.photoUrl = photoUrl;
        if (!TextUtils.isEmpty(isStorePay)) {
            if (TextUtils.equals(isStorePay, "1")) {
                payInStoreRel.setVisibility(View.VISIBLE);
                content.setText(storePayStr);
            } else {
                payInStoreRel.setVisibility(View.GONE);
            }
        } else {
            payInStoreRel.setVisibility(View.GONE);
        }
    }

    public static MerchantServiceFragment newInstance(String id) {
        MerchantServiceFragment fragment = new MerchantServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(StaticData.MERCHANT_SERVICE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(StaticData.MERCHANT_SERVICE_ID);
        }
    }

    @OnClick({R.id.to_pay_btn})
    void onClick() {
        PayInStoreActivity.start(getActivity(), id, merchantName, merchantAddress, photoUrl);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant_service;
    }

    @Override
    protected void initView() {
        merchantServiceMunuAdapter = new MerchantServiceMunuAdapter();
        merchantServiceMunuAdapter.setOnMenuItemOnClickListener(this);
        menuRecyclerView.addItemDecoration(new SimpleItemDecoration(getActivity(), SimpleItemDecoration.VERTICAL_LIST));
        menuRecyclerView.setAdapter(merchantServiceMunuAdapter);
        merchantServiceItemAdapter = new MerchantServiceItemAdapter(getActivity());
        merchantServiceItemAdapter.setOnFourBuyClickListener(this);
        merchantServiceItemAdapter.setOnGoDetailClickListener(this);
        merchantServiceItemAdapter.setOnThreeBuyClickListener(this);
        itemRecyclerView.addItemDecoration(new SimpleItemDecoration(getActivity(), SimpleItemDecoration.VERTICAL_LIST));
        itemRecyclerView.setAdapter(merchantServiceItemAdapter);
        merchantServicePriceListPresenter.setMerchantServicePriceListView(this);
        merchantServicePriceListPresenter.getMerchantServicePriceLis(id);
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void render(MerchantServicePriceListEntity merchantServicePriceListEntity) {
        this.merchantServicePriceListEntity = merchantServicePriceListEntity;
        if (merchantServicePriceListEntity != null && merchantServicePriceListEntity.getTotalList() != null && !merchantServicePriceListEntity.getTotalList().isEmpty()) {
            hasDateLin.setVisibility(View.VISIBLE);
            merchantServiceMunuAdapter.setList(merchantServicePriceListEntity.getTotalList());
            merchantServiceMunuAdapter.setSelectPosition(0);
            merchantServiceItemAdapter.setMerchantServiceTypePrice(merchantServicePriceListEntity.getTotalList().get(0));
            if (merchantServicePriceListEntity.getTotalList().get(0) != null && merchantServicePriceListEntity.getTotalList().get(0).getChildList() != null && !merchantServicePriceListEntity.getTotalList().get(0).getChildList().isEmpty()) {
                serviceNumber.setText(merchantServicePriceListEntity.getTotalList().get(0).getChildList().size() + "个分类");
            } else {
                serviceNumber.setText("1个分类");
            }
        } else {
            hasDateLin.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerBusWorkerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(getActivity()))
                .build().inject(this);

    }

    @Override
    public void onFourBuyClick(MerchantServiceTypePrice merchantServiceTypePrice) {
        if (merchantServiceTypePrice != null) {
            BusinessOrderActivity.start(getActivity(), Constant.SERVICE_TYPE_BUSINESS_INT, id, merchantServiceTypePrice);
        }

    }

    @Override
    public void onGoDetailClick(MerchantServiceTypePrice merchantServiceTypePrice) {
        if (merchantServicePriceListEntity != null) {
            String serviceType = merchantServicePriceListEntity.getTotalList().get(menuPosition).getName();
            ServiceDetailPayActivity.start(getActivity(), serviceType, merchantServiceTypePrice, id);
        }
    }

    @Override
    public void onThreeBuyClick(MerchantServiceTypePrice merchantServiceTypePrice) {
        BusinessOrderActivity.start(getActivity(), Constant.SERVICE_TYPE_BUSINESS_INT, id, merchantServiceTypePrice);
    }

    @Override
    public void menuItemOnClickListener(int menuPosition) {
        this.menuPosition = menuPosition;
        merchantServiceMunuAdapter.setSelectPosition(menuPosition);
        if (merchantServicePriceListEntity != null) {
            if (merchantServicePriceListEntity.getTotalList() != null && !merchantServicePriceListEntity.getTotalList().isEmpty()) {
                MerchantServiceTypePrice threeMerchantServiceTypePrice = merchantServicePriceListEntity.getTotalList().get(menuPosition);
                merchantServiceItemAdapter.setMerchantServiceTypePrice(threeMerchantServiceTypePrice);
                if (threeMerchantServiceTypePrice != null) {
                    if (threeMerchantServiceTypePrice.getChildList() != null && !threeMerchantServiceTypePrice.getChildList().isEmpty()) {
                        serviceNumber.setText(threeMerchantServiceTypePrice.getChildList().size() + "个分类");
                    } else {
                        serviceNumber.setText("1个分类");
                    }
                }
            }
        }
    }
}

package com.homepaas.sls.ui.redpacket.newRedpacket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerCouponContentsComponent;
import com.homepaas.sls.di.module.CouponContentsModule;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.presenter.coupon.CouponContentPresenter;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;
import com.homepaas.sls.ui.common.fragment.BaseListFragment;
import com.homepaas.sls.ui.homepage_3_3_0.DetailWebActivity;
import com.homepaas.sls.ui.order.servicemerchant.ServiceMerchantActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.newRedPacketAdapter.NewRedPacketItemAdapter;
import com.homepaas.sls.ui.search.AllCategoriesActivity;
import com.homepaas.sls.ui.widget.BlankDecoration;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Sherily on 2016/11/29.
 * 未使用 红包
 */

public class NewNoUsedRedPacketFragment extends BaseListFragment<CouponContents> implements  CouponContentsView, NewRedPacketItemAdapter.ShowServiceDetailListener {

    @Inject
    CouponContentPresenter presenter;
    private NewRedPacketItemAdapter newRedPacketItemAdapter;

    public static NewNoUsedRedPacketFragment newInstance(){
        Bundle args = new Bundle();
        NewNoUsedRedPacketFragment fragment = new NewNoUsedRedPacketFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setCouponContentsView(this);
//        presenter.getCouponList(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getCouponList(0,null,null,null,null,false);
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerCouponContentsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponContentsModule(new CouponContentsModule())
                .build()
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setMoreLoadable(false);
        setEmptyView(R.mipmap.kong, "您当前没有红包哦~");
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new BlankDecoration(getContext());
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<CouponContents> list) {
        newRedPacketItemAdapter = new NewRedPacketItemAdapter(list,0);
        newRedPacketItemAdapter.setShowServiceDetailListener(this);
        return newRedPacketItemAdapter;
    }

    @Override
    public void onRefresh() {
        isShow=true;
        presenter.getCouponList(0,null,null,null,null,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void networkErrorRefresh() {
        super.networkErrorRefresh();
        onRefresh();
    }

    @Override
    public void renderCount(int count) {

    }

    @Override
    public void showDetail(ServiceItem serviceItem) {
        if (serviceItem == null) {
            //跳转到全部类目页面
            AllCategoriesActivity.start(getActivity(), null, null);
        } else if (Constant.SERVICE_GROUP.equals(serviceItem.getStructureType())) {
            //跳转类目页面
            mNavigator.showCategory(getContext(), serviceItem.getServiceId(), null, null);
        } else if (Constant.SERVICE_PRODUCT.equals(serviceItem.getStructureType())) {
            //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
            if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
                //跳转详情页面
                DetailWebActivity.start(mContext, serviceItem);
            } else {
                //跳转到非标订单详情页面
                ServiceMerchantActivity.start(mContext, serviceItem);
            }
//            跳转详情页面
//            DetailWebActivity.start(getContext(), serviceItem);
        }
    }


    private static final int REQUEST_LOGIN = 1;
    private boolean isShow = true;
    @Override
    public void showError(Throwable e) {
        if (e instanceof AuthException) {
            render(null);
            if (isShow){
                mNavigator.login(NewNoUsedRedPacketFragment.this,REQUEST_LOGIN);
                isShow = false;
            }
        }else {
            super.showError(e);
        }
    }
}

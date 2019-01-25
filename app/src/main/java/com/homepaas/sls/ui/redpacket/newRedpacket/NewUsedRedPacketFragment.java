package com.homepaas.sls.ui.redpacket.newRedpacket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerCouponContentsComponent;
import com.homepaas.sls.di.module.CouponContentsModule;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.mvp.presenter.coupon.CouponContentPresenter;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;
import com.homepaas.sls.ui.redpacket.newRedpacket.newRedPacketAdapter.NewRedPacketItemAdapter;
import com.homepaas.sls.ui.common.fragment.BaseListFragment;
import com.homepaas.sls.ui.widget.BlankDecoration;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Sherily on 2016/11/29.
 * 已使用红包
 */

public class NewUsedRedPacketFragment extends BaseListFragment<CouponContents> implements CouponContentsView {


    @Inject
    CouponContentPresenter presenter;
    private NewRedPacketItemAdapter newRedPacketItemAdapter;

    public static NewUsedRedPacketFragment newInstance(){
        Bundle args = new Bundle();
        NewUsedRedPacketFragment fragment = new NewUsedRedPacketFragment();
        return fragment;
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
        setEmptyView(R.mipmap.kong, "没有相关红包");
    }
    @Override
    public void networkErrorRefresh() {
        super.networkErrorRefresh();
        onRefresh();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setCouponContentsView(this);
//        presenter.getCouponList(1);
    }
    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new BlankDecoration(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getCouponList(1,null,null,null,null,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<CouponContents> list) {
        newRedPacketItemAdapter = new NewRedPacketItemAdapter(list,1);

        return newRedPacketItemAdapter;
    }

    @Override
    public void onRefresh() {
        isShow=true;
        presenter.getCouponList(1,null,null,null,null,false);
    }

    @Override
    public void renderCount(int count) {

    }


    private static final int REQUEST_LOGIN = 1;
    private boolean isShow = true;
    @Override
    public void showError(Throwable e) {
        if (e instanceof AuthException) {
            render(null);
            if (isShow){
                mNavigator.login(NewUsedRedPacketFragment.this,REQUEST_LOGIN);
                isShow = false;
            }
        }else {
            super.showError(e);
        }
    }
}

package com.homepaas.sls.ui.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.AccountComponent;
import com.homepaas.sls.mvp.model.PayItem;
import com.homepaas.sls.mvp.presenter.account.PaymentPresenter;
import com.homepaas.sls.mvp.view.account.PaymentView;
import com.homepaas.sls.ui.account.adapter.PaymentAdapter;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayDetailFragment extends PageFragment implements PaymentView {


    @Bind(R.id.pay_messages)
    RecyclerView mPayMessages;

    @Bind(R.id.empty_message)
    TextView mEmptyMessage;

    @Inject
    PaymentPresenter mPaymentPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay_detail;
    }

    @Override
    protected void initView() {
        mPayMessages.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));

        mPaymentPresenter.setView(this);
        mPaymentPresenter.obtainPayDetails();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        getComponent(AccountComponent.class)
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPaymentPresenter.destroy();
    }

    @Override
    public void render(List<PayItem> payItems) {
        mOnRefreshEndLister.onRefreshEnd();
        if (payItems==null||payItems.isEmpty()){
            mEmptyMessage.setVisibility(View.VISIBLE);
            mPayMessages.setVisibility(View.GONE);
        }else {
            mEmptyMessage.setVisibility(View.GONE);
            mPayMessages.setVisibility(View.VISIBLE);
            PaymentAdapter adapter = (PaymentAdapter) mPayMessages.getAdapter();
            if (adapter == null) {
                adapter = new PaymentAdapter(payItems);
                mPayMessages.setAdapter(adapter);
            } else {
                adapter.setPayItems(payItems);
            }
        }
    }

    @Override
    public void refresh() {
        mPaymentPresenter.obtainPayDetails();
    }
}

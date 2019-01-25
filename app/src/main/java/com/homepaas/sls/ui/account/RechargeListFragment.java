package com.homepaas.sls.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.di.component.RechargeComponent;
import com.homepaas.sls.domain.entity.Recharge;
import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.mvp.presenter.account.RechargeListPresenter;
import com.homepaas.sls.mvp.view.account.RechargeListView;
import com.homepaas.sls.ui.account.adapter.RechargeAdapter;
import com.homepaas.sls.ui.common.fragment.BaseListFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * 充值列表
 */
public class RechargeListFragment extends BaseListFragment<Recharge> implements RechargeListView {

    private static final String TAG = "RechargeListFragment";

    private OnFragmentItemClickListener mListener;

    @Inject
    RechargeListPresenter mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setMoreLoadable(false);
       // setEmptyView();
        mPresenter.setView(this);
        mPresenter.obtainRecharges();
    }

    @Override
    protected void initializeInjector() {
        getComponent(RechargeComponent.class)
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentItemClickListener) {
            mListener = (OnFragmentItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<Recharge> list) {
        RechargeAdapter  adapter = new RechargeAdapter(list);
        adapter.setOnItemClickListener(new RechargeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position,Recharge recharge) {
                mListener.onItemClick(recharge);
            }
        });
        return adapter;
    }

    @Override
    public void onRefresh() {
        mPresenter.obtainRecharges();
    }

    @Override
    public void render(RechargeListExEntity recharges) {

    }

    public interface OnFragmentItemClickListener {

        void onItemClick(Recharge recharge);
    }
}

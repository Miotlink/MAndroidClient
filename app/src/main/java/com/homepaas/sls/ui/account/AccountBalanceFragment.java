package com.homepaas.sls.ui.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.AccountComponent;
import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.mvp.presenter.account.AccountPresenter;
import com.homepaas.sls.mvp.view.account.AccountBalanceView;
import com.homepaas.sls.ui.account.adapter.BalanceAdapter;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountBalanceFragment extends PageFragment implements AccountBalanceView {


    @Bind(R.id.charge_messages)
    RecyclerView mChargeMessages;

    @Bind(R.id.empty_message)
    TextView mEmptyMessage;

    @Bind(R.id.account_balance)
    TextView mBalance;

    @Inject
    AccountPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_balance;
    }

    @Override
    protected void initView() {
        mChargeMessages.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        mPresenter.setAccountBalanceView(this);

        mPresenter.get();

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
        mPresenter.destroy();
    }

    @Override
    public void renderDetail(List<AccountDetail> details) {
        mOnRefreshEndLister.onRefreshEnd();
        if (details.isEmpty()) {
            mEmptyMessage.setVisibility(View.VISIBLE);
            mChargeMessages.setVisibility(View.GONE);
        } else {
            mEmptyMessage.setVisibility(View.GONE);
            mChargeMessages.setVisibility(View.VISIBLE);
            BalanceAdapter adapter = (BalanceAdapter) mChargeMessages.getAdapter();
            if (adapter == null) {
                adapter = new BalanceAdapter(details);
                mChargeMessages.setAdapter(adapter);
            } else {
                adapter.setDetails(details);
            }
        }

    }

    @Override
    public void renderBalance(AccountInfo info) {
        mOnRefreshEndLister.onRefreshEnd();
        String balance = info.getEnabledBalance();
        String balanceTemp = balance + "元"; //String.format(Locale.CHINA, "%.2f元", Double.parseDouble(balance));
        SpannableString ss = new SpannableString(balanceTemp);
        ss.setSpan(new AbsoluteSizeSpan(sp2px(22)), 0, balanceTemp.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.decorateYellow)), 0, balanceTemp.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBalance.setText(ss);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    public void refresh() {
        mPresenter.get();
    }
}

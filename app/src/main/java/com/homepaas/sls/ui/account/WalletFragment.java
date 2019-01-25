package com.homepaas.sls.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerAccountComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.mvp.presenter.account.WalletBalancePresenter;
import com.homepaas.sls.mvp.view.account.WalletBalanceView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.widget.AnimationUtil;
import com.homepaas.sls.ui.widget.AutoText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2016/12/1.
 */

public class WalletFragment extends CommonBaseFragment implements WalletBalanceView {
    @Bind(R.id.push_rl)
    RelativeLayout pushRl;
    @Bind(R.id.push_text)
    AutoText pushText;
    @Bind(R.id.push_close)
    FrameLayout pushClose;
    @Bind(R.id.account_balance)
    TextView accountBalance;
    @Bind(R.id.recharge_button)
    Button rechargeButton;
    @Bind(R.id.item_my_account)
    FrameLayout itemMyAccount;
    @Bind(R.id.account_ll)
    LinearLayout accountLl;

    @Inject
    WalletBalancePresenter mPresenter;

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initView() {
        mPresenter.setWalletBalanceView(this);
        mPresenter.getAccountInfo();
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.push_close, R.id.recharge_button, R.id.item_my_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.push_close:
                pushRl.setVisibility(View.GONE);
                accountLl.setVisibility(View.VISIBLE);
                pushRl.setAnimation(AnimationUtil.moveToViewLocation(0.0f, 0.0f, 0.0f, -25.0f));
                accountLl.setAnimation(AnimationUtil.moveToViewLocation(0.0f, 0.0f, 25.0f, 0.0f));
                break;
            case R.id.recharge_button:
                RechargeChooseActivity.start(getActivity());
                break;
            case R.id.item_my_account:
                AccountListActivity.start(getActivity());
                break;
            default:
        }
    }


    @Override
    protected void initializeInjector() {
        DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(getActivity()))
                .build()
                .inject(this);

    }


    @Override
    public void renderAccountInfo(NewAccountInfo info) {
        accountBalance.setText(info.getSettlementBalance());
        if (info.getActivity() != null) {
            String text = info.getActivity().getContent();
            if (!TextUtils.isEmpty(text)) {
                pushRl.setVisibility(View.VISIBLE);
                pushText.setText(text);
                if(text.length()>20){
                    pushText.startFor0();
                }
            } else {
                pushRl.setVisibility(View.GONE);
            }
        }

    }
}

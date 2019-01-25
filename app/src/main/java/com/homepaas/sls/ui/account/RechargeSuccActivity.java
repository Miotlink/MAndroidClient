package com.homepaas.sls.ui.account;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerRechargeComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.RechargeInfoEntry;
import com.homepaas.sls.domain.entity.RechargeInfoResponse;
import com.homepaas.sls.mvp.presenter.account.RechargeInfoPresent;
import com.homepaas.sls.mvp.view.account.RechargeInfoView;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RechargeSuccActivity extends CommonBaseActivity implements RechargeInfoView {

    @Bind(R.id.back_ll)
    LinearLayout backLl;
    @Bind(R.id.pay_sum)
    TextView paySum;
    @Bind(R.id.total_money)
    TextView totalMoney;
    @Bind(R.id.recharge_gift)
    TextView rechargeGift;
    @Bind(R.id.recharge_time)
    TextView rechargeTime;
    @Bind(R.id.recharge_tradeno)
    TextView rechargeTradeNo;
    @Bind(R.id.check_detail)
    Button checkDetail;
    @Bind(R.id.main_page)
    Button mianPage;
    @Bind(R.id.gift_ll)
    LinearLayout giftLl;


    private String rechargeCode;
    private RechargeInfoEntry.RechargeInfoResponse rechargeInfo;


    @Inject
    RechargeInfoPresent rechargeInfoPresent;
    private static final String RECHARGECODE = "rechargeCode";

    public static void start(Context context, String rechargeCode) {
        Intent intent = new Intent(context, RechargeSuccActivity.class);
        intent.putExtra(RECHARGECODE, rechargeCode);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_success_info;
    }

    @Override
    protected void initView() {
        rechargeCode = getIntent().getStringExtra(RECHARGECODE);
        rechargeInfoPresent.setRechargeInfoView(this);
        rechargeInfoPresent.getRechargeInfo(rechargeCode);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.back_ll, R.id.check_detail, R.id.main_page})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.check_detail:
                RechargeChooseActivity.start(this);
                break;
            case R.id.main_page:
                startActivity(new Intent(RechargeSuccActivity.this, MainActivity.class));
                break;
            default:
        }
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerRechargeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderRechargeInfo(RechargeInfoResponse rechargeInfoResponse) {
        paySum.setText("¥"+rechargeInfoResponse.getRealPaidMoney());
        totalMoney.setText(rechargeInfoResponse.getTotalMoney()+"元");
        if(!TextUtils.isEmpty(rechargeInfoResponse.getActivity())){
            giftLl.setVisibility(View.VISIBLE);
            rechargeGift.setText(rechargeInfoResponse.getActivity());
        }else {
            giftLl.setVisibility(View.GONE);
        }
        rechargeTime.setText(TimeUtils.formatDateByLine(rechargeInfoResponse.getRechargedTime()));
        rechargeTradeNo.setText(rechargeInfoResponse.getTradeNo());
    }
}

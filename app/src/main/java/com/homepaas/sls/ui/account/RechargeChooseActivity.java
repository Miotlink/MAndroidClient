package com.homepaas.sls.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerRechargeComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.mvp.presenter.account.RechargeListPresenter;
import com.homepaas.sls.mvp.view.account.RechargeListView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.account.adapter.NewRechargeAdapter;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.ExpandedGridView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2016/11/30.
 */

public class RechargeChooseActivity extends CommonBaseActivity implements RechargeListView, AdapterView.OnItemClickListener {

    @Bind(R.id.back_ll)
    LinearLayout backLl;
    @Bind(R.id.gridview)
    ExpandedGridView gridView;
    @Bind(R.id.paymoney)
    TextView paymoney;
    @Bind(R.id.extra_gift)
    TextView extraGift;
    @Bind(R.id.money_symbol)
    TextView moneySymbol;
    @Bind(R.id.extra_gift_money)
    TextView extraGiftMoney;
    @Bind(R.id.total_get_money)
    TextView totalGetMoney;
    @Bind(R.id.recharge_button)
    Button rechargeButton;
    @Bind(R.id.pay_service_tv)
    TextView payServiceTv;

    @Inject
    Navigator navigator;

    private List<RechargeListExEntity.RechargeItem> datas;
    private RechargeListExEntity.RechargeItem recharge;
    private NewRechargeAdapter mAapter;
    private int position = -1;
    private boolean isEquals = false;//填写的金额是否和充值列表中有相同的


    public static void start(Context context) {
        Intent intent = new Intent(context, RechargeChooseActivity.class);
        context.startActivity(intent);
    }

    @Inject
    RechargeListPresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_choose;
    }

    @Override
    protected void initView() {
        mPresenter.setView(this);
        mPresenter.obtainRecharges();

        datas = new ArrayList<>();
        mAapter = new NewRechargeAdapter(this);
        gridView.setAdapter(mAapter);
        rechargeButton.setEnabled(false);
    }

    @Override
    protected void initData() {

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        recharge = datas.get(position);
        rechargeButton.setEnabled(true);
        moneySymbol.setVisibility(View.VISIBLE);
        totalGetMoney.setVisibility(View.VISIBLE);
//        rechargeButton.setText("立即支付" + datas.get(position).getNeedPay() + "元");
        paymoney.setText(recharge.getNeedPay());
        totalGetMoney.setText("¥" + recharge.getTotalMoney());
        if (!TextUtils.isEmpty(recharge.getReturnType())) {
            if (recharge.getReturnType().equals("3")) {
                extraGift.setText("折扣:");
                extraGiftMoney.setText(((int) Double.parseDouble(recharge.getReturnMoney())) / 10 + "");
            } else if (recharge.getReturnType().equals("4")) {
                extraGift.setText("额外赠送:");
                extraGiftMoney.setText(recharge.getReturnMoney());
            } else if (recharge.getReturnType().equals("5")) {
                extraGift.setText("额外赠送:");
                extraGiftMoney.setText("0元");
            }
        } else {
            extraGift.setText("额外赠送:");
            extraGiftMoney.setText("0元");
        }
        int count = parent.getCount();
        TextView rechargeMoney;
        RelativeLayout itemFrame;
        for (int i = 0; i < count; i++) {
            if (i != position) {
                View itemView = parent.getChildAt(i);
                rechargeMoney = (TextView) itemView.findViewById(R.id.recharge_money);
                itemFrame = (RelativeLayout) itemView.findViewById(R.id.item_frame);
                rechargeMoney.setTextColor(getResources().getColor(R.color.appText));
                itemFrame.setBackgroundResource(R.drawable.recharge_unselected_button_bg);
            }
        }

        rechargeMoney = (TextView) view.findViewById(R.id.recharge_money);
        itemFrame = (RelativeLayout) view.findViewById(R.id.item_frame);
        rechargeMoney.setTextColor(getResources().getColor(R.color.wallet_title_color));
        itemFrame.setBackgroundResource(R.drawable.recharge_selected_button_bg);
        this.position = position;

    }

    @OnClick({R.id.back_ll, R.id.recharge_button,R.id.pay_service_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.recharge_button:
                RechargePaymentActivity.start(this, recharge);
                break;
            case R.id.pay_service_tv:
               navigator.loadWebView(this,"file:///android_asset/pay_service_index.html","充值协议");
                break;
            default:
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void render(RechargeListExEntity recharges) {
        datas.clear();
        datas.addAll(recharges.getList());
        if (recharges.getList().isEmpty() || recharges.getList().size() == 0) {
            gridView.setVisibility(View.GONE);
        } else {
            gridView.setVisibility(View.VISIBLE);
        }
        gridView.setOnItemClickListener(this);
        mAapter.setDatas(recharges.getList());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}

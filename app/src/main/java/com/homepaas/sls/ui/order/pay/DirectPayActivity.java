package com.homepaas.sls.ui.order.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.homepaas.sls.R;
import com.homepaas.sls.common.util.PayResult;
import com.homepaas.sls.di.component.DaggerOrderComponent;
import com.homepaas.sls.domain.entity.BalanceInfo;
import com.homepaas.sls.domain.entity.WxSign;
import com.homepaas.sls.event.PayAbortEvent;
import com.homepaas.sls.mvp.presenter.DescriptionPresenter;
import com.homepaas.sls.mvp.presenter.pay.PayPresenter;
import com.homepaas.sls.mvp.view.GetDescriptionView;
import com.homepaas.sls.mvp.view.pay.GetBalanceView;
import com.homepaas.sls.mvp.view.pay.GetDirectPayIdView;
import com.homepaas.sls.mvp.view.pay.GetDirectPaySignView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.EnhanceDialog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class DirectPayActivity extends CommonBaseActivity implements GetBalanceView, GetDirectPayIdView, GetDirectPaySignView, GetDescriptionView {

    private static final String TAG = "DirectPayActivity";
    @Inject
    PayPresenter payPresenter;
    @Inject
    DescriptionPresenter descriptionPrsenter;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.service_money)
    EditText serviceMoney;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.balance_pay)
    ImageView balancePay;
    @Bind(R.id.balance_layout)
    RelativeLayout balanceLayout;
    @Bind(R.id.zhifubao_pay)
    ImageView zhifubaoPay;
    @Bind(R.id.zhifubao_layout)
    RelativeLayout zhifubaoLayout;
    @Bind(R.id.wx_pay)
    ImageView wxPay;
    @Bind(R.id.weixin_layout)
    RelativeLayout weixinLayout;
    @Bind(R.id.service_money_layout)
    LinearLayout serviceMoneyLayout;
    @Bind(R.id.service_illustrate)
    TextView serviceIllustrate;
    @Bind(R.id.pay_button)
    Button payButton;
    private String receiverId ;//185
    private String receiverType;//2
    private String serviceTypeId;//5

    private boolean balancePayTag;
    private boolean wxPayTag ;
    private boolean zhifubaoTag;
    //余额不足提示Dialog
    private CommonDialog commonDialog = new CommonDialog.Builder()
            .setContent("账户余额不足,请选择其他支付方式，支付不足部分")
            .showTitle(false)
            .setCancelButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismiss();
                }
            }).setConfirmTextColor(R.color.appPrimary)
            .setConfirmButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismiss();
                }
            }).create();
    private Double balanceCount;
    private String AndroidFrom = "1";
    private double balancePayMoney = 0;
    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        showMessage("支付成功");
                        finish();

                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showMessage("支付取消");

                        } else if (TextUtils.equals(resultStatus, "6001")) {

                        } else {

                        }
                    }
                    break;
            }
        }
    };
    private String serviceProviderName;
    private double bPayCount;
    private double tPayCount;
    private EnhanceDialog hybridPayDialog;
    private CommonDialog balanceNotEnoughDialog;

    /**
     * @param receiverId          ..被支付人id（工人id）
     * @param receiverType        ..被支付人类型(工人=2)
     * @param serviceTypeID       ..服务类别，即工人信息的defaultService字段
     * @param serviceProviderName ...服务商名称
     */
    public static void start(Context context, String receiverId, String receiverType, String serviceTypeID, String serviceProviderName) {
        Intent intent = new Intent(context, DirectPayActivity.class);
        intent.putExtra("ReceiverId", receiverId);
        intent.putExtra("ReceiverType", receiverType);
        intent.putExtra("ServiceTypeId", serviceTypeID);
        intent.putExtra("ServiceProviderName", serviceProviderName);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_direct_pay;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        receiverId = intent.getStringExtra("ReceiverId");
        receiverType = intent.getStringExtra("ReceiverType");
        serviceTypeId = intent.getStringExtra("ServiceTypeId");
        serviceProviderName = intent.getStringExtra("ServiceProviderName");
        toolbar.setTitle(serviceProviderName);
        payPresenter.setAccountBalanceView(this);
        payPresenter.setGetDirectPayIdView(this);
        payPresenter.setGetDirectPaySignView(this);
        descriptionPrsenter.setGetDescriptionView(this);
        payPresenter.getAccountBalance();
        descriptionPrsenter.getWarmDescription();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerOrderComponent.builder().applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    @OnClick({R.id.zhifubao_layout, R.id.weixin_layout, R.id.balance_layout})
    public void choosePayType(View view) {
        switch (view.getId()) {
            case R.id.weixin_layout:
                wxPayTag = !wxPayTag;
                if (wxPayTag){
                    zhifubaoTag = false;
                }
                break;
            case R.id.zhifubao_layout:
                zhifubaoTag = !zhifubaoTag;
                if (zhifubaoTag)
                {
                    wxPayTag = false;
                }
                break;
            case R.id.balance_layout:
                balancePayTag = !balancePayTag;
                break;
        }
//        checkBalancePay();
        zhifubaoPay.setImageResource(zhifubaoTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        wxPay.setImageResource(wxPayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        balancePay.setImageResource(balancePayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);

        payButton.setEnabled(balancePayTag || wxPayTag || zhifubaoTag);
    }

    /**
     * 输入支付金额变化
     */
    @OnFocusChange(R.id.service_money)
    public void onServiceMoneyChange() {
        String money = (serviceMoney.getText().toString()+"").trim();
        if (money.startsWith("."))return;
        payButton.setEnabled(!TextUtils.isEmpty(money) && (wxPayTag || zhifubaoTag || balancePayTag));
        if (!TextUtils.isEmpty(money)) {//输入金额，且余额小于金额
            if (balanceCount < Double.valueOf(money))
            {
                zhifubaoPay.setEnabled(true);
                wxPay.setEnabled(true);
            }
        }
    }

    /**
     * 检查选中了余额支付的情况下，是否允许三方支付
     */
    private void checkBalancePay() {
        String money = serviceMoney.getText().toString().trim();
        if (balancePayTag) {
            if (!TextUtils.isEmpty(money)) {
                if (balanceCount > Double.valueOf(money)) {
                    zhifubaoTag = false;
                    wxPayTag = false;
                    zhifubaoPay.setEnabled(false);
                    wxPay.setEnabled(false);
                }
            }
        } else {
            zhifubaoPay.setEnabled(true);
            wxPay.setEnabled(true);
        }
    }

    /**
     * 获取直接支付id
     */
    @OnClick({R.id.pay_button})
    public void attempToPay() {
        if (showNetWorkInfo());else return;
        final String s = serviceMoney.getText().toString().trim();
        if (s == null || TextUtils.isEmpty(s)) {
            showMessage("请输入服务金额");
            return;
        }
        final double money = Double.valueOf(s);
        if (balancePayTag)//采用余额支付
        {
            if (wxPayTag || zhifubaoTag) {
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_hybrid_pay_content, null);
                    TextView balancePayCount = (TextView) contentView.findViewById(R.id.balancePayCount);
                    TextView payType = (TextView) contentView.findViewById(R.id.payType);
                    TextView payCount = (TextView) contentView.findViewById(R.id.payCount);
                    //余额支付金额
                    bPayCount = 0.00;
                    //三方支付金额
                    tPayCount = 0.00;
                    if (balanceCount > money) {
                        bPayCount = money;
                        tPayCount = 0.0;
                        showMessage("实际支付金额小于余额，请选择单一支付方式");
                        return;
                    } else {
                        BigDecimal nBD = new BigDecimal(s);
                        nBD.setScale(2,BigDecimal.ROUND_HALF_UP);
                        BigDecimal bBD = new BigDecimal(String.valueOf(balanceCount));
                        bBD.setScale(2);
                        bPayCount = balanceCount;//余额全部用完
                        tPayCount =  nBD.subtract(bBD).doubleValue();//三方支付金额
                    }
                    balancePayCount.setText("余额" + bPayCount + "元");//余额支付的金额
                    payCount.setText(tPayCount + "元");//第三方支付的金额
                    payType.setText(!zhifubaoTag?"微信支付":"支付宝支付");
                    hybridPayDialog = new EnhanceDialog.Builder().setContentView(contentView).
                            setConfirmButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hybridPayDialog.dismiss();
                                    payPresenter.getDirectPayId(receiverId, receiverType, AndroidFrom, money+"", serviceTypeId);
                                }
                            }).setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hybridPayDialog.dismiss();
                        }
                    }).setTitle("付款详情").create();

                hybridPayDialog.show(getSupportFragmentManager(), null);
            } else {
                //单独的余额支付
                if (money > balanceCount) {//无法使用余额支付
                    if (balanceNotEnoughDialog == null)
                        balanceNotEnoughDialog = new CommonDialog.Builder().setContent("账户余额不足，请选择其他支付方式，支付不足部分")
                                .setCancelButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        balanceNotEnoughDialog.dismiss();
                                    }
                                }).setConfirmTextColor(R.color.appPrimary)
                                .setConfirmButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        balanceNotEnoughDialog.dismiss();
                                    }
                                }).showTitle(false).create();
                    balanceNotEnoughDialog.show(getSupportFragmentManager(),null);
                }
                else{
                    payPresenter.getDirectPayId(receiverId, receiverType, AndroidFrom, money+"", serviceTypeId);
                }
            }
        }else{
            payPresenter.getDirectPayId(receiverId, receiverType, AndroidFrom, money+"", serviceTypeId);
        }
    }

    @Override
    public void render(BalanceInfo balanceInfo) {
        BigDecimal bd = new BigDecimal(balanceInfo.balance);
        bd.setScale(2);
        balanceCount = bd.doubleValue();
        balanceTv.setText("余额" + balanceInfo.balance + "元");
    }

    /**
     * 获取到了直接支付的id
     *
     * @param id
     */
    @Override
    public void onDirectPayIdResult(String id) {
        String totalMon = serviceMoney.getText().toString();
        //根据支付tag判断是哪一种支付，获取相应的支付签名
        if (balancePayTag) {
            if (wxPayTag) {
                payPresenter.getDirectPayWxSign(id, balanceCount + "");
            } else if (zhifubaoTag)
                payPresenter.getDirectPayAliSign(id, balanceCount + "");
            else {
                //余额支付
                //检查余额是否可以完成支付
                if (balanceCount > Double.valueOf(totalMon))
                    payPresenter.directBalancePay(id, totalMon);
                else {
                    showMessage("账户余额不足，请选择其他支付方式支付不足部分");
                }
            }
        } else {
            if (wxPayTag) {
                payPresenter.getDirectPayWxSign(id, "0");
            } else if (zhifubaoTag) {
                payPresenter.getDirectPayAliSign(id, "0");
            }
        }
    }

    @Override
    public void onAliSign(final String aliSign) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(DirectPayActivity.this);
                String result = payTask.pay(aliSign, true);
                Message message = Message.obtain();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onWxSign(WxSign sign) {
        PayReq req = new PayReq();
        req.appId = sign.getAppid();
        req.partnerId = sign.getPartnerid();
        req.prepayId = sign.getPrepayid();
        req.nonceStr = sign.getNoncestr();
        req.timeStamp = sign.getTimestamp();
        req.packageValue = sign.getPackag();
        req.sign = sign.getSign();
        req.extData = "WxPay";
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, sign.getAppid());
            api.registerApp(sign.getAppid());
        }
        api.sendReq(req);
    }

    /**
     * 余额直接支付回调
     *
     * @param string
     */
    @Override
    public void onBalancePayResult(String string) {
        showMessage("支付成功");
        payPresenter.getAccountBalance();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void retrieveData() {
        payPresenter.getAccountBalance();
        final String money = serviceMoney.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            return;
        }
        payPresenter.getDirectPayId(receiverId, receiverType, AndroidFrom, money+"", serviceTypeId);
    }

    @Override
    public void renderDescription(String descripiton) {
        serviceIllustrate.setText(descripiton);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event)
    {
        if (event.code==0)
        {
            finish();
        }else{
            showMessage(event.msg);
        }
    }
}

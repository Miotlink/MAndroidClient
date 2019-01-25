package com.homepaas.sls.ui.order.orderplace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.common.util.PayResult;
import com.homepaas.sls.di.component.DaggerPayComponent;
import com.homepaas.sls.di.module.PayModule;
import com.homepaas.sls.domain.entity.BalanceInfo;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.event.PayAbortEvent;
import com.homepaas.sls.event.SuccessPayEvent;
import com.homepaas.sls.mvp.presenter.coupon.CouponContentPresenter;
import com.homepaas.sls.mvp.presenter.pay.PayPresenter;
import com.homepaas.sls.mvp.view.GetWXSignView;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;
import com.homepaas.sls.mvp.view.order.BalancePayView;
import com.homepaas.sls.mvp.view.order.GetAliSignView;
import com.homepaas.sls.mvp.view.pay.GetBalanceView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.CouponUtils;
import com.homepaas.sls.ui.order.detail.DetailOrderActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.UseNewRedPacketActivity;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/14.
 */
public class OrderPayActivity extends CommonBaseActivity implements GetBalanceView, GetAliSignView, GetWXSignView, BalancePayView, CouponContentsView {

    private static final String TAG = "OrderPayActivity";
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.service_money)
    TextView serviceMoney;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
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
    @Bind(R.id.service_illustrate)
    TextView serviceIllustrate;
    @Bind(R.id.pay_button)
    Button payButton;
    @Bind(R.id.coupon_choice)
    TextView coupon;
    @Bind(R.id.pay_money)
    TextView payMoney;

    private double balanceCount;
    private static Object tag = new Object();
    private boolean balancePayTag = false;
    private boolean wxPayTag = false;
    private boolean zhifubaoTag = false;
    private String orderId;
    private String totalMoney;//总金额
    private String needPayMoney;//实际需要支付的金额
    private String serviceProviderName;
    private String balancePayMoney = "0";

    @Inject
    PayPresenter payPresenter;
    @Inject
    CouponContentPresenter couponPresenter;
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
                            DetailOrderActivity.start(OrderPayActivity.this, orderId);
                            OrderPayActivity.this.finish();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showMessage("支付已取消");

                        } else if (TextUtils.equals(resultStatus, "6001")) {

                        } else {

                        }
                    }
                    break;
            }
        }
    };

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
    //优惠券id
    private String couponId = null;
    private EnhanceDialog hybridPayDialog;
    private Double bPayCount;//余额支付金额
    private Double tPayCount;//三方支付金额

    public static void start(Context context, String orderId,String totalMoney, String name) {
        Intent intent = new Intent(context, OrderPayActivity.class);
        intent.putExtra(Constant.OrderId, orderId);
        intent.putExtra("ServiceProviderName", name);
        intent.putExtra("TotalMoney",totalMoney);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        payPresenter.setAccountBalanceView(this);
        payPresenter.setBalancePayView(this);
        payPresenter.setGetAliSignView(this);
        payPresenter.setGetWXSignView(this);
        couponPresenter.setCouponContentsView(this);
        payPresenter.getAccountBalance();
        couponPresenter.getCouponList();
        orderId = getIntent().getStringExtra(Constant.OrderId);
        totalMoney = getIntent().getStringExtra("TotalMoney");
        needPayMoney = totalMoney;
        serviceProviderName = getIntent().getStringExtra("ServiceProviderName");
        bindData();
    }

    @Override
    protected void initData() {

    }

    private void bindData() {
        serviceMoney.setText(totalMoney);
        toolbar.setTitle(serviceProviderName);
        payMoney.setText("实际支付" + totalMoney + "元");
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerPayComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payModule(new PayModule(this))
                .build().inject(this);
    }

    @OnClick(R.id.coupon_choice)
    public void onCouponChoose(View view) {
//        Intent intent = new Intent(this, CouponActivity.class);
        Intent intent = new Intent(this, UseNewRedPacketActivity.class);
        intent.putExtra("TotalMoney", totalMoney);
        intent.putExtra("IsNotUseCoupon", isNotUseCoupon);
        intent.putExtra("CouponId",couponId);
        startActivityForResult(intent, 1000);
    }
    private boolean isNotUseCoupon = false;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                isNotUseCoupon = data.getBooleanExtra("IsNotUseCoupon", false);
                if (isNotUseCoupon){
                    couponId = null;
                    coupon.setText("不使用红包");
                    needPayMoney = totalMoney;
                    payMoney.setText("实际支付" + totalMoney + "元");
                } else {
                    Double discountMoney = Double.valueOf(data.getStringExtra("DiscountMoney"));
                    Double resultMoney = Double.valueOf(data.getStringExtra("ResultMoney"));
                    needPayMoney = String.valueOf(resultMoney);//改变实际支付金额
                    couponId = data.getStringExtra("CouponId");
                    payMoney.setText("实际支付" + resultMoney + "元");
                    coupon.setText("减" + discountMoney + "元");
                }


                if (balancePayTag && Double.valueOf(totalMoney) > balanceCount) {
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            commonDialog.show(getSupportFragmentManager(), null);
                        }
                    });
                }
            }
        }
    }

    @OnClick({R.id.balance_layout, R.id.weixin_layout, R.id.zhifubao_layout})
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

        balancePay.setImageResource(balancePayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        zhifubaoPay.setImageResource(zhifubaoTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        wxPay.setImageResource(wxPayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        payButton.setEnabled(balancePayTag || wxPayTag || zhifubaoTag);
    }

    /**
     * 检查是否可以采用余额支付
     */
    private void checkBalancePay() {
        if (balancePayTag && (!wxPayTag && !zhifubaoTag)) {
            if (balanceCount < Double.valueOf(needPayMoney)) {
                commonDialog.show(getSupportFragmentManager(), null);

            }
        }
    }


    @OnClick({R.id.pay_button})
    public void attempToPay(View view) {
        if (!NetUtils.isConnected(getApplicationContext())){showMessage(getString(R.string.network_error));return;}
        if (balancePayTag)//采用余额支付
        {
            if (wxPayTag || zhifubaoTag) {
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_hybrid_pay_content, null);
                    TextView balancePayCount = (TextView) contentView.findViewById(R.id.balancePayCount);
                    TextView payCount = (TextView) contentView.findViewById(R.id.payCount);
                    //余额支付金额
                    bPayCount = 0.00;
                    //三方支付金额
                    tPayCount = 0.00;
                    if (balanceCount > Double.valueOf(needPayMoney)) {
                        bPayCount = Double.valueOf(needPayMoney);
                        tPayCount = 0.0;
                        showMessage("实际支付金额小于余额，请选择单一支付方式");
                        return;
                    } else {
                        BigDecimal nBD = new BigDecimal(needPayMoney);
                        nBD.setScale(2,BigDecimal.ROUND_HALF_UP);
                        BigDecimal bBD = new BigDecimal(String.valueOf(balanceCount));
                        bBD.setScale(2);
                        bPayCount = balanceCount;//余额全部用完
                        tPayCount =  nBD.subtract(bBD).doubleValue();//三方支付金额
                    }
                    balancePayCount.setText("余额" + bPayCount + "元");//余额支付的金额
                    payCount.setText(tPayCount + "元");//第三方支付的金额
                    hybridPayDialog = new EnhanceDialog.Builder().setContentView(contentView).
                            setConfirmButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hybridPayDialog.dismiss();
                                    if (wxPayTag)
                                        payPresenter.getWXPaysign(orderId, couponId,tPayCount+"", String.valueOf(bPayCount));
                                    else
                                        payPresenter.getAliPaySign(orderId, couponId, tPayCount+"", String.valueOf(bPayCount));
                                }
                            }).setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hybridPayDialog.dismiss();
                        }
                    }).setTitle("付款详情").create();

                hybridPayDialog.show(getSupportFragmentManager(), null);
            } else {
                //余额支付
                payByBalance();
            }
        } else {
            if (wxPayTag) {
                if (BuildConfig.DEBUG)
                    Log.i(TAG, "attemptToPay: " + totalMoney);
                payPresenter.getWXPaysign(orderId, couponId, needPayMoney, balancePayMoney);
                return;
            }
            if (zhifubaoTag) {
                payPresenter.getAliPaySign(orderId, couponId, needPayMoney, balancePayMoney);
                return;
            }

        }
    }

    private void payByBalance() {
        payPresenter.payByBalance(orderId, couponId, needPayMoney);
    }


    @Override
    public void render(BalanceInfo balanceInfo) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, "render: " + balanceInfo.balance);
        BigDecimal bd = new BigDecimal(balanceInfo.balance);
        bd.setScale(2);
        balanceCount = bd.doubleValue();
        balanceTv.setText("余额" + balanceInfo.balance + "元");
    }


    /**
     * 余额支付回调
     *
     * @param errcode ..
     */
    @Override
    public void onBalancePayResult(String errcode) {
        showMessage(TextUtils.equals("0", errcode) ? "支付成功" : "支付异常");
        if (TextUtils.equals("0", errcode)) {
            EventBus.getDefault().post(new SuccessPayEvent());
            DetailOrderActivity.start(this, orderId);
            this.finish();
        }

    }

    @Override
    public void render(String sign) {
        //发起支付宝支付
        startAliPay(sign);
    }


    private void startAliPay(final String sign) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(OrderPayActivity.this);
                String result = payTask.pay(sign, true);
                Message message = Message.obtain();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void render(OrderPayWXSign wxSign) {
        PayReq req = new PayReq();
        req.appId = wxSign.getAppid();
        req.partnerId = wxSign.getPartnerid();
        req.prepayId = wxSign.getPrepayid();
        req.nonceStr = wxSign.getNoncestr();
        req.timeStamp = wxSign.getTimestamp();
        req.packageValue = wxSign.getPackag();
        req.sign = wxSign.getSign();
        req.extData = "WxPay";
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        if (BuildConfig.DEBUG)
            Log.i(TAG, "onSucceed: " + wxSign.getAppid());
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, wxSign.getAppid());
            api.registerApp(wxSign.getAppid());
        }
        api.sendReq(req);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void render(List<CouponContents> couponContentses) {
        CouponUtils.BestCountResult bestCoupon = CouponUtils.getMaxDiscountFromCoupons(totalMoney,couponContentses);
        if (bestCoupon != null) {
            needPayMoney = String.valueOf(bestCoupon.getResultMoney());
            coupon.setText("满"+bestCoupon.amount+"元"+"减" + bestCoupon.getDiscount() + "元");
            payMoney.setText("实际支付" + bestCoupon.getResultMoney() + "元");
            couponId = bestCoupon.bestCouponId;
        } else {
            coupon.setText("暂时没有合适的优惠券");
        }
    }

    @Override
    public void renderCount(int count) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event)
    {
        if (event.code==0)
        {
            DetailOrderActivity.start(this, orderId);
            finish();
        }else{
            showMessage(event.msg);
        }
    }
}
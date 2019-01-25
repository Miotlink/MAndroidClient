package com.homepaas.sls.ui.order.pay;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.common.util.PayResult;
import com.homepaas.sls.di.component.DaggerPayComponent;
import com.homepaas.sls.di.module.PayModule;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.BalanceInfo;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.event.PayAbortEvent;
import com.homepaas.sls.event.SuccessPayEvent;
import com.homepaas.sls.event.WXSuccessPayEvent;
import com.homepaas.sls.mvp.presenter.DescriptionPresenter;
import com.homepaas.sls.mvp.presenter.ServicePresenter;
import com.homepaas.sls.mvp.presenter.coupon.CouponContentPresenter;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.presenter.pay.PayPresenter;
import com.homepaas.sls.mvp.view.GetDescriptionView;
import com.homepaas.sls.mvp.view.GetWXSignView;
import com.homepaas.sls.mvp.view.account.WalletBalanceView;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;
import com.homepaas.sls.mvp.view.order.BalancePayView;
import com.homepaas.sls.mvp.view.order.GetAliSignView;
import com.homepaas.sls.mvp.view.order.OrderDetailView;
import com.homepaas.sls.mvp.view.pay.GetBalanceView;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.CouponUtils;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.SatisifiedActionOrderDetailAdapter;
import com.homepaas.sls.ui.redpacket.newRedpacket.UseNewRedPacketActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.EnhanceDialog;
import com.homepaas.sls.ui.widget.NewCommonDialog;
import com.homepaas.sls.util.CountDownTimerUtils;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.StaticData;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;

/**
 * Created by CMJ on 2016/6/24.
 */

public class PayActivity extends CommonBaseActivity implements GetBalanceView, GetAliSignView, GetWXSignView, BalancePayView, CouponContentsView, OrderDetailView, GetDescriptionView, WalletBalanceView, CountDownTimerUtils.OnFinishListner {

    private static final String TAG = "PayActivity";
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
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
    @Bind(R.id.price_and_count)
    TextView priceAndCount;
    @Bind(R.id.avatar)
    CircleImageView avatar;
    @Bind(R.id.provider_name)
    TextView providerName;
    @Bind(R.id.call_button)
    ImageView callButton;
    @Bind(R.id.total_price_text)
    TextView totalPriceText;
    @Bind(R.id.worker_info)
    RelativeLayout reWorkerInfo;
    @Bind(R.id.action_special)
    RecyclerView actionSpecial;
    @Bind(R.id.count_down_time)
    TextView countDownTime;
    @Bind(R.id.ly_count_down)
    LinearLayout countDown;
    @Bind(R.id.deposit_title)
    TextView depositTitle;
    @Bind(R.id.deposit_money)
    TextView depositMoney;
    @Bind(R.id.deposit_rel)
    RelativeLayout depositRel;
    @Bind(R.id.service_price_rel)
    RelativeLayout servicePriceRel;
    @Bind(R.id.coupon_rel)
    RelativeLayout couponRel;

    @Inject
    PayPresenter payPresenter;
    @Inject
    CouponContentPresenter couponPresenter;
    @Inject
    OrderPresenter orderPresenter;
    @Inject
    DescriptionPresenter descriptionPresenter;
    @Inject
    ServicePresenter servicePresenter;
    @Bind(R.id.img_service)
    ImageView imgService;
    @Bind(R.id.tv_service_money)
    TextView tvServiceMoney;
    @Bind(R.id.tv_service_name)
    TextView tvServiceName;

    private double balanceCount;
    private boolean balancePayTag = false;
    private boolean wxPayTag = false;
    private boolean zhifubaoTag = false;
    private String orderId;
    private String confirmGo;
    private String totalMoney;//服务总金额
    private String needPayMoney;//实际需要支付的金额
    private String serviceProviderName;
    private String balancePayMoney = "0";//单一支付时使用
    private String serviceId;
    private String serviceAddressStr = "";
    private String clientNameStr = "";
    private String serviceTimeStr = "";
    private String isKdOrder = "";
    private static final int SDK_PAY_FLAG = 1;
    //优惠券id
    private String couponId = null;
    private Double bPayCount;//余额支付金额
    private Double tPayCount;//三方支付金额
    private String phoneNumber;
    private boolean isNotUseCoupon = false;

    private EnhanceDialog hybridPayDialog;
    private List<CouponContents> couponContentses;
    private BigDecimal bdMinus;//减免的总金额
    private BigDecimal needPayDecimal;//总价
    private BigDecimal couponDecimal;//优惠券减免的金额
    private BigDecimal actionDecimal;//活动减免的金额
    private BigDecimal depositDecimal;//定金的金额
    private CountDownTimerUtils countDownTimerUtils;
    private SatisifiedActionOrderDetailAdapter satisifiedActionOrderDetailAdapter;
    private List<ActivityNgInfoNew.ActivityNgDetail> satisfiedSpecialActivityList;
    private IWXAPI api;
    private NewCommonDialog outOfNoticeDialog;

    private void showOutOfDialog() {
        String contet = "还差一步，确定要退出吗？15:00内还可从订单列表继续支付，否则订单将自动取消哦~";
        long curSystemTime = System.currentTimeMillis();
        long countTime = 0;
        if (countDownTimerUtils != null)
            countTime = countDownTimerUtils.getCurMillisUntilFinished();
        if (countTime > 1000) {
            outOfNoticeDialog = new NewCommonDialog.Builder()
                    .showTitle(false)
                    .setContent(contet)
                    .setmCountDown(true)
                    .setCountDown(countTime, 1000, curSystemTime)
                    .setKeyColor(Color.parseColor("#f56165"))
                    .setCancelTextColor(R.color.count_down_time)
                    .setCancelButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳到订单列表页面
                            goToOrderDtailsActivity();
                            outOfNoticeDialog.dismissDialog();

                        }
                    })
                    .setConfirmTextColor(R.color.count_down_time)
                    .setConfirmButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            outOfNoticeDialog.dismissDialog();
                        }
                    })
                    .create();
            outOfNoticeDialog.show(getSupportFragmentManager(), null);
        } else {
            //跳到订单列表页面
            goToOrderDtailsActivity();
        }

    }

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
//                        EventBus.getDefault().post(new SuccessPayEvent());
                        PaySuccessInfoActivity.start(PayActivity.this, orderId, String.valueOf(needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal)), serviceAddressStr, clientNameStr, serviceTimeStr, isKdOrder);
                        ActivityCompat.finishAfterTransition(PayActivity.this);
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showMessage("支付失败,请稍后重试");

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
                    commonDialog.dismissAllowingStateLoss();
                }
            }).setConfirmTextColor(R.color.appPrimary)
            .setConfirmButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismissAllowingStateLoss();
                }
            }).create();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        initialize();
        payPresenter.setWalletBalanceView(this);
        payPresenter.setBalancePayView(this);
        orderPresenter.setOrderDetailView(this);
        orderId = getIntent().getStringExtra(Constant.OrderId);//考虑后台最好可以给出获取价格的接口，避免前端页面之间传递太多数据，产生严重的依赖，对于推送也不够友好
        confirmGo = getIntent().getStringExtra(StaticData.ConfirmGO);  //0是从定价服务提交订单直接到支付页面，不经过订单详情页；
        orderPresenter.getOrderDetail(orderId);
        totalMoney = getIntent().getStringExtra("TotalMoney");
        needPayMoney = totalMoney;
        serviceProviderName = getIntent().getStringExtra("ServiceProviderName");
        payPresenter.setGetAliSignView(this);
        payPresenter.setGetWXSignView(this);
        couponPresenter.setCouponContentsView(this);
        descriptionPresenter.setGetDescriptionView(this);
        descriptionPresenter.getWarmDescription();
        bindData();
    }

    @Override
    protected void initData() {

    }

    private void initialize() {
        //init for safe;
        bdMinus = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
        needPayDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
        couponDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
        actionDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
        depositDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
    }

    private void bindData() {
        toolbar.setTitle("支付");
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
        intent.putExtra(StaticData.TOTAL_MONEY, totalMoney);
        intent.putExtra(StaticData.IS_NOT_USE_COUPON, isNotUseCoupon);
        intent.putExtra(StaticData.COUPON_ID, couponId);
        intent.putExtra(StaticData.SERVICE_ID, serviceId);
        intent.putExtra(StaticData.IS_PAY_COUPON, true);
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                isNotUseCoupon = data.getBooleanExtra("IsNotUseCoupon", false);
                if (isNotUseCoupon) {
                    couponId = null;
                    coupon.setText("不使用红包");
                    needPayDecimal = new BigDecimal(needPayMoney).setScale(2, RoundingMode.HALF_UP);
                    couponDecimal = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
//                    payMoney.setText("¥" + needPayDecimal.subtract(actionDecimal).subtract(depositDecimal).doubleValue());
                    String payMoney = cutUnnecessaryPrecision(needPayDecimal.subtract(actionDecimal).subtract(depositDecimal).toPlainString());
                    tvServiceMoney.setText("¥" + payMoney);
                    payButton.setText("确认支付¥" + payMoney);
                } else {
                    String discountMoney = data.getStringExtra("DiscountMoney");
                    Double resultMoney = Double.valueOf(data.getStringExtra("ResultMoney"));
                    String amountMoney = data.getStringExtra("AmountMoney");
//                needPayMoney = String.valueOf(resultMoney);//改变实际支付金额
                    couponId = data.getStringExtra("CouponId");
                    needPayDecimal = new BigDecimal(needPayMoney).setScale(2, RoundingMode.HALF_UP);
                    if (!TextUtils.isEmpty(discountMoney)) {
                        couponDecimal = new BigDecimal(discountMoney).setScale(2, RoundingMode.HALF_UP);
                    } else {
                        couponDecimal = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
                    }
//                    payMoney.setText("¥" + needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal).doubleValue());
                    if (TextUtils.equals("0", amountMoney) || TextUtils.equals("0.0", amountMoney) || TextUtils.equals("0.00", amountMoney)) {
                        coupon.setText("无门槛减 ¥" + cutUnnecessaryPrecision(discountMoney));
                    } else {
                        coupon.setText("满" + cutUnnecessaryPrecision(amountMoney) + "减 ¥" + cutUnnecessaryPrecision(discountMoney));
                    }
                    String payMoney = cutUnnecessaryPrecision(needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal).toPlainString());
                    tvServiceMoney.setText("¥" + payMoney);
                    payButton.setText("确认支付¥" + payMoney);
                }

            }
        }
    }

    @OnClick({R.id.balance_layout, R.id.weixin_layout, R.id.zhifubao_layout})
    public void choosePayType(View view) {
        switch (view.getId()) {
            case R.id.weixin_layout://微信
                wxPayTag = !wxPayTag;
                if (wxPayTag) {
                    zhifubaoTag = false;
                }
                if (totalMoney != null && !totalMoney.isEmpty() && balanceCount >= Double.valueOf(totalMoney)) {//有余额余额且足够支付，三者只能选择其一)
                    zhifubaoTag=false;
                    balancePayTag=false;
                }
                break;
            case R.id.zhifubao_layout: //支付宝
                zhifubaoTag = !zhifubaoTag;
                if (zhifubaoTag) {
                    wxPayTag = false;
                }
                if (totalMoney != null && !totalMoney.isEmpty() && balanceCount >= Double.valueOf(totalMoney)) {//有余额余额且足够支付，三者只能选择其一)
                    wxPayTag=false;
                    balancePayTag=false;
                }
                break;
            case R.id.balance_layout://余额
                if (balanceCount <= 0)
                    return;
                balancePayTag = !balancePayTag;
                if (totalMoney != null && !totalMoney.isEmpty() && balanceCount >= Double.valueOf(totalMoney)) {//有余额余额且足够支付，三者只能选择其一)
                    wxPayTag=false;
                    zhifubaoTag=false;
                }
                break;
        }
//        checkBalancePay();

        LogUtils.i("TAG", "wxPayTag:" + wxPayTag + "\t" + "zhifubaoTag:" + zhifubaoTag + "\t" + "balancePayTag:" + balancePayTag + "\t");
        //有余额但是余额不足以支付，选择框的图标需要和其他情况不一致
        if (!TextUtils.isEmpty(totalMoney) && balanceCount > 0 && balanceCount < Double.valueOf(totalMoney)) {
            if (balancePayTag && !zhifubaoTag && !wxPayTag) {
                balancePay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                zhifubaoPay.setImageResource(R.mipmap.anduigou);
                wxPay.setImageResource(R.mipmap.anduigou);
            } else if (balancePayTag && zhifubaoTag) {
                balancePay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                zhifubaoPay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                wxPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
            } else if (balancePayTag && wxPayTag) {
                balancePay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                zhifubaoPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                wxPay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
            } else if (zhifubaoTag) {
                balancePay.setImageResource(R.mipmap.anduigou);
                zhifubaoPay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                wxPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
            } else if (wxPayTag) {
                balancePay.setImageResource(R.mipmap.anduigou);
                zhifubaoPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                wxPay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
            } else {
                balancePay.setImageResource(R.mipmap.anduigou);
                zhifubaoPay.setImageResource(R.mipmap.anduigou);
                wxPay.setImageResource(R.mipmap.anduigou);
            }
        } else if (totalMoney != null && !totalMoney.isEmpty() && balanceCount >= Double.valueOf(totalMoney)) {//有余额余额且足够支付，三者只能选择其一
            if (wxPayTag) {
                balancePay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                zhifubaoPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                wxPay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                zhifubaoTag = false;
                balancePayTag = false;
            } else if (zhifubaoTag) {
                balancePay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                zhifubaoPay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                wxPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                balancePayTag = false;
                wxPayTag = false;
            } else {
                balancePay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                zhifubaoPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                wxPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                zhifubaoTag = false;
                wxPayTag = false;
            }
        } else {//没有余额只能选择支付宝或微信
            balancePay.setImageResource(balancePayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
            zhifubaoPay.setImageResource(zhifubaoTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
            wxPay.setImageResource(wxPayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        }
        payButton.setEnabled(balancePayTag || wxPayTag || zhifubaoTag);
    }

    /**
     * 检查是否可以采用余额支付
     */

    private void checkBalancePay() {
        String money = totalMoney;
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
     * 默认使用余额支付,简单判断余额是否够支付订单
     *
     * @note: 因为两个接口的数据可能存在前后问题 所以接口获取数据后都进行判断下
     */
    private void enableBalancePay() {
        if (balanceCount <= 0) {//余额为o
            balancePayTag = false;
            zhifubaoTag = true;
            zhifubaoPay.setImageResource(zhifubaoTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
            balancePay.setImageResource(balancePayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
            zhifubaoPay.setImageResource(zhifubaoTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
            zhifubaoPay.setEnabled(true);
            payButton.setEnabled(balancePayTag || wxPayTag || zhifubaoTag);
        } else {
            if (totalMoney != null && !totalMoney.isEmpty() && balanceCount >= Double.valueOf(totalMoney)) {//有余额余额且足够支付，三者只能选择其一
                balancePayTag = true;
                payButton.setEnabled(true);
                balancePay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                payButton.setEnabled(balancePayTag || wxPayTag || zhifubaoTag);
            } else {
                //有余额，但是余额不足，默认使用余额和支付宝混搭
                balancePayTag = true;
                zhifubaoTag = true;
                balancePay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                zhifubaoPay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
                wxPay.setImageResource(R.mipmap.subscribe_order_list_no_select);
                zhifubaoPay.setEnabled(true);
                payButton.setEnabled(balancePayTag || wxPayTag || zhifubaoTag);
            }
        }
    }

    @OnClick(R.id.call_button)
    public void callWorker() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    @OnClick({R.id.pay_button})
    public void attemptToPay(View view) {

        if (!NetUtils.isConnected(getApplicationContext())) {
            showMessage(getString(R.string.network_error));
            return;
        }
        if (balancePayTag)//采用余额支付
        {
            if (wxPayTag || zhifubaoTag) {
                View contentView = getLayoutInflater().inflate(R.layout.dialog_hybrid_pay_content, null);
                TextView balancePayCount = (TextView) contentView.findViewById(R.id.balancePayCount);
                TextView payCount = (TextView) contentView.findViewById(R.id.payCount);
                TextView payType = (TextView) contentView.findViewById(R.id.payType);
                //余额支付金额
                bPayCount = 0.00;
                //三方支付金额
                tPayCount = 0.00;
                if (TextUtils.isEmpty(needPayMoney)) {
                    showMessage("订单总价为null,后台返回数据有误");
                    return;
                }
                if (balanceCount > Double.valueOf(needPayMoney) - actionDecimal.doubleValue() - couponDecimal.doubleValue() - depositDecimal.doubleValue()) {//余额足以支付，提示使用余额支付方式
                    bPayCount = Double.valueOf(needPayMoney);
                    tPayCount = 0.0;
                    showMessage("实际支付金额小于余额，请选择单一支付方式");
                    return;
                } else {//采用BigDecimal类进行精确的计算
                    needPayDecimal = new BigDecimal(needPayMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal balanceCountDecimal = new BigDecimal(String.valueOf(balanceCount)).setScale(2, RoundingMode.HALF_UP);
                    bPayCount = balanceCount;//余额全部用完
                    ////三方支付金额:订单总价减去优惠券减免、余额(活动减免不参与计算)
                    tPayCount = needPayDecimal.subtract(balanceCountDecimal).subtract(couponDecimal).doubleValue();
                }
                balancePayCount.setText("余额 ¥" + cutUnnecessaryPrecision(String.valueOf(bPayCount)));//余额支付的金额
                payType.setText(wxPayTag ? "微信支付" : "支付宝支付");
                //弹窗中显示的第三方支付数值是减去了余额、活动减免以及优惠券的金额，传递给后台的tPayCount只减去了优惠券（---兼容老系统---）
                BigDecimal tPayDecimal = new BigDecimal(tPayCount).setScale(2, RoundingMode.HALF_UP).subtract(actionDecimal).subtract(depositDecimal);
                payCount.setText(tPayDecimal.doubleValue() + "元");//第三方支付的金额
                hybridPayDialog = new EnhanceDialog.Builder().setContentView(contentView).
                        setConfirmButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hybridPayDialog.dismissAllowingStateLoss();
                                if (wxPayTag)
                                    payPresenter.getWXPaysign(orderId, couponId, String.valueOf(tPayCount), String.valueOf(bPayCount));
                                else
                                    payPresenter.getAliPaySign(orderId, couponId, String.valueOf(tPayCount), String.valueOf(bPayCount));
                            }
                        }).setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hybridPayDialog.dismissAllowingStateLoss();
                    }
                }).setTitle("付款详情").create();
                hybridPayDialog.show(getSupportFragmentManager(), null);

            } else {//余额支付
                //检查余额是否可以完成支付
                if (balanceCount >= Double.valueOf(needPayMoney) - couponDecimal.doubleValue() - actionDecimal.doubleValue() - depositDecimal.doubleValue())
                    payByBalance();
                else {
                    showMessage("账户余额不足，请选择其他支付方式支付不足部分");
                }
            }
        } else {
            if (wxPayTag) {
                if (BuildConfig.DEBUG)
                    Log.i(TAG, "attemptToPay: " + totalMoney);
                //传递给后台的金额为订单总金额减去优惠券的金额
                BigDecimal nBD = new BigDecimal(needPayMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
                String needPayMoney = String.valueOf(nBD.subtract(couponDecimal).doubleValue());
                payPresenter.getWXPaysign(orderId, couponId, needPayMoney, balancePayMoney);
                return;
            }
            if (zhifubaoTag) {
                BigDecimal nBD = new BigDecimal(needPayMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
                String needPayMoney = String.valueOf(nBD.subtract(couponDecimal).doubleValue());
                payPresenter.getAliPaySign(orderId, couponId, needPayMoney, balancePayMoney);
                return;
            }
        }
        //保存用户上一次支付记录
        savePayWay();
    }

    private void savePayWay() {
//        PayHistoryInfo payHistoryInfo = new PayHistoryInfo();
//        PreferencesUtil.saveObject(StaticData.PAY_HISTORY_INFO,payHistoryInfo);
    }

    private void payByBalance() {
        BigDecimal nBD = new BigDecimal(needPayMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
        String needPayMoney = String.valueOf(nBD.subtract(couponDecimal).doubleValue());
        payPresenter.payByBalance(orderId, couponId, needPayMoney);
    }


    @Override
    public void render(BalanceInfo balanceInfo) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, "render: " + balanceInfo.balance);
        BigDecimal bd = new BigDecimal(balanceInfo.balance);
        bd.setScale(2);
        balanceCount = bd.doubleValue();
        balanceTv.setText("余额 ¥" + cutUnnecessaryPrecision(balanceInfo.balance));

        enableBalancePay();
    }


    /**
     * 余额支付回调
     *
     * @param errcode ..
     */
    @Override
    public void onBalancePayResult(String errcode) {
        boolean success = TextUtils.equals("0", errcode);
        showMessage(success ? "支付成功" : "支付异常");
        if (success) {
            PaySuccessInfoActivity.start(this, orderId, String.valueOf(needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal)), serviceAddressStr, clientNameStr, serviceTimeStr, isKdOrder);
            EventBus.getDefault().post(new SuccessPayEvent());
            ActivityCompat.finishAfterTransition(this);
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
                PayTask payTask = new PayTask(PayActivity.this);
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
        this.couponContentses = couponContentses;
        computeCouponFavors();
    }

    @Override
    public void renderCount(int count) {

    }

    @Override
    public void render(DetailOrderEntity order, long requestTime) {
        if (countDownTimerUtils != null) {
            countDownTimerUtils.cancel();
            countDownTimerUtils = null;
        }
        if (order != null) {
            //传递到支付成功页面的数据
            if (order.getService() != null) {
                Service service = order.getService();
                serviceTimeStr = service.getServiceStartAt();
                if (service.getAddressInfo() != null) {
                    serviceAddressStr = service.getAddressInfo().getAddress1();
                    clientNameStr = service.getAddressInfo().getContact();
                }
            }
            isKdOrder = order.getIsKdEOrder();
            if (!TextUtils.equals("50", order.getOrderStatus())) {
                //倒计时
                if (!TextUtils.isEmpty(order.getResidualTime()) && Long.valueOf(order.getResidualTime()) > 0) {
                    long millisInFuture = Long.valueOf(order.getResidualTime()).longValue();
                    long offset = (long) Math.ceil(System.currentTimeMillis() / 1000 - requestTime / 1000);
                    long countDownmillisInFuture = (long) Math.floor(millisInFuture - offset);
                    if (countDownmillisInFuture >= 1) {
                        countDown.setVisibility(View.VISIBLE);
                        countDownTimerUtils = new CountDownTimerUtils(countDownmillisInFuture * 1000, 1000, countDownTime);
                        countDownTimerUtils.setOnFinishListner(this);
                        countDownTimerUtils.start();


                    } else {//剩余时间<1s时延时响应的时间结束支付跳转到订单详情页面
                        countDown.setVisibility(View.GONE);
                        balanceTv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                goToOrderDtailsActivity();
                            }
                        }, 1000);
                    }
                } else {
//                    countDownTimerUtils.cancel();
//                    countDownTimerUtils = null;
                    countDown.setVisibility(View.GONE);
                }
//                //订单已提交显示
//                if (TextUtils.equals("0", order.getNegotiable()) || (order.getDepositInfo() != null && TextUtils.equals("0", order.getDepositInfo().getDepositIsPayOff()))) {
//                    showStatusRel.setVisibility(View.VISIBLE);
//                } else {
//                    showStatusRel.setVisibility(View.GONE);
//                }
//                //工人显示
//                if (!TextUtils.isEmpty(order.getIsKdEOrder()) && TextUtils.equals("2", order.getIsKdEOrder())) {
//                    reWorkerInfo.setVisibility(View.GONE);
//                } else {
//                    if (order.getServiceProviderId() == null ||
//                            order.getServiceProviderName() == null) {
//                        reWorkerInfo.setVisibility(View.GONE);
//                    } else {
//                        reWorkerInfo.setVisibility(View.VISIBLE);
//
//                        String serviceProviderPic = order.getServiceProviderPic();
//                        if (!TextUtils.isEmpty(serviceProviderPic)) {
//                            Glide.with(mContext).load(serviceProviderPic)
//                                    .asBitmap()
//                                    .fitCenter()
//                                    .placeholder(R.mipmap.service_or_worker_icon_default)
//                                    .error(R.mipmap.service_or_worker_icon_default)
//                                    .into(new SimpleTarget<Bitmap>() {
//
//                                        @Override
//                                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                                            avatar.setImageBitmap(resource);
//                                        }
//                                    });
//                        }
//                        if (!TextUtils.isEmpty(order.getGender())) {
//                            serviceProviderName = order.getGender().equals("0") ? order.getServiceProviderName() + "  师傅" : order.getServiceProviderName() + "  阿姨";
//                        } else {
//                            serviceProviderName = order.getServiceProviderName();
//                        }
//                        providerName.setText(serviceProviderName);
//                    }
//                }
                bindData();
                //服务类型图片
                Glide.with(mContext).load(order.getService().getServiceIcon())
                        .asBitmap()
                        .fitCenter()
                        .placeholder(R.mipmap.service_or_worker_icon_default)
                        .error(R.mipmap.service_or_worker_icon_default)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                imgService.setImageBitmap(resource);
                            }
                        });
                //服务名称
                tvServiceName.setText(order.getService().getServiceName());
                //有没有定金，定金有没有支付，两种布局显示
                if (order.getDepositInfo() != null && !TextUtils.isEmpty(order.getDepositInfo().getDepositAmount()) && TextUtils.equals("0", order.getDepositInfo().getDepositIsPayOff())) {
                    servicePriceRel.setVisibility(View.GONE);
                    actionSpecial.setVisibility(View.GONE);
                    couponRel.setVisibility(View.GONE);
                    depositRel.setVisibility(View.GONE);
                    tvServiceName.setText("订金");
//                    depositTitle.setText();
//                    depositMoney.setText("¥" + order.getDepositInfo().getDepositAmount());
                    totalMoney = order.getDepositInfo().getDepositAmount();
                    needPayMoney = totalMoney;
                    needPayDecimal = new BigDecimal(needPayMoney);
                    String money = cutUnnecessaryPrecision(String.valueOf(needPayMoney));
//                    payMoney.setText("¥" + money);
                    tvServiceMoney.setText("¥" + money);
                    payButton.setText("确认支付¥" + money);
                    serviceId = order.getService().getServiceId();

                    payPresenter.getAccountInfo();
                } else {
//                    servicePriceRel.setVisibility(View.VISIBLE);
                    actionSpecial.setVisibility(View.VISIBLE);
                    couponRel.setVisibility(View.VISIBLE);
//                    priceAndCount.setText(order.price + "/" + order.unitName + "x\t" + order.serviceNumber);
                    totalMoney = order.totalPrice;
//                    totalPriceText.setText("¥" + cutUnnecessaryPrecision(totalMoney));
//                    priceAndCount.setText(order.getServiceNumber() + "x" + "\t\t" + "¥" + order.getPrice());
                    needPayMoney = totalMoney;
                    phoneNumber = order.getServiceProviderPhone();
                    ActivityNgInfoNew activityNgInfos = order.getActivityNgInfos();
                    if (!TextUtils.isEmpty(order.getTotalPrice()) && activityNgInfos != null && activityNgInfos.isSpecialSatisfied(Float.valueOf(order.getTotalPrice()))) {
                        actionSpecial.setVisibility(View.VISIBLE);
                        satisfiedSpecialActivityList = activityNgInfos.getSatisfiedSpecialActivityList();
                        if (satisifiedActionOrderDetailAdapter == null) {
                            satisifiedActionOrderDetailAdapter = new SatisifiedActionOrderDetailAdapter();
//                            actionSpecial.addItemDecoration(new SimpleItemDecoration(this, SimpleItemDecoration.VERTICAL_LIST));
                            actionSpecial.setAdapter(satisifiedActionOrderDetailAdapter);
                        } else {
                            actionSpecial.getAdapter();
                        }
                        satisifiedActionOrderDetailAdapter.setData(satisfiedSpecialActivityList);
                    } else {
                        actionSpecial.setVisibility(View.GONE);
                    }

                    String minus = "0";
                    if (!TextUtils.isEmpty(needPayMoney)) {
                        needPayDecimal = new BigDecimal(needPayMoney);
                    }
                    if (activityNgInfos != null && !TextUtils.isEmpty(order.getTotalPrice()) && activityNgInfos.isSpecialSatisfied(Float.valueOf(order.getTotalPrice()))) {
                        for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : activityNgInfos.getSatisfiedSpecialActivityList()) {
                            String minuss = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
                            BigDecimal newminusDecimal = new BigDecimal(minuss).setScale(2, RoundingMode.HALF_UP);
                            actionDecimal = actionDecimal.add(newminusDecimal);
                        }
                    }
                    if (order.getDepositInfo() != null && !TextUtils.isEmpty(order.getDepositInfo().getDepositAmount())) {
                        depositRel.setVisibility(View.VISIBLE);
                        depositTitle.setText("已付订金");
                        depositMoney.setText("-¥" + order.getDepositInfo().getDepositAmount());
                        depositDecimal = new BigDecimal(order.getDepositInfo().getDepositAmount()).setScale(2, RoundingMode.HALF_UP);
                    } else {
                        depositRel.setVisibility(View.GONE);
                    }
                    float moneyString = needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal).floatValue();
                    String money = cutUnnecessaryPrecision(String.valueOf(moneyString));
//                    payMoney.setText("¥" + money);
                    tvServiceMoney.setText("¥" + money);
                    payButton.setText("确认支付¥" + money);
                    //获取到订单详情后在去请求余额
                    serviceId = order.getService().getServiceId();
                    payPresenter.getAccountInfo();
                    couponPresenter.getPayCouponList(totalMoney, serviceId);
                }
            } else {
                ClientOrderDetailActivity.start(this, orderId);
                finish();
            }
        }
    }

    @Override
    public void renderTrackOrderStatus(TrackOrderInfo orderInfo) {

    }

    private void computeCouponFavors() {
        //FIXME!!!一个页面好几个接口存在先后顺序,接口需要做严格的处理,否则逻辑上会有Bug,以后再处理
        if (totalMoney == null || totalMoney.isEmpty()) {
            return;
        }

        CouponUtils.BestCountResult bestCoupon = CouponUtils.getMaxDiscountFromCoupons(totalMoney, couponContentses);
        if (bestCoupon != null) {
            if (!TextUtils.isEmpty(bestCoupon.amount) && TextUtils.equals(bestCoupon.amount, "0") || TextUtils.equals(bestCoupon.amount, "0.0") || TextUtils.equals(bestCoupon.amount, "0.00")) {
                coupon.setText("无门槛减" + cutUnnecessaryPrecision(bestCoupon.getDiscount()) + "元");
            } else {
                coupon.setText("满" + cutUnnecessaryPrecision(bestCoupon.amount) + "元" + "减" + cutUnnecessaryPrecision(bestCoupon.getDiscount()) + "元");
            }
            couponDecimal = new BigDecimal(bestCoupon.getDiscount());
//            BigDecimal needPayDecimal = new BigDecimal(needPayMoney);
//            needPayDecimal.setScale(2, RoundingMode.HALF_UP);
//            float moneyString = needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal).floatValue();
//            payMoney.setText("¥" + cutUnnecessaryPrecision(String.valueOf(moneyString)));
//            payButton.setText("确认支付 ¥" + moneyString);
            couponId = bestCoupon.bestCouponId;
        } else {
            coupon.setText("暂时没有合适的红包");
        }
        needPayDecimal = new BigDecimal(needPayMoney);
        needPayDecimal.setScale(2, RoundingMode.HALF_UP);
        float moneyStr = needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal).floatValue();
        String money = cutUnnecessaryPrecision(String.valueOf(moneyStr));
//        payMoney.setText("¥" + money);
        tvServiceMoney.setText("¥" + money);
        payButton.setText("确认支付 ¥" + money);
    }

    @Override
    public void renderDescription(String description) {
        serviceIllustrate.setText(description);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event) {
        if (event.code == 0) {
            finish();
        } else {
            showMessage(event.msg);
        }
    }

//    @OnClick(R.id.help)
//    public void showHelp() {
//        showReminderDialog();
//    }

    /*****
     * 微信支付结果的回调
     ******/

    //取消支付，或者支付不成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayNotSuccess(PayAbortEvent event) {
        if (event.msg != null)
            showMessage(event.msg);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(WXSuccessPayEvent event) {
        showMessage("支付成功");
        PaySuccessInfoActivity.start(PayActivity.this, orderId, String.valueOf(needPayDecimal.subtract(couponDecimal).subtract(actionDecimal).subtract(depositDecimal)), serviceAddressStr, clientNameStr, serviceTimeStr, isKdOrder);
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void renderAccountInfo(NewAccountInfo info) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, "render: " + info.getSettlementBalance());
        BigDecimal bd = new BigDecimal(info.getSettlementBalance());
        bd.setScale(2);
        balanceCount = bd.doubleValue();
        balanceTv.setText("余额 ¥" + cutUnnecessaryPrecision(info.getSettlementBalance()));

        enableBalancePay();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String countDownTimeStr = countDownTime.getText().toString();
                int countDownVis = countDown.getVisibility();
                if (!TextUtils.isEmpty(countDownTimeStr) && !TextUtils.equals(countDownTimeStr, "00:00") && countDownVis == 0) {
                    showOutOfDialog();
                } else {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String countDownTimeStr = countDownTime.getText().toString();
        int countDownVis = countDown.getVisibility();
        if (!TextUtils.isEmpty(countDownTimeStr) && !TextUtils.equals(countDownTimeStr, "00:00") && countDownVis == 0) {
            showOutOfDialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFinish(boolean isFinish) {
        countDown.setVisibility(View.GONE);
        if (countDownTimerUtils != null) {
            countDownTimerUtils.cancel();
            countDownTimerUtils = null;
        }

        //计时结束直接结束支付，跳到订单列表页面
        balanceTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToOrderDtailsActivity();
            }
        }, 1000);

    }

    public void goToOrderDtailsActivity() {
        Intent intent = new Intent(PayActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ToOrderList", 3);
        startActivity(intent);
//        OrderDetailsActivity.start(PayActivity.this, orderId, "0");
        ActivityCompat.finishAfterTransition(PayActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimerUtils != null) {
            countDownTimerUtils.cancel();
            countDownTimerUtils = null;
        }
    }
}

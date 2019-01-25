package com.homepaas.sls.ui.newdetail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.common.util.PayResult;
import com.homepaas.sls.di.component.DaggerBusWorkerServiceComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.entity.WxSign;
import com.homepaas.sls.event.PayAbortEvent;
import com.homepaas.sls.event.WXSuccessPayEvent;
import com.homepaas.sls.mvp.presenter.DescriptionPresenter;
import com.homepaas.sls.mvp.presenter.pay.PayPresenter;
import com.homepaas.sls.mvp.view.GetDescriptionView;
import com.homepaas.sls.mvp.view.account.WalletBalanceView;
import com.homepaas.sls.mvp.view.pay.GetDirectPayIdView;
import com.homepaas.sls.mvp.view.pay.GetDirectPaySignView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.EnhanceDialog;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.util.StaticData;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;

/**
 * Created by JWC on 2017/2/9.
 */

public class PayOrderActivity extends CommonBaseActivity implements WalletBalanceView, GetDirectPayIdView, GetDirectPaySignView,GetDescriptionView {
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.pay_money)
    TextView payMoney;
    @Bind(R.id.price_rel)
    RelativeLayout priceRel;
    @Bind(R.id.balance_icon)
    TextView balanceIcon;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.balance_pay)
    ImageView balancePay;
    @Bind(R.id.balance_text)
    TextView balanceText;
    @Bind(R.id.balance_layout)
    RelativeLayout balanceLayout;
    @Bind(R.id.alipay_icon)
    TextView alipayIcon;
    @Bind(R.id.zhifubao_pay)
    ImageView zhifubaoPay;
    @Bind(R.id.zhifubao_layout)
    RelativeLayout zhifubaoLayout;
    @Bind(R.id.wechat_icon)
    TextView wechatIcon;
    @Bind(R.id.wx_pay)
    ImageView wxPay;
    @Bind(R.id.weixin_layout)
    RelativeLayout weixinLayout;
    @Bind(R.id.pay_mode)
    LinearLayout payMode;
    @Bind(R.id.pay_button)
    Button payButton;
    @Bind(R.id.prompt_text)
    TextView promptText;

    private boolean balancePayTag = false;
    private boolean wxPayTag = false;
    private boolean zhifubaoTag = false;
    private static final String TOTAL_MONEY = "tatal_money";
    private static final String NEED_PAY_MONEY = "need_pay_money";
    private static final String RECEIVER_ID = "receiver_id";  //被支付人id（工人id）
    private static final String SERVICE_TYPE_ID = "serviceType_id";  //服务类别，即工人信息的defaultService字段
    private static final String ACTIVITYNG_ID = "activityNg_id";
    private double totalMoney = 0.00;
    private String receiverId;
    private String activityNgId;
    private String merchantNameStr;
    private double balanceCount;
    private IWXAPI api;
    private String payOrderId;//订单单号

    private EnhanceDialog hybridPayDialog;
    private CommonDialog balanceNotEnoughDialog;

    private double needPayMoney;//实际需要支付的金额
    private double bPayCount;//余额支付金额
    private double tPayCount;//三方支付金额


    public static void start(Context context, String receiverId, double totalMoney, double needPayMoney, String activityNgId, String merchantName) {
        Intent intent = new Intent(context, PayOrderActivity.class);
        intent.putExtra(RECEIVER_ID, receiverId);
        intent.putExtra(TOTAL_MONEY, totalMoney);
        intent.putExtra(NEED_PAY_MONEY, needPayMoney);
        intent.putExtra(ACTIVITYNG_ID, activityNgId);
        intent.putExtra(StaticData.MERCHANT_NAME, merchantName);
        context.startActivity(intent);

    }

    @Inject
    PayPresenter payPresenter;
    @Inject
    DescriptionPresenter descriptionPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        totalMoney = getIntent().getDoubleExtra(TOTAL_MONEY, 0.00);
        needPayMoney = getIntent().getDoubleExtra(NEED_PAY_MONEY, 0.00);
        receiverId = getIntent().getStringExtra(RECEIVER_ID);
        activityNgId = getIntent().getStringExtra(ACTIVITYNG_ID);
        merchantNameStr = getIntent().getStringExtra(StaticData.MERCHANT_NAME);
        payMoney.setText("¥" + needPayMoney + "元");
        payPresenter.setWalletBalanceView(this);
        payPresenter.setGetDirectPayIdView(this);
        payPresenter.setGetDirectPaySignView(this);
        payPresenter.getAccountInfo();
        descriptionPresenter.setGetDescriptionView(this);
        descriptionPresenter.getWarmDescription();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerBusWorkerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build().inject(this);
    }

    @OnClick({R.id.balance_layout, R.id.weixin_layout, R.id.zhifubao_layout})
    public void choosePayType(View view) {
        switch (view.getId()) {
            case R.id.weixin_layout:
                wxPayTag = !wxPayTag;
                if (wxPayTag) {
                    zhifubaoTag = false;
                }
                break;
            case R.id.zhifubao_layout:
                zhifubaoTag = !zhifubaoTag;
                if (zhifubaoTag) {
                    wxPayTag = false;
                }
                break;
            case R.id.balance_layout:
                balancePayTag = !balancePayTag;
                break;
        }
        balancePay.setImageResource(balancePayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        zhifubaoPay.setImageResource(zhifubaoTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        wxPay.setImageResource(wxPayTag ? R.mipmap.client_v330_ic_orders_pitch_on : R.mipmap.subscribe_order_list_no_select);
        payButton.setEnabled(balancePayTag || wxPayTag || zhifubaoTag);
    }

    /**
     * 默认使用余额支付,简单判断余额是否够支付订单
     *
     * @note: 因为两个接口的数据可能存在前后问题 所以接口获取数据后都进行判断下
     */
    private void enableBalancePay() {
        if (balanceCount > totalMoney) {
            balancePayTag = true;
            payButton.setEnabled(true);
            balancePay.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
        }
    }

    @Override
    public void renderAccountInfo(NewAccountInfo info) {
        if (info != null) {
            BigDecimal bd = new BigDecimal(info.getSettlementBalance());
            bd.setScale(2);
            balanceCount = bd.doubleValue();
            balanceTv.setText("¥" + cutUnnecessaryPrecision(info.getSettlementBalance()));
            enableBalancePay();
        }
    }


    @OnClick({R.id.pay_button})
    public void attemptToPay(View view) {

        if (!NetUtils.isConnected(getApplication())) {
            showMessage(getString(R.string.network_error));
            return;
        }
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
                if (balanceCount > totalMoney) {
                    bPayCount = totalMoney;
                    tPayCount = 0.0;
                    showMessage("实际支付金额小于余额，请选择单一支付方式");
                    return;
                } else {
                    BigDecimal nBD = new BigDecimal(totalMoney);
                    nBD.setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal bBD = new BigDecimal(String.valueOf(balanceCount));
                    bBD.setScale(2);
                    bPayCount = balanceCount;//余额全部用完
                    tPayCount = nBD.subtract(bBD).doubleValue();//三方支付金额
                }
                balancePayCount.setText("余额" + bPayCount + "元");//余额支付的金额
                payCount.setText(tPayCount + "元");//第三方支付的金额
                payType.setText(!zhifubaoTag ? "微信支付" : "支付宝支付");
                hybridPayDialog = new EnhanceDialog.Builder().setContentView(contentView).
                        setConfirmButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hybridPayDialog.dismiss();
                                payPresenter.createDirectPay(receiverId, "3", "1", String.valueOf(totalMoney), activityNgId);
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
                if (totalMoney > balanceCount) {//无法使用余额支付
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
                    balanceNotEnoughDialog.show(getSupportFragmentManager(), null);
                } else {
                    payPresenter.createDirectPay(receiverId, "3", "1", String.valueOf(totalMoney), activityNgId);
                }
            }
        } else {
            payPresenter.createDirectPay(receiverId, "3", "1", String.valueOf(totalMoney), activityNgId);
        }
    }


    /**
     * 获取到了直接支付的id
     *
     * @param id
     */
    @Override
    public void onDirectPayIdResult(String id) {
        payOrderId = id;
        //根据支付tag判断是哪一种支付，获取相应的支付签名
        if (balancePayTag) {
            if (wxPayTag) {
                payPresenter.getDirectPayWxSign(id, balancePay + "");
            } else if (zhifubaoTag) {
                payPresenter.getDirectPayAliSign(id, balancePay + "");
            } else {
                //余额支付
                //检查余额是否可以完成支付
                if (balanceCount > Double.valueOf(needPayMoney)) {
                    payPresenter.directBalancePay(id, needPayMoney + "");
                } else {
                    showMessage("账户余额不足，请选择其他支付方式支付不足部分");
                }
            }
        } else {
            if (wxPayTag) {
                payPresenter.getDirectPayWxSign(id, 0.00 + "");
            } else if (zhifubaoTag) {
                payPresenter.getDirectPayAliSign(id, 0.00 + "");
            }
        }
    }

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
                        PayInStoreSuccessActivity.start(PayOrderActivity.this, String.valueOf(needPayMoney), merchantNameStr, TimeUtils.getCurrentTime());
                        ActivityCompat.finishAfterTransition(PayOrderActivity.this);

                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showMessage("支付失败");

                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            showMessage("支付取消");
                        } else {
                            showMessage("支付失败");
                        }
                    }
                    break;
            }
        }
    };


    @Override
    public void onAliSign(final String aliSign) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(PayOrderActivity.this);
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
        if (sign != null) {
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
        } else {
            if (BuildConfig.DEBUG)
                Log.d("PAY_GET", "返回错误");
        }
    }

    @Override
    public void onBalancePayResult(String string) {
        showMessage("支付成功");
//        payPresenter.getAccountInfo();
        PayInStoreSuccessActivity.start(this, String.valueOf(needPayMoney), merchantNameStr, TimeUtils.getCurrentTime());
        ActivityCompat.finishAfterTransition(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event) {
        if (event.code == 0) {
            finish();
        } else {
            showMessage(event.msg);
        }
    }

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
        PayInStoreSuccessActivity.start(this, String.valueOf(needPayMoney), merchantNameStr, TimeUtils.getCurrentTime());
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private String mCallingPhone;
    private String mTitle;

    // 拨打电话
    private void dial(String phone, String title) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getSupportFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION_CALL_AND_CALL_LOG);
            }
        }
    }

    @Override
    public void renderDescription(String descripiton) {
        SpannableString spStr = new SpannableString(descripiton);
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#3fbef9"));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                dial(getString(R.string.service_phone_number), "客服电话");
            }
        }, spStr.length() - 12, spStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        promptText.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        promptText.append(spStr);
        promptText.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }
}

package com.homepaas.sls.ui.order;

import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.common.util.PayResult;
import com.homepaas.sls.data.entity.AddServiceAlipayEntity;
import com.homepaas.sls.data.entity.AddServiceWxpayEntity;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.event.PayAbortEvent;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.contract.AddServiceNumContract;
import com.homepaas.sls.newmvp.presenter.AddServiceNumPresenter;
import com.homepaas.sls.ui.common.activity.CommonMvpBaseActivity;
import com.homepaas.sls.ui.order.pay.PaySuccessInfoActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.EnhanceDialog;
import com.homepaas.sls.ui.widget.MyTextWatcher;
import com.homepaas.sls.util.ArithUtil;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.StaticData;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;


/**
 * Created by mhy on 2017/8/26.
 * 增加服务数量
 */

public class AddServiceNumActivity extends CommonMvpBaseActivity<AddServiceNumContract.Presenter> implements AddServiceNumContract.View {
    @Bind(R.id.ed_money)
    EditText edMoney;
    @Bind(R.id.balance_icon)
    ImageView balanceIcon;
    @Bind(R.id.balance_text)
    TextView balanceText;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.balance_pay)
    ImageView balancePay;
    @Bind(R.id.balance_layout)
    RelativeLayout balanceLayout;
    @Bind(R.id.alipay_icon)
    ImageView alipayIcon;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.zhifubao_pay)
    ImageView zhifubaoPay;
    @Bind(R.id.zhifubao_layout)
    RelativeLayout zhifubaoLayout;
    @Bind(R.id.wechat_icon)
    ImageView wechatIcon;
    @Bind(R.id.wx_pay)
    ImageView wxPay;
    @Bind(R.id.weixin_layout)
    RelativeLayout weixinLayout;
    @Bind(R.id.pay_button)
    Button payButton;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;


    private NewAccountInfo newAcountInfo;
    private IWXAPI api;
    private Subscription subscription;
    private EnhanceDialog hybridPayDialog;

    private String orderID;
    private double balanceCount;
    //%2==0 表示没选中
    private int balancePayClick;//余额支付单击次数
    private int zhifubaoPayClick;//支付宝支付单击次数
    private int wxPayClick;//微信支付单击次数

    @Override
    protected AddServiceNumContract.Presenter getPresenter() {
        return new AddServiceNumPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_service_num;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        orderID = getIntent().getStringExtra(StaticData.ORDER_ID);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edMoney.addTextChangedListener(new MyTextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before,
                                      int count) {
                if (arg0.length() == 0) {
                    // No entered text so will show hint
                    edMoney.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                } else {
                    edMoney.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getBalanceInfo();//获取用户余额信息
    }

    @OnClick({R.id.balance_layout, R.id.zhifubao_layout, R.id.weixin_layout, R.id.pay_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.balance_layout:
                balancePayClick++;
                balancePay.setImageResource(balancePayClick % 2 == 0 ? R.mipmap.subscribe_order_list_no_select : R.mipmap.client_v330_ic_orders_pitch_on);
                break;
            case R.id.zhifubao_layout:
                //不可以三者同时选择，需要做限制
                if (wxPayClick % 2 != 0) {
                    wxPayClick--;
                    wxPay.setImageResource(wxPayClick % 2 == 0 ? R.mipmap.subscribe_order_list_no_select : R.mipmap.client_v330_ic_orders_pitch_on);
                }
                zhifubaoPayClick++;
                zhifubaoPay.setImageResource(zhifubaoPayClick % 2 == 0 ? R.mipmap.subscribe_order_list_no_select : R.mipmap.client_v330_ic_orders_pitch_on);
                break;
            case R.id.weixin_layout:
                //不可以三者同时选择，需要做限制
                if (zhifubaoPayClick % 2 != 0) {
                    zhifubaoPayClick--;
                    zhifubaoPay.setImageResource(wxPayClick % 2 == 0 ? R.mipmap.subscribe_order_list_no_select : R.mipmap.client_v330_ic_orders_pitch_on);
                }
                wxPayClick++;
                wxPay.setImageResource(wxPayClick % 2 == 0 ? R.mipmap.subscribe_order_list_no_select : R.mipmap.client_v330_ic_orders_pitch_on);
                break;
            case R.id.pay_button:
                pay();
                break;
        }

    }


    /**
     * 判断支付方式
     */
    private void pay() {
        if (TextUtils.isEmpty(edMoney.getText().toString().trim())) {
            showMessage("输入补拍金额");
        } else if (zhifubaoPayClick % 2 == 0 && wxPayClick % 2 == 0 && balancePayClick % 2 == 0) {
            showMessage("请选择支付方式");
        } else if (zhifubaoPayClick % 2 == 0 && wxPayClick % 2 == 0 && balancePayClick % 2 != 0) {                //余额支付
            if (!ArithUtil.compare(newAcountInfo.getSettlementBalance(), edMoney.getText().toString().trim())) {
                //余额支付不足提示
                showMessage("账户余额不足，请选择其他支付方式支付不足部分");
            } else {
                mPresenter.iodOfBalancePay(orderID, edMoney.getText().toString().trim());
            }
        } else if (wxPayClick % 2 != 0) { //微信支付
            if (balancePayClick % 2 == 0) {
                //单独微信支付  余额支付为"0"
                mPresenter.iodOfWxpay(orderID, "0", edMoney.getText().toString().trim());
            } else {
                if (ArithUtil.compare(newAcountInfo.getSettlementBalance(), edMoney.getText().toString().trim()))//如果余额支付可以单独支付
                    showMessage("实际支付金额小于余额，请选择单一支付方式");
                else {//微信和余额支付混合
                    payShowDialog(false);
                }
            }
        } else if (zhifubaoPayClick % 2 != 0) { //支付宝支付
            if (balancePayClick % 2 == 0) {
                //单独支付宝支付  余额支付为"0"
                mPresenter.iodOfAlipay(orderID, "0", edMoney.getText().toString().trim());
            } else {
                if (ArithUtil.compare(newAcountInfo.getSettlementBalance(), edMoney.getText().toString().trim())) //如果余额支付可以单独支付，就不需要混合支付
                    showMessage("实际支付金额小于余额，请选择单一支付方式");
                else {
                    payShowDialog(true);
                }
            }
        }
    }

    /**
     * 余额支付和三方支付混合提示弹框弹框
     */
    public void payShowDialog(final boolean isAliPay) {
        View contentView = getLayoutInflater().inflate(R.layout.dialog_hybrid_pay_content, null);
        TextView balancePayCount = (TextView) contentView.findViewById(R.id.balancePayCount);
        TextView payCount = (TextView) contentView.findViewById(R.id.payCount);
        TextView payType = (TextView) contentView.findViewById(R.id.payType);
        balancePayCount.setText("余额 ¥" + cutUnnecessaryPrecision(newAcountInfo.getSettlementBalance()));//余额支付的金额
        payType.setText(isAliPay ? "支付宝支付" : "微信支付");
        payCount.setText(ArithUtil.sub(edMoney.getText().toString().trim(), newAcountInfo.getSettlementBalance()).toString() + "元");//第三方支付的金额

        hybridPayDialog = new EnhanceDialog.Builder().setContentView(contentView).
                setConfirmButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hybridPayDialog.dismissAllowingStateLoss();
                        //余额和支付宝 或微信混合  newAcountInfo.getSettlementBalance() 余额全部用完
                        //ArithUtil.sub(edMoney.getText().toString().trim(),newAcountInfo.getSettlementBalance()).toString() 余额支付缺少的钱交给第三方
                        if (isAliPay) //支付宝混合支付
                            mPresenter.iodOfAlipay(orderID, newAcountInfo.getSettlementBalance(), ArithUtil.sub(edMoney.getText().toString().trim(), newAcountInfo.getSettlementBalance()).toString());
                        else //微信混合支付
                            mPresenter.iodOfWxpay(orderID, newAcountInfo.getSettlementBalance(), ArithUtil.sub(edMoney.getText().toString().trim(), newAcountInfo.getSettlementBalance()).toString());
                    }
                }).setCancelButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hybridPayDialog.dismissAllowingStateLoss();
            }
        }).setTitle("付款详情").create();
        hybridPayDialog.show(getSupportFragmentManager(), null);
    }
////////////////////////////////////interface info

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
            //进入支付成功界面
            PaySuccessInfoActivity.start(AddServiceNumActivity.this, orderID, edMoney.getText().toString().trim(), "", "", "", "");
            finish();
        }
    }

    @Override
    public void initBalanceInfo(NewAccountInfo newAccountInfo) {
        this.newAcountInfo = newAccountInfo;
        BigDecimal bd = new BigDecimal(newAccountInfo.getSettlementBalance());
        bd.setScale(2);
        balanceCount = bd.doubleValue();
        balanceTv.setText("余额" + cutUnnecessaryPrecision(newAccountInfo.getSettlementBalance()) + "元");
        if (balanceCount > 0) {//默认选中余额支付
            balancePayClick++;
            balancePay.setImageResource(balancePayClick % 2 == 0 ? R.mipmap.subscribe_order_list_no_select : R.mipmap.client_v330_ic_orders_pitch_on);
        }
    }

    @Override
    public void iodOfWxpay(AddServiceWxpayEntity addServiceWxpayEntity) {
        AddServiceWxpayEntity.WxpaySignBean wxSign = addServiceWxpayEntity.getWxpaySign();
        PayReq req = new PayReq();
        req.appId = wxSign.getAppid();
        req.partnerId = wxSign.getPartnerid();
        req.prepayId = wxSign.getPrepayid();
        req.nonceStr = wxSign.getNoncestr();
        req.timeStamp = wxSign.getTimestamp();
        req.packageValue = wxSign.getPackageX();
        req.sign = wxSign.getSign();
        req.extData = "WxPay";
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        if (BuildConfig.DEBUG)
            LogUtils.i("TAG", "onSucceed: " + wxSign.getAppid());
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, wxSign.getAppid());
            api.registerApp(wxSign.getAppid());
        }
        api.sendReq(req);
    }

    @Override
    public void iodOfAlipay(final AddServiceAlipayEntity addServiceAlipayEntity) {
        //发起支付宝支付
        subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                PayTask payTask = new PayTask(AddServiceNumActivity.this);
                String result = payTask.pay(addServiceAlipayEntity.getAlipaySign(), true);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).compose(RxUtil.<String>applySchedulers()).
                subscribe(new Action1<String>() {
                    @Override
                    public void call(String sign) {
                        PayResult payResult = new PayResult(sign);
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        if (TextUtils.equals(resultStatus, "9000")) {
                            showMessage("支付成功");
                            PaySuccessInfoActivity.start(AddServiceNumActivity.this, orderID, edMoney.getText().toString().trim(), "", "", "", "");
                            ActivityCompat.finishAfterTransition(AddServiceNumActivity.this);
                        } else {
                            if (TextUtils.equals(resultStatus, "8000")) {
                                showMessage("支付失败,请稍后重试");
                            } else if (TextUtils.equals(resultStatus, "6001")) {

                            } else {

                            }
                        }
                    }
                });
    }

    /**
     * 微信支付回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event) {
        if (event.code == 0) {
            showMessage("支付成功");
            PaySuccessInfoActivity.start(AddServiceNumActivity.this, orderID, edMoney.getText().toString().trim(), "", "", "", "");
            ActivityCompat.finishAfterTransition(AddServiceNumActivity.this);
        } else {
            showMessage(event.msg);
        }
    }

    /**
     * 微信支付回调
     *
     * @param event
     */
    //取消支付，或者支付不成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayNotSuccess(PayAbortEvent event) {
        if (event.msg != null)
            showMessage(event.msg);
    }
    ////////////////////////////////////interface info


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        EventBus.getDefault().unregister(this);
    }
}

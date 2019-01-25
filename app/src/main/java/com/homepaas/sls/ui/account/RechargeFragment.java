package com.homepaas.sls.ui.account;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.di.component.DaggerRechargeComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.event.PayAbortEvent;
import com.homepaas.sls.event.WXSuccessPayEvent;
import com.homepaas.sls.mvp.presenter.account.NewRechargePresenter;
import com.homepaas.sls.mvp.view.account.RechargeView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.widget.CallPhoneUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值页面
 */
public class RechargeFragment extends CommonBaseFragment implements RechargeView {

    private static final String TAG = "RechargeFragment";

    private static final String ARG_PARAM = "param";


    @Bind(R.id.recharge_explanation)
    TextView mRechargeExplanation;

    @Bind(R.id.recharge_money)
    TextView mRechargeMoney;

    @Bind(R.id.pending_pay_money)
    TextView mPendingPayMoney;

    @Bind(R.id.alipay_checker)
    ImageView mAlipayChecker;

    @Bind(R.id.wechat_pay_checker)
    ImageView mWechatChecker;
    @Bind(R.id.reminder_call)
    TextView reminderCall;
    @Bind(R.id.confirm_button)
    Button confirmButton;

    private RechargeParam mParam;

    @Inject
    NewRechargePresenter presenter;

    private String rechargeCode;

    /*支付类型: 0x101支付宝支付; 1，0x110微信支付*/
    private int payType = 0x101;
    /*微信支付API*/
    private IWXAPI api;

    private OnFragmentInteractionListener mListener;

    public RechargeFragment() {
    }


    public static RechargeFragment newInstance(RechargeParam param) {
        RechargeFragment fragment = new RechargeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getParcelable(ARG_PARAM);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void initView() {
        presenter.setRechargeView(this, getActivity());
        mAlipayChecker.getDrawable().setLevel(2);
        mWechatChecker.getDrawable().setLevel(1);
        mRechargeMoney.setText(formatMoney(mParam.total));
        mPendingPayMoney.setText(formatMoney(mParam.needPay));
        confirmButton.setClickable(true);
        String reminderText = getResources().getString(R.string.reminder_second);
        reminderCall.setText(matcherSearchText(getActivity(), reminderText));
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    private static String formatMoney(String money) {
        return String.format(Locale.getDefault(), "%.2f元", Float.parseFloat(money));
    }

    @Override
    protected void initializeInjector() {
        DaggerRechargeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.back_ll, R.id.alipay_method, R.id.wechat_pay_method, R.id.confirm_button, R.id.reminder_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                getActivity().finish();
                break;
            case R.id.reminder_call:
                CallPhoneUtils.dial(getActivity(), this, "4008-262-056", "客服电话");
                break;
            case R.id.alipay_method:
                Log.d(TAG, "onClick: alipay");
                mAlipayChecker.getDrawable().setLevel(2);
                mWechatChecker.getDrawable().setLevel(1);
                payType = 0x101;
                break;
            case R.id.wechat_pay_method:
                Log.d(TAG, "onClick: wechat");
                mAlipayChecker.getDrawable().setLevel(1);
                mWechatChecker.getDrawable().setLevel(2);
                payType = 0x110;
                break;
            case R.id.confirm_button:
                if (!NetUtils.isConnected(getActivity())) {
                    showMessage(getString(R.string.network_error));
                    return;
                }
                if (payType == 0x101) {
                    //发起支付宝支付充值
                    payByAlipay();
                } else if (payType == 0x110) {
                    //发起微信支付
                    payByWX();
                }
                confirmButton.setClickable(false);
                break;
        }
    }

    /**
     * 微信支付
     */
    private void payByWX() {
        Log.d(TAG, "payByWX: ");
        presenter.payByWx(mParam.activityId, mParam.needPay, mParam.total, mParam.activity);
    }


    /**
     * 支付宝支付
     */
    private void payByAlipay() {

        presenter.payByAli(mParam.activityId, mParam.needPay, mParam.total, mParam.activity);

    }

    @Override
    public void onRechargetFail() {

        showMessage(getString(R.string.pay_fail));
        confirmButton.setClickable(true);
    }

    @Override
    public void onRechargeSuccess(String orderId) {
        confirmButton.setClickable(true);
        RechargeSuccActivity.start(getActivity(), orderId);
        ActivityCompat.finishAfterTransition(getActivity());
    }

    @Override
    public void onRechargeEnsure() {
        showMessage(getString(R.string.recharge_result_ensuring));

    }

    @Override
    public void onRechargeCancel() {
        confirmButton.setClickable(true);
        showMessage(getString(R.string.pay_cancel));
    }

    @Override
    public void onAppIdReceive(String appId) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(getActivity(), appId);
            api.registerApp(appId);
            presenter.setWXAPI(api);
        }
    }

    @Override
    public void onError() {
        confirmButton.setClickable(true);
    }

    @Override
    public void returnRechargeCode(String rechargeCode) {
        this.rechargeCode=rechargeCode;
    }


    public static class RechargeParam implements Parcelable {

        String total;
        String needPay;
        String activity;
        String activityId;

        public RechargeParam(String activityId, String needPay, String total, String activity) {
            this.total = total;
            this.needPay = needPay;
            this.activity = activity;
            this.activityId = activityId;
        }


        protected RechargeParam(Parcel in) {
            total = in.readString();
            needPay = in.readString();
            activity = in.readString();
            activityId = in.readString();
        }

        public static final Creator<RechargeParam> CREATOR = new Creator<RechargeParam>() {
            @Override
            public RechargeParam createFromParcel(Parcel in) {
                return new RechargeParam(in);
            }

            @Override
            public RechargeParam[] newArray(int size) {
                return new RechargeParam[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(total);
            dest.writeString(needPay);
            dest.writeString(activity);
            dest.writeString(activityId);
        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    public static SpannableString matcherSearchText(Context context, String text) {
        SpannableString sum = new SpannableString(text);
        int lenth = text.length();
        sum.setSpan(new TextAppearanceSpan(context, R.style.reminder_text_style), lenth - 12, lenth, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sum;
    }


    /*****
     * 微信支付结果的回调
     ******/

    //取消支付，或者支付不成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayNotSuccess(PayAbortEvent event) {
        confirmButton.setClickable(true);
        if (event.msg != null)
            showMessage(event.msg);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(WXSuccessPayEvent event) {
        showMessage("支付成功");
        confirmButton.setClickable(true);
        RechargeSuccActivity.start(getActivity(), rechargeCode);
        ActivityCompat.finishAfterTransition(getActivity());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResult(PayAbortEvent event) {
        if (event.code == 0) {
            getActivity().finish();
        } else {
            showMessage(event.msg);
        }
    }


}

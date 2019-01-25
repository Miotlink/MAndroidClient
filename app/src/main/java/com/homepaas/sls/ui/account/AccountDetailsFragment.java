package com.homepaas.sls.ui.account;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerAccountComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.AccountDetailItemEntry;
import com.homepaas.sls.domain.entity.AccountMessage;
import com.homepaas.sls.mvp.presenter.account.AccountDetailsPresent;
import com.homepaas.sls.mvp.view.account.AccountDetailsView;
import com.homepaas.sls.ui.account.adapter.AccountDetailsAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/28.
 */

public class AccountDetailsFragment extends CommonBaseFragment implements AccountDetailsView, AccountDetailsAdapter.OnItemOnClickListener {

    @Bind(R.id.account_detail_rv)
    RecyclerView recyclerView;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.account_number)
    TextView account_number;
    @Bind(R.id.customer_service_telephone)
    TextView customer_service_telephone;
    @Bind(R.id.back_ll)
    LinearLayout backLl;

    private static final String USER_ID = "user_id";
    private int userId;
    private String orderId;
    AccountDetailsAdapter mAccountDetailsAdapter;

    @Inject
    AccountDetailsPresent accountDetailsPresent;


    public static AccountDetailsFragment newInstance(int id) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_details;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            userId = getArguments().getInt(USER_ID);
        }
        mAccountDetailsAdapter = new AccountDetailsAdapter(getActivity());
        mAccountDetailsAdapter.setOnItemOnClickListener(this);
        int spacingInPixels = 22;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setAdapter(mAccountDetailsAdapter);

        accountDetailsPresent.setAccountDetailsView(this);
        accountDetailsPresent.getAccountDetails(userId);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.customer_service_telephone, R.id.back_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.customer_service_telephone:
                dial(getString(R.string.service_phone_number), "客服电话");
                break;
            case R.id.back_ll:
                getActivity().finish();
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


    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private String mCallingPhone;
    private String mTitle;
    // 拨打电话

    private void dial(String phone, String title) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        }
    }

    @Override
    public void renderAccountDetails(AccountDetailItemEntry accountDetailItemEntry) {

        List<AccountMessage> list = new ArrayList<AccountMessage>();
        int id = accountDetailItemEntry.getId();
        String tradeType = accountDetailItemEntry.getTradeType();
        String settlementType = accountDetailItemEntry.getSettlementType();
        String tradeName = accountDetailItemEntry.getTradeName();
        String tradeTypeId = accountDetailItemEntry.getTradeTypeId();
        String settlementTime = accountDetailItemEntry.getSettlementTime();
        String settlementAmount = accountDetailItemEntry.getSettlementAmount();
        String statusNote = accountDetailItemEntry.getStatusNote();
        String isMinus = accountDetailItemEntry.getIsMinus();
        String tradeNo = accountDetailItemEntry.getTradeNo();
        String tradeAmount = accountDetailItemEntry.getTradeAmount();
        String tradeNote = accountDetailItemEntry.getTradeNote();
        String billNumber = accountDetailItemEntry.getBillNumber();
        String paymentMode = accountDetailItemEntry.getPaymentMode();
        orderId = accountDetailItemEntry.getOrderId();
        String discountAmount = accountDetailItemEntry.getDiscountAmount();

        if (!TextUtils.isEmpty(tradeName)) {
            AccountMessage tradeName_detail = new AccountMessage("交易说明", tradeName);
            list.add(tradeName_detail);
        }
        if (!TextUtils.isEmpty(paymentMode)) {
            AccountMessage paymentMode_detail = new AccountMessage("支付方式", paymentMode);
            list.add(paymentMode_detail);
        }
        if (!TextUtils.isEmpty(tradeTypeId)) {
            if (TextUtils.equals(tradeTypeId, "50")) {
                if (!TextUtils.isEmpty(tradeNote)) {
                    AccountMessage tradeNote_detail = new AccountMessage("充值优惠", tradeNote);
                    list.add(tradeNote_detail);
                }
            } else if (TextUtils.equals(tradeTypeId, "60")) {
                if (!TextUtils.isEmpty(tradeNote)) {
                    AccountMessage tradeNote_detail = new AccountMessage("推荐用户", tradeNote);
                    list.add(tradeNote_detail);
                }
            }
        }

        if (!TextUtils.isEmpty(tradeAmount)) {
            if (!TextUtils.isEmpty(tradeTypeId)) {
                if (TextUtils.equals(tradeTypeId, "50")) {
                    AccountMessage tradeAmount_detail = new AccountMessage("支付金额", tradeAmount);
                    list.add(tradeAmount_detail);
                } else {
                    if (!TextUtils.isEmpty(discountAmount)) {
                        AccountMessage tradeAmount_detail = new AccountMessage("订单金额", (Double.parseDouble(tradeAmount) + Double.parseDouble(discountAmount)) + "");
                        list.add(tradeAmount_detail);
                    } else {
                        AccountMessage tradeAmount_detail = new AccountMessage("订单金额", tradeAmount);
                        list.add(tradeAmount_detail);
                    }
                }
            }
        }


        if (!TextUtils.isEmpty(discountAmount)) {
            AccountMessage discountAmount_detail = new AccountMessage("订单优惠", "-" + discountAmount);
            list.add(discountAmount_detail);
        }

        if (!TextUtils.isEmpty(settlementTime)) {
            AccountMessage settlementTime_detail = new AccountMessage("创建时间", TimeUtils.formatDateByLine(settlementTime));
            list.add(settlementTime_detail);
        }
        if (!TextUtils.isEmpty(tradeNo)) {
            AccountMessage tradeNo_detail = new AccountMessage("订单号", tradeNo);
            list.add(tradeNo_detail);
        }
        if (!TextUtils.isEmpty(billNumber)) {
            AccountMessage billNumber_detail = new AccountMessage("交易单号", billNumber);
            list.add(billNumber_detail);
        }
        if (!TextUtils.isEmpty(settlementAmount)) {
            if (settlementAmount.equals("0.00")) {
                account_number.setText(settlementAmount);
            } else {
                if (isMinus.equals("False")) {
                    account_number.setText("+" + settlementAmount);
                } else {
                    account_number.setText("-" + settlementAmount);
                }
            }
        }
        mAccountDetailsAdapter.setOrderId(orderId);
        mAccountDetailsAdapter.setDatas(list);

    }

    @Override
    public void onItemClick() {
        if (!TextUtils.isEmpty(orderId)) {
            ClientOrderDetailActivity.start(getActivity(), orderId);
        }
    }
}

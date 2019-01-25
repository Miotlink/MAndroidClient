package com.homepaas.sls.ui.newdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerBusWorkerServiceComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.GetAtStoreActivityEntity;
import com.homepaas.sls.mvp.presenter.newdetail.PayinStoreActivityPresent;
import com.homepaas.sls.mvp.view.newdetail.GetAtStoreActivityView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.newdetail.adapter.ActionActRuleAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.homepaas.sls.util.StaticData;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/2/7.
 */

public class PayInStoreActivity extends CommonBaseActivity implements GetAtStoreActivityView {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.photo)
    RoundedImageView photo;
    @Bind(R.id.merchant_name)
    TextView merchantName;
    @Bind(R.id.merchant_address)
    TextView merchantAddress;
    @Bind(R.id.pay_money_edit)
    EditText payMoneyEdit;
    @Bind(R.id.action_collection)
    RecyclerView actionCollection;
    @Bind(R.id.confirm_button)
    Button confirmButton;
    @Bind(R.id.last_need_pay)
    TextView lastNeedPay;
    @Bind(R.id.money_symbol)
    TextView moneySymbol;
    private String merchantId;
    private String merchantNameStr;
    private String merchantAddressStr;
    private String photoUrlStr;
    private double needPayMoney = 0.0;
    private String activityNgIdStr="";
    private ActionActRuleAdapter actionActRuleAdapter;
    private GetAtStoreActivityEntity getAtStoreActivityEntity;

    @Inject
    PayinStoreActivityPresent payinStoreActivityPresent;
    private static final int UPDATE_PRICE = 99;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PRICE:
                    if (!TextUtils.isEmpty(payMoneyEdit.getText().toString())) {
                        if (getAtStoreActivityEntity != null && getAtStoreActivityEntity.getActivityNgDetailList() != null && !getAtStoreActivityEntity.getActivityNgDetailList().isEmpty()) {
                            calculatePrice(payMoneyEdit.getText().toString());
                        } else {
                            lastNeedPay.setText("¥" + payMoneyEdit.getText().toString());
                            activityNgIdStr = "";
                            needPayMoney = Double.valueOf(payMoneyEdit.getText().toString());
                        }
                    } else {
                        lastNeedPay.setText("");
                        activityNgIdStr = "";
                        if (getAtStoreActivityEntity != null && getAtStoreActivityEntity.getActivityNgDetailList() != null && !getAtStoreActivityEntity.getActivityNgDetailList().isEmpty()) {
                            calculatePrice("0.00");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_in_store;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        merchantId = getIntent().getStringExtra(StaticData.MERCHANT_SERVICE_ID);
        merchantNameStr = getIntent().getStringExtra(StaticData.MERCHANT_NAME);
        merchantAddressStr = getIntent().getStringExtra(StaticData.MERCHANT_ADDRESS);
        photoUrlStr = getIntent().getStringExtra(StaticData.MERCHANT_PHOTO_URL);
        if (!TextUtils.isEmpty(photoUrlStr)) {
            Glide.with(this)
                    .load(photoUrlStr)
                    .placeholder(R.mipmap.default_image)
                    .into(new ImageTarget(photo));
        }
        merchantName.setText(merchantNameStr);
        merchantAddress.setText(merchantAddressStr);
        actionActRuleAdapter = new ActionActRuleAdapter(this);
        actionCollection.setAdapter(actionActRuleAdapter);
        payinStoreActivityPresent.setGetAtStoreActivityView(this);
        payinStoreActivityPresent.getGetAtStoreActivity(merchantId);
        confirmButton.setEnabled(false);
        payMoneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!payMoneyEdit.getText().toString().isEmpty()) {
                    confirmButton.setEnabled(true);
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(UPDATE_PRICE, 500);
                    } else {
                        mHandler.sendEmptyMessageDelayed(UPDATE_PRICE, 500);
                    }
                }else{
                    lastNeedPay.setText("");
                    activityNgIdStr = "";
                    if (getAtStoreActivityEntity != null && getAtStoreActivityEntity.getActivityNgDetailList() != null && !getAtStoreActivityEntity.getActivityNgDetailList().isEmpty()) {
                        calculatePrice("0.00");
                    }
                    confirmButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

    }


    public static void start(Context context, String id, String merchantName, String merchantAddress, String photoUrl) {
        Intent intent = new Intent(context, PayInStoreActivity.class);
        intent.putExtra(StaticData.MERCHANT_SERVICE_ID, id);
        intent.putExtra(StaticData.MERCHANT_NAME, merchantName);
        intent.putExtra(StaticData.MERCHANT_ADDRESS, merchantAddress);
        intent.putExtra(StaticData.MERCHANT_PHOTO_URL, photoUrl);
        context.startActivity(intent);

    }

    @Override
    protected void initializeInjector() {
        DaggerBusWorkerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build().inject(this);
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

    @OnClick({R.id.confirm_button})
    void onClick() {
        if (!TextUtils.isEmpty(payMoneyEdit.getText().toString())) {
            PayOrderActivity.start(this, merchantId, Double.valueOf(payMoneyEdit.getText().toString()), needPayMoney, activityNgIdStr, merchantNameStr);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(PayInStoreActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(GetAtStoreActivityEntity getAtStoreActivityEntity) {
        this.getAtStoreActivityEntity = getAtStoreActivityEntity;
        if (getAtStoreActivityEntity != null && getAtStoreActivityEntity.getActivityNgDetailList() != null && !getAtStoreActivityEntity.getActivityNgDetailList().isEmpty()) {
            actionCollection.setVisibility(View.VISIBLE);
            actionActRuleAdapter.setList(getAtStoreActivityEntity.getActivityNgDetailList());
        } else {
            actionCollection.setVisibility(View.GONE);
        }
    }


    /**
     * @param inputMoney 输入的价格
     */
    public void calculatePrice(String inputMoney) {
        activityNgIdStr="";
        BigDecimal inputMoneyDecimal = new BigDecimal(inputMoney).setScale(2, RoundingMode.HALF_UP);
        BigDecimal reduceDecimal = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
        List<GetAtStoreActivityEntity.ActivityNgDetail> activityNgDetailList = getAtStoreActivityEntity.getBestActivity(Float.valueOf(inputMoney));
        for (GetAtStoreActivityEntity.ActivityNgDetail activityNgDetail : activityNgDetailList) {
            if(TextUtils.equals(activityNgIdStr,"")){
                activityNgIdStr=activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getAcitivityNgId();
            }else {
                activityNgIdStr = activityNgIdStr + "," + activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getAcitivityNgId();
            }
            if (!TextUtils.isEmpty(activityNgDetail.getReturnType()) && TextUtils.equals(activityNgDetail.getReturnType(), "0") && !TextUtils.isEmpty(activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus())) {
                BigDecimal addDecimal = new BigDecimal(activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus()).setScale(2, RoundingMode.HALF_UP);
                reduceDecimal = reduceDecimal.add(addDecimal);
            }
        }
        if(!activityNgDetailList.isEmpty()) {
            actionActRuleAdapter.setList(activityNgDetailList);
        }else{
            actionActRuleAdapter.setList(getAtStoreActivityEntity.getActivityNgDetailList());
        }
        if(!TextUtils.isEmpty(inputMoney)&&TextUtils.equals(inputMoney,"0.00")){
            lastNeedPay.setText("");
        }else {
            lastNeedPay.setText("¥" + inputMoneyDecimal.subtract(reduceDecimal).doubleValue());
        }
        needPayMoney = inputMoneyDecimal.subtract(reduceDecimal).doubleValue();
    }
}

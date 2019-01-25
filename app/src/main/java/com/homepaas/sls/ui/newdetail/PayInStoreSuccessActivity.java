package com.homepaas.sls.ui.newdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.util.StaticData;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/2/21.
 */

public class PayInStoreSuccessActivity extends CommonBaseActivity {
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.pay_sum)
    TextView paySum;
    @Bind(R.id.merchant_name)
    TextView merchantName;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.pay_time)
    TextView payTime;
    @Bind(R.id.main_page)
    Button mainPage;

    private static final String PAY_MONEY="pay_monty";
    private static final String PAY_TIME="pay_time";
    private String payMoneyStr;
    private String merchantNameStr;
    private String payTimeStr;

    public static void start(Context context,String payMoneyStr,String merchantNameStr,String payTimeStr){
        Intent intent=new Intent(context,PayInStoreSuccessActivity.class);
        intent.putExtra(PAY_MONEY,payMoneyStr);
        intent.putExtra(StaticData.MERCHANT_NAME,merchantNameStr);
        intent.putExtra(PAY_TIME,payTimeStr);
        context.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_in_store_success;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        payMoneyStr=getIntent().getStringExtra(PAY_MONEY);
        merchantNameStr=getIntent().getStringExtra(StaticData.MERCHANT_NAME);
        payTimeStr=getIntent().getStringExtra(PAY_TIME);
        paySum.setText("¥"+payMoneyStr);
        merchantName.setText(merchantNameStr);
        payTime.setText(payTimeStr);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.main_page})
    void onClick(){
        Intent intent =new Intent(this, MainActivity.class);
        intent.putExtra(StaticData.RETURN_MAP_FRAGMENT,"0");
        startActivity(intent);
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent =new Intent(this, MainActivity.class);
                intent.putExtra(StaticData.RETURN_MAP_FRAGMENT,"0");
                startActivity(intent);
                ActivityCompat.finishAfterTransition(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.homepaas.sls.ui.newdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.MerchantServiceTypePrice;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.BusinessOrderActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.homepaas.sls.util.StaticData;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/2/9.
 */

public class ServiceDetailPayActivity extends CommonBaseActivity {
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.photo_image)
    RoundedImageView photoImage;
    @Bind(R.id.service_type)
    TextView serviceType;
    @Bind(R.id.service_name)
    TextView serviceName;
    @Bind(R.id.service_price)
    TextView servicePrice;
    @Bind(R.id.service_content)
    TextView serviceContent;
    @Bind(R.id.purchase_btn)
    Button purchaseBtn;
    @Bind(R.id.content_lin)
    LinearLayout contentLin;
    @Bind(R.id.contianer)
    FrameLayout contianer;

    private static final String TAG = "ServiceDetailPayActivity";
    private static final String MERCHANT_TYPE_PRICE = "mechantTypePrice";
    private static final String SERVICE_TYPE = "service_type";
    private MerchantServiceTypePrice merchantServiceTypePrice;
    private String serviceTypeStr;
    private String merchantId;


    public static void start(Context context, String serviceType, MerchantServiceTypePrice merchantServiceTypePrice, String id) {
        Intent intent = new Intent(context, ServiceDetailPayActivity.class);
        intent.putExtra(SERVICE_TYPE, serviceType);
        intent.putExtra(MERCHANT_TYPE_PRICE, merchantServiceTypePrice);
        intent.putExtra(StaticData.MERCHANT_SERVICE_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail_pay;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        merchantServiceTypePrice = (MerchantServiceTypePrice) getIntent().getSerializableExtra(MERCHANT_TYPE_PRICE);
        serviceTypeStr = getIntent().getStringExtra(SERVICE_TYPE);
        merchantId = getIntent().getStringExtra(StaticData.MERCHANT_SERVICE_ID);
        if (merchantServiceTypePrice != null) {
            Glide.with(this).load(merchantServiceTypePrice.getIcon()).placeholder(R.mipmap.default_image)
                    .into(new ImageTarget(photoImage));
            serviceName.setText(merchantServiceTypePrice.getName());
            servicePrice.setText(merchantServiceTypePrice.getPrice());
            if (TextUtils.isEmpty(merchantServiceTypePrice.getDescription())) {
                contentLin.setVisibility(View.GONE);
            } else {
                contentLin.setVisibility(View.VISIBLE);
                serviceContent.setText(merchantServiceTypePrice.getDescription());
            }
            if (!TextUtils.isEmpty(serviceTypeStr)) {
                serviceType.setText(serviceTypeStr);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.photo_image, R.id.purchase_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_image:
                String photoUrl = "";
                if (merchantServiceTypePrice != null) {
                    if (!TextUtils.isEmpty(merchantServiceTypePrice.getIcon())) {
                        photoUrl = merchantServiceTypePrice.getIcon();
                    }
                }
                GallerySingleFragment fragment = GallerySingleFragment.newInstance(photoUrl);
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(TAG)
                        .add(R.id.contianer, fragment, TAG)
                        .commit();
                break;
            case R.id.purchase_btn:
                BusinessOrderActivity.start(this, Constant.SERVICE_TYPE_BUSINESS_INT, merchantId, merchantServiceTypePrice);
                break;
            default:
        }
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
}

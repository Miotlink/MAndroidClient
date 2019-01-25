package com.homepaas.sls.ui.redpacket.newRedpacket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.network.Url;
import com.homepaas.sls.di.component.DaggerCouponContentsComponent;
import com.homepaas.sls.di.module.CouponContentsModule;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.mvp.presenter.coupon.CouponContentPresenter;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.personalcenter.more.GeneralWebViewActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.newRedPacketAdapter.NewRedPacketItemAdapter;
import com.homepaas.sls.ui.widget.BaseListActivity;
import com.homepaas.sls.ui.widget.BlankDecoration;
import com.homepaas.sls.util.StaticData;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UseNewRedPacketActivity extends BaseListActivity<CouponContents> implements CouponContentsView, NewRedPacketItemAdapter.canUseListener {

    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.select)
    ImageView select;
    @Bind(R.id.aviable_count)
    TextView aviableCount;
    @Bind(R.id.activity_use_new_red_packet)
    LinearLayout activityUseNewRedPacket;

    @Inject
    CouponContentPresenter couponContentPresenter;
    private NewRedPacketItemAdapter newRedPacketItemAdapter;
    private String totalMoney;
    private boolean isNotUseCoupon;
    private String lastSelectedCouponId;
    private String serviceId;
    private boolean isPayCoupon;
    private String latitude;
    private String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setMoreLoadable(false);
        setEmptyView(R.mipmap.kong, "您当前没有红包哦~", null);
        Intent intent = getIntent();
        totalMoney = intent.getStringExtra(StaticData.TOTAL_MONEY);
        isNotUseCoupon = intent.getBooleanExtra(StaticData.IS_NOT_USE_COUPON, false);
        lastSelectedCouponId = intent.getStringExtra(StaticData.COUPON_ID);
        serviceId=intent.getStringExtra(StaticData.SERVICE_ID);
        isPayCoupon = intent.getBooleanExtra(StaticData.IS_PAY_COUPON,false);
        latitude=intent.getStringExtra(StaticData.LATITUDE);
        longitude=intent.getStringExtra(StaticData.LONGITUDE);
        if (isNotUseCoupon){
            select.setSelected(true);
        } else {
            select.setSelected(false);
        }
        couponContentPresenter.setCouponContentsView(this);
        if (totalMoney != null){
            couponContentPresenter.getCouponList(3, totalMoney,serviceId,longitude,latitude,isPayCoupon);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        couponContentPresenter.destroy();
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerCouponContentsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponContentsModule(new CouponContentsModule())
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coupon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.coupon_about) {
            Intent intent = new Intent(this, GeneralWebViewActivity.class);
            intent.putExtra(Navigator.WEB_VIEW_TITLE, "红包说明");
            intent.putExtra(Navigator.WEB_VIEW_URL, Url.HTM_BASE_URL_DEFAULT + "yhqsm.html");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentRes() {
        return R.layout.activity_use_new_red_packet;
    }

    @OnClick(R.id.select)
   public void chose(){
//        if (!isNotUseCoupon){
//            select.setSelected(false);
//        } else {
//            select.setSelected(true);
//            //还要回传使用红包与否信息
            if (totalMoney == null) return;//从个人中心我的优惠券进入的情况totalmoney为空，不做任何处理
        Intent data = new Intent();
        data.putExtra("ResultMoney", "");
        data.putExtra("DiscountMoney", "");
        data.putExtra("CouponId", "");
        data.putExtra("IsNotUseCoupon",true);
        setResult(Activity.RESULT_OK, data);
        this.finish();
//
//        }
    }
    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new BlankDecoration(getContext());
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<CouponContents> list) {
        newRedPacketItemAdapter = new NewRedPacketItemAdapter(list,3);
        newRedPacketItemAdapter.setLastSelectedCouponId(lastSelectedCouponId,isNotUseCoupon);
        newRedPacketItemAdapter.setCanUseListener(this);
        return newRedPacketItemAdapter;
    }

    @Override
    public void onRefresh() {
        if (totalMoney != null){
            couponContentPresenter.getCouponList(3, totalMoney,serviceId,null,null,isPayCoupon);
        }
    }
    public SpannableStringBuilder matcherSearchText(int color, String text, String keyword) {
        SpannableStringBuilder ss = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
    @Override
    public void renderCount(int count) {
        String text = "有"+count+"个红包可使用";
        if (count <= 0){
            select.setSelected(true);
            aviableCount.setVisibility(View.GONE);
        } else {
            if (isNotUseCoupon){
                select.setSelected(true);
            } else {
                select.setSelected(false);
            }
            aviableCount.setVisibility(View.VISIBLE);
            aviableCount.setText(matcherSearchText(Color.parseColor("#FF1B56"), text, Integer.toString(count)));
        }
    }

    @Override
    public void select(CouponContents couponContents) {
        select.setSelected(false);
        if (totalMoney == null) return;//从个人中心我的优惠券进入的情况totalmoney为空，不做任何处理
        Double discountMoney = 0.0;
        Double serviceMoney = Double.valueOf(totalMoney);
        Double resultMoney = serviceMoney;
        Double amountMonty=0.0;
        if (TextUtils.equals(couponContents.getCouponDetailses().get(0).getDiscountType(), Constant.DiscountTypeDiscount)){
            discountMoney = Double.valueOf(totalMoney)*Double.valueOf(couponContents.getCouponDetailses().get(0).getDiscount());
            resultMoney = serviceMoney - discountMoney;
        } else {
            if(!TextUtils.isEmpty(couponContents.getCouponDetailses().get(0).getDiscountAmount())) {
                discountMoney = Double.valueOf(couponContents.getCouponDetailses().get(0).getDiscountAmount());
            }
            if(!TextUtils.isEmpty(couponContents.getCouponDetailses().get(0).getAmount())) {
                amountMonty = Double.valueOf(couponContents.getCouponDetailses().get(0).getAmount());
            }
            resultMoney = serviceMoney - discountMoney;
        }
        Intent data = new Intent();
        data.putExtra("ResultMoney", String.valueOf(new DecimalFormat("#.00").format(resultMoney)));
        data.putExtra("DiscountMoney", String.valueOf(new DecimalFormat("#.00").format(discountMoney)));
        data.putExtra("AmountMoney", amountMonty <= 0 ? "0" : String.valueOf(new DecimalFormat("#.00").format(amountMonty)));
        data.putExtra("CouponId", couponContents.getId());
        data.putExtra("CouponContents",couponContents);
        setResult(Activity.RESULT_OK, data);
        this.finish();
    }
}

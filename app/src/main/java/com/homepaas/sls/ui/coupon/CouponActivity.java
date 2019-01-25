package com.homepaas.sls.ui.coupon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.homepaas.sls.R;
import com.homepaas.sls.data.network.Url;
import com.homepaas.sls.di.component.DaggerCouponContentsComponent;
import com.homepaas.sls.di.module.CouponContentsModule;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.mvp.presenter.coupon.CouponContentPresenter;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;
import com.homepaas.sls.mvp.view.share.ShareView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.order.CouponUtils;
import com.homepaas.sls.ui.personalcenter.more.GeneralWebViewActivity;
import com.homepaas.sls.ui.widget.BaseListActivity;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/11.
 */
public class CouponActivity extends BaseListActivity<CouponContents> implements CouponContentsView, CouponAdapter.OnCoupClickListener, ShareView, UMShareListener {

    private static final String TAG = "RED_ACT";

    private List<CouponContents> datas;

    //    private CouponAdapter adapter;
    @Inject
    CouponContentPresenter presenter;

    public static final String PACKET_ALL = "1";//所有红包
    public static final String PACKET_SPENDABLE = "0";//可使用红包
    private String totalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coupon);
//        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
//        mockData();


        setTitle("我的红包");
        setEmptyView(R.layout.activity_my_coupon_empty_view);

        setMoreLoadable(false);
        presenter.setCouponContentsView(this);
        presenter.getCouponList();
        totalMoney = getIntent().getStringExtra("TotalMoney");
    }

    @Override
    protected int getContentRes() {
        return R.layout.activity_coupon;
    }

    @OnClick({R.id.coupon})
    public void share(){
       startActivity(new Intent(this,ShareCouponActivity.class));
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerCouponContentsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponContentsModule(new CouponContentsModule())
                .build()
                .inject(this);
//        DaggerRedEnvelopeComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .redEnvelopeModule(new RedEnvelopeModule())
//                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void render(@Nullable List<CouponContents> list) {
        if (totalMoney!=null)
        CouponUtils.sortValidCoupons(totalMoney,list);
        super.render(list);
    }

    @Override
    public void renderCount(int count) {

    }

    //    private void mockData() {
////        List<Object> datas = new ArrayList<>();
////        for (int i = 0; i < 10; i++) {
////            datas.add(new Object());
////        }
//        datas = new ArrayList<>();
//        adapter = new CouponAdapter(this,datas);
//        recyclerView.setAdapter(adapter);
//    }

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
        if (item.getItemId() == R.id.coupon_about){
            Intent intent = new Intent(this,GeneralWebViewActivity.class);
            intent.putExtra(Navigator.WEB_VIEW_TITLE,"红包说明");
            intent.putExtra(Navigator.WEB_VIEW_URL, Url.HTM_BASE_URL_DEFAULT+"yhqsm.html");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public void render(List<CouponContents> couponContentses) {
//        if (!couponContentses.isEmpty()) {
//            CouponAdapter adapter = (CouponAdapter) recyclerView.getAdapter();
//            //优惠券排序
//            List<CouponContents> results = CouponUtils.sortCoupons(couponContentses);
//            if (adapter == null) {
//                adapter = new CouponAdapter(this, results);
//                recyclerView.setAdapter(adapter);
//                adapter.setOnCoupClickListener(this);
//            } else {
//                adapter.setDatas(results);
//            }
//        }
//        if (couponContentses.isEmpty())
//            emptyView.setVisibility(View.VISIBLE);
//        else{
//            emptyView.setVisibility(View.GONE);
//        }
//
//    }

    @Override
    public RecyclerView.Adapter initAdapter(List<CouponContents> list) {
        CouponAdapter adapter =  new CouponAdapter(this, list);
        adapter.setOnCoupClickListener(this);
        return adapter;
    }


    /**
     * 点击有效可用的优惠券后回调
     *
     * @param item ..
     */
    @Override
    public void onItemClick(CouponContents item) {
        if (totalMoney == null) return;//从个人中心我的优惠券进入的情况totalmoney为空，不做任何处理
        Double discountMoney = 0.0;
        Double serviceMoney = Double.valueOf(totalMoney);
        Double resultMoney = serviceMoney;
        Double tempMoney;
        String timestamp = String.valueOf(System.currentTimeMillis());
        if (item.getStartTime().compareTo(timestamp)<0&&item.getEndTime().compareTo(timestamp)>0);
        else {
            showMessage("红包已过期");
            return;
        }
        if (TextUtils.equals("1",item.getIsUsed())){
            showMessage("红包已使用");
            return;
        }
        List<CouponContents.CouponDetails> coupons = item.getCouponDetailses();
        int size = coupons.size();
        for (int i = 0; i < size; i++) {
            CouponContents.CouponDetails coupon = coupons.get(i);
            Double amount = Double.valueOf(coupon.getAmount());
            Double discounAmount = Double.valueOf(coupon.getDiscountAmount());
            if (serviceMoney < amount)
                continue;
            else {
                tempMoney = serviceMoney - discounAmount;
                if (tempMoney < resultMoney) {
                    resultMoney = tempMoney;
                }
            }
        }
        if (resultMoney == serviceMoney)//优惠券无法使用
        {
            showMessage("未达到减免金额，无法使用该红包");
        } else {

            if (Double.valueOf(resultMoney) <= 0) {
                resultMoney = 1.0;
                discountMoney = serviceMoney - 1.0;
            } else {
                discountMoney = serviceMoney - resultMoney;
            }
            Intent data = new Intent();
            data.putExtra("ResultMoney", String.valueOf(new DecimalFormat("#.00").format(resultMoney)));
            data.putExtra("DiscountMoney", String.valueOf(new DecimalFormat("#.00").format(discountMoney)));
            data.putExtra("CouponId", item.getId());
            setResult(Activity.RESULT_OK, data);
            this.finish();
        }
    }

    @Override
    public void onRefresh() {
        presenter.getCouponList();
    }

    /**
     * 获取优惠券回调
     * @param shareInfo
     */
    @Override
    public void onShareResult(ShareInfo shareInfo) {
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.setUrl(shareInfo.getUrl());
        shareDialog.setImage(new UMImage(this,shareInfo.getPicture()));
        shareDialog.setText(shareInfo.getDescription());
        shareDialog.setTitle(shareInfo.getTitle());
        shareDialog.setUmShareListener(this);
        shareDialog.show();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }

    @Override
    protected void retrieveData() {
       presenter.getCouponList();
    }
}

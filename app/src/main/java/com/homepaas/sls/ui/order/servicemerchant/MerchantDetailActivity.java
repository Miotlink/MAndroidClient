package com.homepaas.sls.ui.order.servicemerchant;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.newmvp.contract.MerchantContract;
import com.homepaas.sls.newmvp.presenter.MerchantPresenter;
import com.homepaas.sls.ui.common.activity.CommonMvpBaseActivity;
import com.homepaas.sls.ui.order.manage.OrderManageAdapter;
import com.homepaas.sls.ui.widget.MerchantDetailToolbar;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.StatusBarUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runtimepermission.acp.AcpListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mhy on 2017/12/26.
 * 商家详情
 */

public class MerchantDetailActivity extends CommonMvpBaseActivity<MerchantContract.Presenter> implements MerchantContract.View {

    @Bind(R.id.indicator)
    TabLayout indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.bar_view)
    View barView;
    @Bind(R.id.rl_merchant_icon)
    RoundedImageView rlMerchantIcon;
    @Bind(R.id.tv_merchant_name)
    TextView tvMerchantName;
    @Bind(R.id.rb_service_grade)
    RatingBar rbServiceGrade;
    @Bind(R.id.tv_grade)
    TextView tvGrade;
    @Bind(R.id.tv_service_time)
    TextView tvServiceTime;
    @Bind(R.id.call_button)
    ImageView callButton;
    @Bind(R.id.toolbar)
    MerchantDetailToolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    private String userType;//必填，用户类型，2：工人，3：商户
    private String merchantOrWorkerId;//必填，商户或者普通工人的Id
    private OrderManageAdapter adapter;
    private List<String> titleList = new ArrayList<>();
    private BusinessExInfoOutput businessExInfoOutput;
    private MerchantDetailMerchantFragment merchantDetailMerchantFragment;
    private MerchantDetailServiceFragment merchantDetailServiceFragment;
    private MerchantDetailLevalUateFragment merchantDetailLevalUateFragment;

    public static void start(Context context, String userType, String merchantOrWorkerId, boolean isOrderActivity, String orderType, String secondLevel, double latitude,double
            longitude) {
        Intent starter = new Intent(context, MerchantDetailActivity.class);
        starter.putExtra("userType", userType);
        starter.putExtra("merchantOrWorkerId", merchantOrWorkerId);
        starter.putExtra(StaticData.IS_ORDER_ACTIVITY, isOrderActivity);
        starter.putExtra(StaticData.ORDER_TYPE, orderType);
        starter.putExtra(StaticData.SECOND_LEVEL, secondLevel);
        starter.putExtra(StaticData.LATITUDE, latitude);
        starter.putExtra(StaticData.LONGITUDE, longitude);
        context.startActivity(starter);
    }

    @Override
    protected MerchantContract.Presenter getPresenter() {
        return new MerchantPresenter();
    }

    @Override
    public void setStateBarColor() {
        super.setStateBarColor();
        StatusBarUtil.transparencyBar(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_merchant_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        merchantOrWorkerId = intent.getStringExtra("merchantOrWorkerId");
        boolean isOrderActivity = intent.getBooleanExtra(StaticData.IS_ORDER_ACTIVITY, false);
        String orderType = intent.getStringExtra(StaticData.ORDER_TYPE);
        String secondLevel = intent.getStringExtra(StaticData.SECOND_LEVEL);

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //折叠时你想要的颜
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.trans));
        //展开始颜色
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() + verticalOffset <= 20) {
                    if (businessExInfoOutput == null)
                        return;
                    toolbarLayout.setTitle(TextUtils.isEmpty(businessExInfoOutput.getName()) ? "" : businessExInfoOutput.getName());
                    toolbar.setTitle(R.string.space);
                    isShow = true;
                } else if (isShow) {
                    toolbarLayout.setTitle("  ");
                    isShow = false;
                }
            }
        });

        merchantDetailServiceFragment = MerchantDetailServiceFragment.newInstance(userType, merchantOrWorkerId, isOrderActivity, orderType, secondLevel,
                intent.getDoubleExtra(StaticData.LATITUDE, 0), intent.getDoubleExtra(StaticData.LONGITUDE, 0));
        merchantDetailLevalUateFragment = MerchantDetailLevalUateFragment.newInstance(userType, merchantOrWorkerId);
        merchantDetailMerchantFragment = MerchantDetailMerchantFragment.newInstance();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, StatusBarUtil.getStatusBarHeight(mContext));
        barView.setLayoutParams(params);
        List<Fragment> fragments = new ArrayList<>();
        titleList.add("服务");
        fragments.add(merchantDetailServiceFragment);
        titleList.add("评价");
        fragments.add(merchantDetailLevalUateFragment);
        titleList.add("商家");
        fragments.add(merchantDetailMerchantFragment);
        adapter = new OrderManageAdapter(getSupportFragmentManager(), fragments, titleList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        indicator.setupWithViewPager(viewpager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
        mPresenter.getMerchantOrWorkerInfo(userType, merchantOrWorkerId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.merchant_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.more:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void initMerchantOrWorkerInfo(BusinessExInfoOutput businessExInfoOutput) {
        if (businessExInfoOutput == null)
            return;
        this.businessExInfoOutput = businessExInfoOutput;
        if (!TextUtils.isEmpty(businessExInfoOutput.getAvatarUrl()))
            Glide.with(mContext).load(businessExInfoOutput.getAvatarUrl())
                    .fitCenter()
                    .placeholder(R.mipmap.default_image)
                    .error(R.mipmap.default_image)
                    .into(rlMerchantIcon);
        if (!TextUtils.isEmpty(businessExInfoOutput.getName()))
            tvMerchantName.setText(businessExInfoOutput.getName());
        rbServiceGrade.setRating(businessExInfoOutput.getGrade());
        tvGrade.setText(businessExInfoOutput.getGrade() + "");
        if (!TextUtils.isEmpty(businessExInfoOutput.getServiceTime()))
            tvServiceTime.setText(businessExInfoOutput.getServiceTime());
        if (merchantDetailMerchantFragment != null)
            merchantDetailMerchantFragment.setRlMerchantOrWorkerDetail(businessExInfoOutput);
        if (merchantDetailServiceFragment != null)
            merchantDetailServiceFragment.setMerchantOrWorkerNameAndUserId(businessExInfoOutput.getName(), businessExInfoOutput.getUserId());
    }

    @OnClick(R.id.call_button)
    public void onViewClicked() {
        if (businessExInfoOutput == null)
            return;
        // 拨打电话
        PermissionUtils.callPhonePermission(mContext, new AcpListener() {
            @Override
            public void onGranted() {
                NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(businessExInfoOutput.getPhoneNumber(), "拨打商家电话");
                serviceFragment.show(getSupportFragmentManager(), null);
            }

            @Override
            public void onDenied(List<String> permissions) {
                showMessage("拨打电话需要权限,请到设置中心");
            }
        });
    }

    @Override
    public void initBusinessOrderServiceList(BusinessOrderServiceListEntity businessOrderServiceListEntity) {

    }

    @Override
    public void initBusinessSecService(BusinessSecServiceEntity businessSecServiceEntity) {

    }

    @Override
    public void initBusinessServiceList(BusinessServiceListEntity businessServiceListEntity) {

    }

    @Override
    public void initBusinessCommentList(BusinessCommentListOutput businessCommentListOutput) {

    }
}

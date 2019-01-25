package com.homepaas.sls.ui.coupon;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerShareComponent;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.mvp.presenter.share.SharePresenter;
import com.homepaas.sls.mvp.view.share.ShareView;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.umeng.socialize.media.UMImage;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by CJJ on 2016/7/12.
 */
public class ShareCouponActivity extends CommonBaseActivity implements ShareView {

    @Inject
    SharePresenter sharePresenter;
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.share)
    TextView share;
    private ShareInfo sharInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_coupon;
    }

    @Override
    protected void initView() {
        sharePresenter.setShareView(this);
        sharePresenter.getShareCoupon();
        init();
    }

    @Override
    protected void initData() {

    }

    private void init() {

/*        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("您有10张优惠券");
        int colorSpan = Color.parseColor("#32BEFF");
//        SpannableString spannableString = new SpannableString("您有10张优惠券");
        int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());
        stringBuilder.setSpan(new AbsoluteSizeSpan(v),2,4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        stringBuilder.setSpan(colorSpan,2,4,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);*/
        description.setText(Html.fromHtml("您有<font color='#32BEFF'><big><big>10</big></big></font>张优惠券可与好友分享"));
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerShareComponent.builder().applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    /**
     * 分享信息回调
     * @param shareInfo
     */
    @Override
    public void onShareResult(ShareInfo shareInfo) {
//        description.setText(shareInfo.getDescription());
        sharInfo = shareInfo;
    }


    @OnClick({R.id.share})
    public void shareCoupon(){
        if (sharInfo == null)
        {
            showMessage("获取优惠券失败");
            return;
        }
        ShareDialog shareDialog =new ShareDialog(this);
        shareDialog.setUrl(sharInfo.getUrl());
        shareDialog.setTitle(sharInfo.getTitle());
        shareDialog.setText(sharInfo.getDescription());
        shareDialog.setCancelable(true);
        shareDialog.setImage(new UMImage(this,sharInfo.getPicture()));
        shareDialog.show();
    }

    @OnClick({R.id.close})
    public void close()
    {
        finish();
    }
}

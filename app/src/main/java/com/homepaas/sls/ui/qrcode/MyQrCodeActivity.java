package com.homepaas.sls.ui.qrcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.common.QrCodeGenerator;
import com.homepaas.sls.di.component.DaggerQrCodeComponent;
import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.mvp.presenter.MyQrCodePresenter;
import com.homepaas.sls.mvp.presenter.PersonalCenterPresenter;
import com.homepaas.sls.mvp.view.PersonalCenterView;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import javax.inject.Inject;

import butterknife.Bind;

public class MyQrCodeActivity extends CommonBaseActivity implements PersonalCenterView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.photo)
    ImageView photo;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.qr_code)
    ImageView qrCode;

    private static final int REQUEST_QRCODE = 2;
    @Inject
    MyQrCodePresenter presenter;
    @Inject
    PersonalCenterPresenter mPresenter;

    private static final String KEY = "personal";


    public static void start(Context context, PersonalInfoModel infoModel) {
        Intent intent = new Intent(context, MyQrCodeActivity.class);
        intent.putExtra(KEY, infoModel);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_qrcode;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        mPresenter.setPersonalCenterView(this);
        PersonalInfoModel model = getIntent().getParcelableExtra(KEY);
        if (model != null) {
            setModel(model);
        } else {
            if (mPresenter.isLoggedIn()) {
                if (NetUtils.isConnected(getApplication())) {
                    mPresenter.showQrCode();
                } else showMessage(getString(R.string.please_check_network));
            } else {
                mNavigator.login(MyQrCodeActivity.this, REQUEST_QRCODE);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerQrCodeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    private void setModel(PersonalInfoModel model) {
        if (model != null) {
            Glide.with(this)
                    .load(model.getSmallPic())
                    .placeholder(R.mipmap.default_user_icon)
                    .into(new ImageTarget(photo));
            int size = getResources().getDimensionPixelSize(R.dimen.dialog_qr_code_size);
            Bitmap bitmap = QrCodeGenerator.createQRCode(model.getQrCode(), size, size);
            qrCode.setImageBitmap(bitmap);
            name.setText(model.getNickName());
            phone.setText(model.getPhoneNumber());
        }
    }

    // FIXME: 2016/7/13 0013 无法分享图片了？？？
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.qr_code, menu);
        return true;
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ShareDialog", "onActivityResult: "+(resultCode== Activity.RESULT_OK)+(resultCode==Activity.RESULT_CANCELED));
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_QRCODE:
                    if (data != null && data.getBooleanExtra("Status",false) )
                        mPresenter.showQrCode();
                    break;
            }
        }
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            shareQrCode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    private void shareQrCode() {
       View content = findViewById(android.R.id.content);
        Bitmap bitmap = convertViewToBitmap(content,content.getWidth(),content.getHeight());
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.setImage(new UMImage(this,bitmap));
        shareDialog.setUmShareListener(new ShareDialog.SimpleUMShareListener(){
            @Override
            public void onResult(SHARE_MEDIA share_media) {
                presenter.uploadRecord();
            }
        });
        shareDialog.show();
    }

    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight){
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void render(PersonalInfoModel infoModel) {

    }

    @Override
    public void noticeCount(int count) {

    }

    @Override
    public void showQrCode(PersonalInfoModel infoModel) {
        setModel(infoModel);
    }
}

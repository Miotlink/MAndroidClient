package com.homepaas.sls.ui.personalcenter.personalmessage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.homepaas.sls.R;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.di.component.DaggerPersonalInfoComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.di.module.TokenModule;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.mvp.presenter.personalcenter.PersonalInfoPresenter;
import com.homepaas.sls.mvp.view.personalcenter.PersonalInfoView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.qrcode.MyQrCodeActivity;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.util.PermissionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Activities that contain this fragment must implement the
 * {@link OnFragmentItemClickListener} interface
 * to handle interaction events.
 */
public class PersonalInfoFragment extends CommonBaseFragment implements PersonalInfoView {

    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 1;
    @Bind(R.id.show)
    ImageView show;
    @Bind(R.id.head_portrait)
    RelativeLayout headPortrait;
    @Bind(R.id.arrow1)
    ImageView arrow1;
    @Bind(R.id.item_nick_name)
    RelativeLayout itemNickName;
    @Bind(R.id.arrow2)
    ImageView arrow2;
    @Bind(R.id.item_qr_code)
    RelativeLayout itemQrCode;
    @Bind(R.id.item_modify_password)
    FrameLayout itemModifyPassword;
    @Bind(R.id.item_address)
    FrameLayout itemAddress;
    @Bind(R.id.contact_service)
    LinearLayout contactService;
    @Bind(R.id.logout)
    Button logout;
    private OnFragmentItemClickListener mListener;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.photo)
    ImageView mPhoto;

    @Bind(R.id.nick_name)
    TextView mNickname;

    @Inject
    PersonalInfoPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_message;
    }

    @Override
    protected void initView() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter.setView(this);
        mPresenter.getPersonalInfo();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerPersonalInfoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .personalInfoModule(new PersonalInfoModule())
                .tokenModule(new TokenModule())
                .build()
                .inject(this);
//        getComponent(PersonalInfoComponent.class)
//                .inject(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CALL_AND_CALL_LOG:
                if (grantResults.length > 0) {
                    for (int gra : grantResults) {
                        if (gra != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                }
                dial();
                break;
        }
    }
    private CommonDialog dialog;
    private void contactHotLine() {
        dialog = new CommonDialog.Builder()
                .showTitle(false)
                .setContent("联系客服" + getString(R.string.service_phone_number))
                .setConfirmButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+R.string.service_phone_number));
                        startActivity(intent);
                        dialog.dismiss();
//                        mNavigator.dial(getContext(), getString(R.string.service_phone_number));
                    }
                }).setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show(getFragmentManager(), null);
    }
    // 拨打电话
    private void dial() {
//        List<String> permissions = new ArrayList<>(2);
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            permissions.add(Manifest.permission.CALL_PHONE);
//        }
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            //Service 中无法回调onRequestPermissionsResult，提前请求
//            permissions.add(Manifest.permission.READ_CALL_LOG);
//        }
//        if (permissions.isEmpty()) {
////            CallDialogFragment serviceFragment = CallDialogFragment.newInstance(phone, title);
////            serviceFragment.show(getFragmentManager(), null);
//            contactHotLine();
//        } else {
//            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION_CALL_AND_CALL_LOG);
//        }

        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.PHONE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group,null), REQUEST_PERMISSION_CALL_AND_CALL_LOG)) {
            contactHotLine();
        }
    }

    @OnClick(R.id.contact_service)
    void call(){
        dial();
    }
    @OnClick(R.id.head_portrait)
    void setPhotoClick() {
        mListener.onPhotoClick();
    }

    @OnClick(R.id.item_nick_name)
    void modifyNicknameClick() {
        if (mListener != null) {
            mListener.onModifyNickname();
        }
    }

    @OnClick(R.id.item_qr_code)
    void showQrCodeClick() {
        mPresenter.showQrCode();
    }

    @OnClick(R.id.item_modify_password)
    void modifyPasswordClick() {
        mNavigator.modifyPassword(getActivity());
    }

    @OnClick(R.id.item_address)
    void modifyAddressClick() {

    }

    @OnClick(R.id.logout)
    void logoutClick() {
        CommonDialog commonDialog = new CommonDialog.Builder()
                .showTitle(false)
                .setContent("是否确认退出当前账户？")
                .setCancelButton("否", null)
                .setConfirmButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.logout();
                    }
                })
                .create();

        commonDialog.show(getFragmentManager(), "");

    }

    @Override
    public void render(PersonalInfoModel infoModel) {
        Glide.with(mContext).load(infoModel.getSmallPic())
                .asBitmap()
                .fitCenter()
                .placeholder(R.mipmap.default_user_icon)
                .error(R.mipmap.default_user_icon)
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mPhoto.setImageBitmap(resource);
                    }
                });
        if (!TextUtils.isEmpty(infoModel.getNickName())){
            mNickname.setText(infoModel.getNickName());
        } else {
            mNickname.setText("点我设置昵称");
        }

    }
    private static final int CODE_LOGIN = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status",false) )
                        mPresenter.getPersonalInfo();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof AuthException) {
            mNavigator.login(PersonalInfoFragment.this,CODE_LOGIN);
//            LoginDialogFragment.show(getActivity(), new LoginDialogFragment.OnLoginListener() {
//                @Override
//                public void onLogin() {
//                    mPresenter.getPersonalInfo();
//                }
//            });
        } else {
            showMessage(e.getMessage());
        }
    }


    void requestModifyPhoto(String path) {
        mPresenter.modifyPhoto(path);
    }

    @Override
    public void showQrCodeView(PersonalInfoModel infoModel) {
        if (NetUtils.isConnected(getActivity())) {
//            MyQrCodeFragment fragment = MyQrCodeFragment.newInstance(infoModel);
//            fragment.show(getFragmentManager(), null);
            MyQrCodeActivity.start(getActivity(), infoModel);
        } else
            showMessage(getString(R.string.please_check_network));
    }

    @Override
    public void modifyPhoto(String url) {
        Glide.with(mContext).load(url)
                .asBitmap()
                .fitCenter()
                .placeholder(R.mipmap.default_user_icon)
                .error(R.mipmap.default_user_icon)
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mPhoto.setImageBitmap(resource);
                    }
                });
        EventBus.getDefault().post(new EventPersonalInfo(EventPersonalInfo.PERSONAL_INFO));
    }

    @Override
    public void logout() {
        ActivityCompat.finishAfterTransition(getActivity());
        EventPersonalInfo info = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
        info.setLogin(false);
        EventBus.getDefault().post(info);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentItemClickListener) {
            mListener = (OnFragmentItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentItemClickListener {

        void onModifyNickname();

        void onPhotoClick();
    }
}

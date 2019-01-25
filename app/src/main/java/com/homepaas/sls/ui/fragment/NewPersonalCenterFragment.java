package com.homepaas.sls.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.di.component.DaggerMainComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.mvp.presenter.PersonalCenterPresenter;
import com.homepaas.sls.mvp.presenter.TokenPresenter;
import com.homepaas.sls.mvp.view.PersonalCenterView;
import com.homepaas.sls.mvp.view.TokenView;
import com.homepaas.sls.pushservice.PushUtil;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.NewCommonDialog;
import com.homepaas.sls.util.DensityUtil;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PermissionUtil;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.homepaas.sls.R.id.btn_login;
import static com.homepaas.sls.R.id.recommend_content;

/**
 * A simple {@link } subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link NewPersonalCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPersonalCenterFragment extends CommonBaseFragment implements PersonalCenterView, TokenView {

    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.rl_hotfix)
    RelativeLayout rlHotfix;
    @Bind(R.id.rl_setting)
    RelativeLayout rlSetting;
    @Bind(R.id.unreadIcon)
    View unreadIcon;
    @Bind(R.id.message)
    FrameLayout message;
    @Bind(R.id.head_portrait)
    CircleImageView headPortrait;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.telephone)
    TextView telephone;
    @Bind(R.id.personal_info)
    LinearLayout personalInfo;
    @Bind(R.id.my_account)
    TextView myAccount;
    @Bind(R.id.my_coupon)
    TextView myCoupon;
    @Bind(R.id.my_collect)
    TextView myCollect;
    @Bind(R.id.service_address)
    RelativeLayout serviceAddress;
    @Bind(R.id.call_log)
    RelativeLayout callLog;
    @Bind(R.id.comment)
    RelativeLayout comment;
    @Bind(recommend_content)
    RelativeLayout recommendContent;
    @Bind(R.id.recommend_divider)
    View recommendDivider;
    @Bind(R.id.recommend_code)
    RelativeLayout recommendCode;
    @Bind(R.id.recommend)
    LinearLayout recommend;
    @Bind(R.id.contact_service)
    LinearLayout contactService;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_LOGIN = 1;
    private static final int REQUEST_QRCODE = 2;
    private static final int REQUEST_ACCOUNT = 3;
    private static final int REQUEST_COLLECTION = 4;
    private static final int REQUEST_ORDER = 5;
    private static final int REQUEST_MESSAGE = 6;
    private static final int REQUEST_COMMENT = 7;
    private static final int REQUEST_CONPONT = 8;
    public static final int REQUEST_INVITATION = 9;//邀请码
    private static final int REQUEST_WRITE_INVITATION = 10;
    private static final int REQUEST_ADDRESS = 11;
    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 12;
    private static final int REQUEST_LOAD_INFO = 13;

    @Inject
    PersonalCenterPresenter mPresenter;
    @Inject
    TokenPresenter tokenPresenter;
    @Inject
    protected PushUtil pushUtil;
    @Bind(R.id.ly_login_info)
    LinearLayout lyLoginInfo;
    @Bind(btn_login)
    Button btnLogin;
    @Bind(R.id.img_edit)
    ImageView imgEdit;
    private PushInfo pushInfo_mine;
    private PushInfo pushInfo_add;
    private CommonDialog dialog;
    private CommonAppPreferences commonAppPreferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewPersonalCenterFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPersonalCenterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPersonalCenterFragment newInstance(String param1, String param2) {
        NewPersonalCenterFragment fragment = new NewPersonalCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mPresenter.setPersonalCenterView(this);
        EventBus.getDefault().register(mPresenter);
        LogUtils.i("TAG", "NewPersonalCenterFragment:onCreate()");

    }

    private LoginDialogFragment loginDialogFragment;

    private NewCommonDialog newUserConpoutDialog;
    private boolean isNewUser;
    private SharedPreferences sharedPreferences;

    private void showNewUserDialog() {
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(Constant.IS_NEW_USER_PRF, Context.MODE_PRIVATE);
        isNewUser = sharedPreferences.getBoolean(Constant.IS_NEW_USER_FIELD, false);
        if (isNewUser) {
            String content = sharedPreferences.getString(Constant.NEW_USER_MESSAGE, "");
            newUserConpoutDialog = new NewCommonDialog.Builder()
                    .showTitle(false)
                    .setContent(content)
                    .setContentGravity(Gravity.CENTER)
                    .setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            newUserConpoutDialog.dismissDialog();
                        }
                    })
                    .setConfirmButton("查看", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mNavigator.showMyNewRed(getActivity());
                            newUserConpoutDialog.dismissDialog();
                        }
                    })
                    .create();
            sharedPreferences.edit().putBoolean(Constant.IS_NEW_USER_FIELD, false).commit();
            newUserConpoutDialog.show(getFragmentManager(), null);
        }
    }


    @Override
    protected void initializeInjector() {
        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
    }

    // 拨打电话
    private void dial() {
        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.PHONE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), REQUEST_PERMISSION_CALL_AND_CALL_LOG)) {
            contactHotLine();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CALL_AND_CALL_LOG:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                dial();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (!mPresenter.isLoggedIn())
                return;
            switch (requestCode) {
                case REQUEST_LOGIN:
//                    if (data != null && data.getBooleanExtra("Status", false))
//                        mNavigator.showPersonalDetail(getActivity());
                    break;
                case REQUEST_LOAD_INFO:
                    if (data != null && data.getBooleanExtra("Status", false)) {
                        mPresenter.loadPersonalInfo();
                    }
                    break;
                case REQUEST_COLLECTION:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.showCollections(getActivity());
                    break;
                case REQUEST_MESSAGE:
                    if (data != null && data.getBooleanExtra("Status", false)) {
                        if (View.VISIBLE == unreadIcon.getVisibility()) {
                            unreadIcon.setVisibility(View.GONE);
                        }
                        mNavigator.viewMessage(getActivity());
                    }
                    break;
                case REQUEST_COMMENT:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.showComment(getActivity());
                    break;
                case REQUEST_ACCOUNT:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.showMyAccount(getActivity());
                    break;
                case REQUEST_CONPONT:
                    mNavigator.showMyCoupons(getActivity());
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.showMyNewRed(getActivity());
                    break;
                case REQUEST_ORDER:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.showOrder(getActivity());
                    break;
                case REQUEST_INVITATION:
                    if (data != null && data.getBooleanExtra("Status", false))
                        tokenPresenter.getToken(this);
                    break;
                case REQUEST_WRITE_INVITATION:
//                    mNavigator.writeBackInvitation(getActivity());
                    if (data != null && data.getBooleanExtra("Status", false))
                        pushUtil.startWebView(getContext(), pushInfo_add);
                    break;
                case REQUEST_ADDRESS:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.showAddress(getActivity());
                    break;
                default:
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_personal_center;
    }

    @Override
    protected void initView() {
        LogUtils.i("NeWPersonalCenterFragment:initView");
        commonAppPreferences = new CommonAppPreferences(getActivity());
        initClick();


        Drawable drawable = getResources().getDrawable(R.mipmap.my_account);
        drawable.setBounds(0, 0, DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        myAccount.setCompoundDrawables(null, drawable, null, null);//只放左边
        Drawable drawable1 = getResources().getDrawable(R.mipmap.my_collect);
        drawable1.setBounds(0, 0, DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        myCollect.setCompoundDrawables(null, drawable1, null, null);//只放左边
        Drawable drawable2 = getResources().getDrawable(R.mipmap.my_red_packet);
        drawable2.setBounds(0, 0, DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        myCoupon.setCompoundDrawables(null, drawable2, null, null);//只放左边
    }

    @Override
    protected void initData() {

    }

    //防重复点击
    private void initClick() {

        //服务地址
        RxBindingViewClickHelper.onClick(serviceAddress, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (mPresenter.isLoggedIn()) {
                    mNavigator.showAddress(getActivity());
                } else
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_ADDRESS);
            }
        });
        //通话记录
        RxBindingViewClickHelper.onClick(callLog, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                mNavigator.showCallLogs(getActivity());
            }
        });
        //我的评价
        RxBindingViewClickHelper.onClick(comment, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (mPresenter.isLoggedIn()) {
                    mNavigator.showComment(getActivity());
                } else {
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_COMMENT);
                }
            }
        });
        //推荐有奖
        RxBindingViewClickHelper.onClick(recommendCode, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (!mPresenter.isLoggedIn()) {
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_INVITATION);
                } else {
                    pushUtil.startWebView(getContext(), pushInfo_mine);
                }
            }
        });

//        //填写邀请码
//        RxBindingViewClickHelper.onClick(recommendContent, new RxBindingViewClickHelper.OnRxClick() {
//            @Override
//            public void rxClick(View view) {
//                if (mPresenter.isLoggedIn()) {
//                    pushUtil.startWebView(getContext(), pushInfo_add);
//                } else {
//                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_WRITE_INVITATION);
//                }
//            }
//        });

        //我的账户
        RxBindingViewClickHelper.onClick(myAccount, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (mPresenter.isLoggedIn()) {
                    mNavigator.showMyAccount(getActivity());
                } else {
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_ACCOUNT);
                }
            }
        });
        //我的红包
        RxBindingViewClickHelper.onClick(myCoupon, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (mPresenter.isLoggedIn()) {
                    mNavigator.showMyNewRed(getActivity());
                } else {
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_CONPONT);
                }
            }
        });

        //我的收藏
        RxBindingViewClickHelper.onClick(myCollect, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (mPresenter.isLoggedIn()) {
                    mNavigator.showCollections(getActivity());
                } else {
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_COLLECTION);
                }
            }
        });
        //客服电话
        RxBindingViewClickHelper.onClick(contactService, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                dial();
            }
        });

        //设置
        RxBindingViewClickHelper.onClick(rlSetting, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                mNavigator.otherMessage(getActivity());
            }
        });

        //消息
        RxBindingViewClickHelper.onClick(message, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (mPresenter.isLoggedIn()) {
                    mNavigator.viewMessage(getActivity());
                } else {
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_MESSAGE);
                }
            }
        });//个人信息
        RxBindingViewClickHelper.onClick(personalInfo, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (mPresenter.isLoggedIn()) {
                    mNavigator.showPersonalDetail(getActivity());
                } else {
                    mNavigator.login(NewPersonalCenterFragment.this, REQUEST_LOGIN);
                }
            }
        });

        RxBindingViewClickHelper.onClick(btnLogin, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                mNavigator.login(NewPersonalCenterFragment.this, REQUEST_LOGIN);
            }
        });
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        EventBus.getDefault().unregister(mPresenter);
        super.onDestroy();
    }

    private void contactHotLine() {
        dialog = new CommonDialog.Builder()
                .showTitle(false)
                .setContent("联系客服妹子" + getString(R.string.service_phone_number))
                .setConfirmButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + R.string.service_phone_number));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }).setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show(getFragmentManager(), null);
    }

    private void restView() {
        unreadIcon.setVisibility(View.INVISIBLE);
        lyLoginInfo.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        headPortrait.setImageResource(R.mipmap.default_user_icon);
        //移除邀请码items
        recommend.setVisibility(View.GONE);
    }

    @Override
    public void render(PersonalInfoModel infoModel) {
        if (mView != null) {
            if (infoModel == null) {
                lyLoginInfo.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                headPortrait.setImageResource(R.mipmap.default_user_icon);
                //移除邀请码items
                recommend.setVisibility(View.GONE);
            } else {
                commonAppPreferences.setHeadPortrait(infoModel.getSmallPic());
                showNewUserDialog();
                lyLoginInfo.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                //
                if (TextUtils.equals(infoModel.getNickName(), infoModel.getPhoneNumber()) || TextUtils.isEmpty(infoModel.getNickName())) {
                    name.setText(getResources().getString(R.string.txt1));
                    imgEdit.setVisibility(View.VISIBLE);
                } else {
                    name.setText(infoModel.getNickName());
                    imgEdit.setVisibility(View.GONE);
                }
                telephone.setText(infoModel.getPhoneNumber());
                Glide.with(mContext).load(infoModel.getSmallPic())
                        .asBitmap()
                        .fitCenter()
                        .placeholder(R.mipmap.default_user_icon)
                        .error(R.mipmap.default_user_icon)
                        .into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                headPortrait.setImageBitmap(resource);
                            }
                        });
            }
            //获取到邀请码地址
            if (infoModel != null) {
                pushInfo_mine = new PushInfo();
                pushInfo_mine.setTitle("推荐有奖");
                pushInfo_mine.setUrl(infoModel.getRecommendationUrl_Mine());
                pushInfo_mine.setShareTitle(getString(R.string.invitation_share_title));
                pushInfo_mine.setShareDescription(getString(R.string.invitation_share_description));
                pushInfo_mine.setIsShare("1");
                pushInfo_mine.setShareUrl(infoModel.getRecommendationUrl_Share());
                pushInfo_mine.setShareResoures(R.drawable.share_invitation_pic);
                if (infoModel.getRecommendUserName() == null) {
                    pushInfo_add = new PushInfo();
                    pushInfo_add.setTitle("填写邀请码");
                    pushInfo_add.setUrl(infoModel.getRecommendationUrl_Add());
                    pushInfo_add.setIsShare("0");
                } else {
//                menuItemList.remove(writeInvitationCodeItem);
//                mAdapter.refreshItem(menuItemList);
                }
            }
        }


    }


    @Override
    public void showLogin() {
        //dialog消失后不会走 onResume
//        LoginDialogFragment.show(getActivity(), null);
        mNavigator.login(NewPersonalCenterFragment.this, REQUEST_LOAD_INFO);
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        if (e instanceof AuthException || e instanceof ResponseMetaAuthException) {
            restView();
        }
    }

    @Override
    public void noticeCount(int count) {
        if (unreadIcon != null)
            unreadIcon.setVisibility((count > 0) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showQrCode(PersonalInfoModel infoModel) {

    }

    @Override
    public void onToken(String token) {
        pushUtil.startWebView(getContext(), pushInfo_mine);
    }

    @OnClick(R.id.rl_hotfix)
    public void onViewClicked() {
        //测试补丁文件加载
        loadPatch();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter.isLoggedIn()) {
            mPresenter.loadPersonalInfo();
            mPresenter.getUnreadMessageCount();
        } else {
            restView();
        }
    }

    /**
     * 加载热补丁插件
     */
    public void loadPatch() {
        TinkerInstaller.onReceiveUpgradePatch(getActivity(),
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
    }

    /**
     * 杀死应用加载补丁
     */
    public void killApp() {
        ShareTinkerInternals.killAllOtherProcess(getActivity());
        Process.killProcess(Process.myPid());
    }

}

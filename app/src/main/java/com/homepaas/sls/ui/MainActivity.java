package com.homepaas.sls.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.Global;
import com.homepaas.sls.R;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.DaggerMainComponent;
import com.homepaas.sls.di.component.MainComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.BannerModule;
import com.homepaas.sls.di.module.FirstOrderInfoModule;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.NewFirstOpenAppInfo;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.entity.VerifyTokenBody;
import com.homepaas.sls.event.CloseDialogEvent;
import com.homepaas.sls.event.NoSearchServiceEvent;
import com.homepaas.sls.jsinterface.entity.ObjectApns;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.presenter.FirstOpenAppPresenter;
import com.homepaas.sls.mvp.presenter.MainActivityPresenter;
import com.homepaas.sls.mvp.view.FirstOpenAppView;
import com.homepaas.sls.mvp.view.MainActivityView;
import com.homepaas.sls.phone.PhoneStateService;
import com.homepaas.sls.pushservice.GetuiAppViewIndex;
import com.homepaas.sls.ui.account.RechargeChooseActivity;
import com.homepaas.sls.ui.account.WalletActivity;
import com.homepaas.sls.ui.comment.MyCommentActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.detail.BusinessDetailActivity;
import com.homepaas.sls.ui.detail.WorkerDetailActivity;
import com.homepaas.sls.ui.fragment.NewPersonalCenterFragment;
import com.homepaas.sls.ui.homepage_3_3_0.HomePageFragment;
import com.homepaas.sls.ui.imchating.ImLoginActivity;
import com.homepaas.sls.ui.imchating.util.DemoHelper;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.login.RegisterActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.chooseservice.NewChooseServiceActivity;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ErrandServiceDetailActivity;
import com.homepaas.sls.ui.order.directOrder.ExpressOrderTrackingActivity;
import com.homepaas.sls.ui.order.directOrder.PlaceOrderActivity;
import com.homepaas.sls.ui.order.manage.OrderManageFragment;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.personalcenter.CallLogsActivity;
import com.homepaas.sls.ui.personalcenter.EmptyMessageActivity;
import com.homepaas.sls.ui.personalcenter.collection.NewCollectionActivity;
import com.homepaas.sls.ui.personalcenter.more.FeedbackActivity;
import com.homepaas.sls.ui.personalcenter.more.MoreActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.PersonalMessageActivity;
import com.homepaas.sls.ui.qrcode.MyQrCodeActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewRedPacketActivity;
import com.homepaas.sls.ui.search.MainContentFragment;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.CurrencyDialog;
import com.homepaas.sls.ui.widget.FilletCommonDialog;
import com.homepaas.sls.ui.widget.bottomnavigation.MainBottomNavigation;
import com.homepaas.sls.ui.widget.bottomnavigation.OnBottomNavigationSelectedListener;
import com.homepaas.sls.update.DownloadService;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.time.DateUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.ui.BaseActivity;
import com.runtimepermission.acp.AcpListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.navigation.Navigator.BUSINESS_ID;
import static com.homepaas.sls.navigation.Navigator.MESSAGE_CONTENT;
import static com.homepaas.sls.navigation.Navigator.MESSAGE_TITLE;
import static com.homepaas.sls.navigation.Navigator.WORKER_ID;

//import com.umeng.analytics.MobclickAgent;

public class MainActivity extends CommonBaseActivity implements OnBottomNavigationSelectedListener, HasComponent<MainComponent>, MainActivityView, FirstOpenAppView, DemoHelper.AddRedDot {

    @Bind(R.id.activity_new_main)
    RelativeLayout activityNewMain;
    //新手指导页
    @Bind(R.id.novice)
    FrameLayout novice;
    @Bind(R.id.yindao_3)
    ImageView yindao3;
    @Bind(R.id.yindao_2)
    ImageView yindao2;
    @Bind(R.id.novive_btn)
    Button noviveBtn;
    @Bind(R.id.novice_bottom)
    ImageView noviceBottom;
    @Bind(R.id.activity_imageView)
    ImageView activityImageView;
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.activity_rel)
    RelativeLayout activityRel;
    @Bind(R.id.background)
    RelativeLayout background;
    @Bind(R.id.main_bottom_navigation)
    MainBottomNavigation mainBottomNavigation;


    @Inject
    Global global;
    @Inject
    FirstOpenAppPresenter firstAppPresenter;
//    @Inject
//    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    MainActivityPresenter mainActivityPresenter;

    //附近fragment
    private MainContentFragment mMainContentFragment;
    //我的订单
    private OrderManageFragment orderManageFragment;
    //我的
    private NewPersonalCenterFragment personalCenterFragment;
    //首页
    private HomePageFragment homePageFragment;

    private MainComponent mComponent;
    private PushInfo pushInfo;
    private ObjectApns objectApns;


    //推送通用弹出框
    private FilletCommonDialog filletCommonDialog;
    //h5吊起通用弹出框
    private FilletCommonDialog objectApnsCommonDialog;
    private NoSearchServiceEvent event;
    private CurrencyDialog dialogForceUpdate;
    private CurrencyDialog dialogUpdate;
    private SharedPreferences sharedPreferences;
    private CommonAppPreferences commonAppPreferences;
    private SharedPreferences preferences;
    private NetWorkConnectionReceiver netWorkConnectionReceiver = new NetWorkConnectionReceiver();
    private NewFirstOpenAppInfo newFirstOpenAppInfo;

    private String token = null;
    private static final String TAG = "MainActivity";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_main;
    }

    private void initClickListener() {
        mainBottomNavigation.setBottomNavigationSelectedListener(this);
    }


    @Override
    public View getSnackBarHolderView() {
        return activityNewMain;
    }


    @Override
    protected void initView() {
        LogUtils.i("MainActivity:initView");
        initNetWorkStatusListener();

        sharedPreferences = getSharedPreferences(StaticData.SPF_NAME, MODE_PRIVATE);
        commonAppPreferences = new CommonAppPreferences(getContext());
        mainActivityPresenter.setMainActivityView(this);

        EventBus.getDefault().register(this);

        startService(new Intent(this, PhoneStateService.class));
        //获取首页弹框信息
        firstAppPresenter.setFirstOpenAppView(this);
        //新手活动
        preferences = getContext().getSharedPreferences(global.getFirstPrefNewcomerActive(), MODE_PRIVATE);
        getFirstAppOpenInfo();
        mainActivityPresenter.verifyToken();
        mainActivityPresenter.checkUpdate();
        background.setAlpha(0.7f);
        initClick();
    }

    /**
     * 清理 FragmentManager 中的 Fragment。<br/>
     * 解决在系统设置中更改权限后，App 被 kill 掉重启时的 Fragment 状态错误问题。
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void clearFragmentManagerInsideFragments(Activity activity) {
        if (activity instanceof FragmentActivity) {
            FragmentManager manager = ((FragmentActivity) activity).getSupportFragmentManager();
            int count = manager.getBackStackEntryCount();
            @SuppressLint("RestrictedApi") List<Fragment> list = manager.getFragments();
            int fragmentCount = list == null ? 0 : list.size();
            if (list != null) {
                for (Fragment fragment : list) {
                    manager.beginTransaction().remove(fragment).commit();
                }
            }
        }
    }

    private void initClick() {
        //拦截touch事件，
        mainBottomNavigation.getmRadioButtonOrder().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //未登录状态点击订单交互修改 点击订单，跳出登录页。
                if (event.getAction() == MotionEvent.ACTION_DOWN && !PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {
                    mNavigator.login(MainActivity.this, REQUEST_LOGIN_BASE_ACTIVITY);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取第一次打开后的弹框内容
     */
    private void getFirstAppOpenInfo() {
        firstAppPresenter.getFirstOpenAppInfoEX();
    }


    @Override
    protected void initData() {
        mMainContentFragment = new MainContentFragment();
        orderManageFragment = OrderManageFragment.newInstance();
        personalCenterFragment = new NewPersonalCenterFragment();
        homePageFragment = HomePageFragment.newInstance();
        //默认显示首页
        mainBottomNavigation.setBottomNavigationClick(MainBottomNavigation.TAB_HOME);
        LogUtils.i("MainActivity:initData");
        clearFragmentManagerInsideFragments(this);
        onValueSelected(MainBottomNavigation.TAB_HOME);
        initClickListener();
    }
//
//
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        LogUtils.i("MainActivity:onSaveInstanceState\t" + outState);
//        super.onSaveInstanceState(outState, outPersistentState);
//    }

    public void emptyViewClick(View view) {

    }

    @OnClick({R.id.novive_btn, R.id.novice, R.id.close, R.id.activity_imageView})
    public void select(View view) {
        switch (view.getId()) {
            case R.id.novice:
                yindao2.setVisibility(View.GONE);
                yindao3.setVisibility(View.GONE);
                noviveBtn.setVisibility(View.VISIBLE);
                noviceBottom.setVisibility(View.VISIBLE);
                break;
            case R.id.novive_btn:
                SharedPreferences preferences = getContext().getSharedPreferences(global.getFirstPrefMain(), MODE_PRIVATE);
                //走完一遍引导页面后设置标志为false
                preferences.edit().putBoolean("isFirstInMain", false).apply();
                novice.setVisibility(View.GONE);

                //在新手引导以后弹出,只有在弹出指导页的情况下
                getFirstAppOpenInfo();
                preferences = getContext().getSharedPreferences(global.getFirstPrefNewcomerActive(), MODE_PRIVATE);
                preferences.edit().putInt(global.PREF_KEY_FOR_NEWCOMER_ACTIVE, 1).apply();
                break;
            case R.id.close:
                activityRel.setVisibility(View.GONE);
                break;
            case R.id.activity_imageView://单击活动图片进行跳转，携带活动信息
                if (newFirstOpenAppInfo != null && !TextUtils.isEmpty(newFirstOpenAppInfo.getOpenUrl())) {
                    PushInfo pushInfo = new PushInfo();
                    pushInfo.setIsShare(newFirstOpenAppInfo.getIsShare());
                    pushInfo.setUrl(newFirstOpenAppInfo.getOpenUrl());
                    pushInfo.setShareUrl(newFirstOpenAppInfo.getShareUrl());
                    pushInfo.setShareTitle(newFirstOpenAppInfo.getShareTitle());
                    pushInfo.setShareDescription(newFirstOpenAppInfo.getShareDescription());
                    pushInfo.setSharePic(newFirstOpenAppInfo.getSharePic());
                    WebViewActivity.start(this, pushInfo);
                }
                activityRel.post(new Runnable() {
                    @Override
                    public void run() {
                        activityRel.setVisibility(View.GONE);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void renderFirstOrder(boolean isFirst, IsFirstOrderInfo.Service lastOrderServiceInfo) {
        if (isFirst) {
            Intent intent = new Intent(MainActivity.this, NewChooseServiceActivity.class);
            intent.putExtra("isFirstOrder", isFirst);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ConfirmOrderActivity.class);
            if (lastOrderServiceInfo != null) {
                intent.putExtra("LastServiceTypeId", lastOrderServiceInfo.getId());
                intent.putExtra("LastServiceTypeName", lastOrderServiceInfo.getName());
            }
            intent.putExtra(StaticData.SEARCH_OR_BUTTON, "1");
            startActivity(intent);
        }

    }

    private void setSharedPreferences(String imUserName, String imPwd) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.IM_USERNAME, imUserName);
        editor.putString(StaticData.IM_PWD, imPwd);
        editor.commit();
    }

    @Override
    public void send(VerifyTokenBody verifyTokenBody) {
        if (verifyTokenBody != null) {
            if (!TextUtils.isEmpty(verifyTokenBody.getUserId())) {
                GrowingIO growingIO = GrowingIO.getInstance();
                growingIO.setCS1("user_id", verifyTokenBody.getUserId());
            }
            if (!TextUtils.isEmpty(verifyTokenBody.getImUserName()) && !TextUtils.isEmpty(verifyTokenBody.getImPwd())) {
                setSharedPreferences(verifyTokenBody.getImUserName(), verifyTokenBody.getImPwd());
            }
        }
    }

    @Override
    public void update(final UpdateCheck updateCheck) {
        int currentCode = BuildConfig.VERSION_CODE;
        if (currentCode < updateCheck.getVersion() && !TextUtils.isEmpty(updateCheck.getUrl())) {
            if (updateCheck.getForceUpdate() == 1) {
                if (dialogForceUpdate == null) {
                    dialogForceUpdate = new CurrencyDialog(MainActivity.this);
                    dialogForceUpdate.setTitle("版本更新");
                    dialogForceUpdate.setMessage("我们已经更新了新的版本，当前版本不可用，请及时更新哦~");
                    dialogForceUpdate.setYesOnclickListener("更新", new CurrencyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            updateApk(updateCheck);
                            dialogForceUpdate.dismiss();
                        }
                    });
                    dialogForceUpdate.setNoOnclickListener("退出", new CurrencyDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            Process.killProcess(Process.myPid());
                        }
                    });
                }
                dialogForceUpdate.show();
            } else {
                if (dialogUpdate == null) {
                    dialogUpdate = new CurrencyDialog(MainActivity.this);
                    dialogUpdate.setTitle("版本更新");
                    dialogUpdate.setMessage("发现新版本，要升级新版本吗？");
                    dialogUpdate.setYesOnclickListener("更新", new CurrencyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            updateApk(updateCheck);
                            dialogUpdate.dismiss();
                        }
                    });
                    dialogUpdate.setNoOnclickListener("忽略", new CurrencyDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialogUpdate.dismiss();
                        }
                    });
                }
                dialogUpdate.show();
            }
        }
    }


    private void updateApk(final UpdateCheck updateCheck) {
        PermissionUtils.downLoadFilePermission(mContext, new AcpListener() {
            @Override
            public void onGranted() {
                MaterialDialog materialDialog = new MaterialDialog.Builder(MainActivity.this)
                        .title("版本升级")
                        .content("正在下载安装包，请稍候")
                        .progress(false, 100, false)
                        .cancelable(false)
                        .negativeText("取消")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                DownloadService.stopDownload();

                                if (updateCheck.getForceUpdate() == 1) {
                                    Process.killProcess(Process.myPid());
                                }

                            }
                        })
                        .show();
                DownloadService.setMaterialDialog(materialDialog);
                DownloadService.start(MainActivity.this, updateCheck.getUrl(), updateCheck.getMd5());
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onNoSearchService(final NoSearchServiceEvent event) {
        //此时还无法直接弹出Dialog提示，onResume生命周期中弹Dialog
        this.event = event;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
    }

    private void alertNoSearchResult(String searchContent) {
//        searchNoResultDialog = new CommonDialog.Builder()
//                .showTitle(false)
//                .setContent("附近没有工人提供" + searchContent + "服务，试试一键下单，我们将免费帮您安排工人上门服务")
//                .setCancelButton("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        searchNoResultDialog.dismiss();
//                    }
//                })
//                .setConfirmButton("一键下单", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(MainActivity.this, ConfirmOrderActivity.class));
//                    }
//                })
//                .create();
//        searchNoResultDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    protected void onResume() {
        if (event != null) {
            alertNoSearchResult(event.searchContent);
            event = null;
        }
        DemoHelper.setAddRedDot(this);
        DemoHelper.sethotWhere(true);
        super.onResume();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (DEBUG)
            Log.d(TAG, "onNewIntent: ");
        if (intent.getIntExtra("ToOrderList", 0) > 0) {
            mainBottomNavigation.setBottomNavigationClick(MainBottomNavigation.ORDER);
        }

        if (intent.getIntExtra(StaticData.HOME_PAGE_INDEX, -1) > -1) {
            int index = intent.getIntExtra(StaticData.HOME_PAGE_INDEX, -1);
            mainBottomNavigation.setBottomNavigationClick(index);
        }
        //可能收到推送时时，不在主页，从其他页面跳过来，回调此方法
        pushInfo = (PushInfo) intent.getSerializableExtra("PushInfo");
        if (pushInfo != null) {
            commonDialog(pushInfo);
        }
        objectApns = (ObjectApns) intent.getSerializableExtra("ObjectApns");
        if (objectApns != null) {
            commonDialog(objectApns);
        }

    }

    private void commonDialog(ObjectApns objectApns) {
        boolean showTitle = !TextUtils.isEmpty(objectApns.getAlertTitle());
        objectApnsCommonDialog = new FilletCommonDialog.Builder()
                .showTitle(showTitle)
                .setTitle(objectApns.getAlertTitle())
                .setContent(objectApns.getAlertMessage())
                .setContentGravity(Gravity.CENTER)
                .setCancelButton(objectApns.getAlertCancelTitle(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        objectApnsCommonDialog.dismissAllowingStateLoss();
                    }
                })
                .setConfirmButton(objectApns.getAlertConfirmTitle(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        objectApnsCommonDialog.dismissAllowingStateLoss();
                    }
                }).create();
        objectApnsCommonDialog.show(getSupportFragmentManager(), null);
    }

    //推送通用弹出框
    private void commonDialog(final PushInfo pushInfo) {
        filletCommonDialog = new FilletCommonDialog.Builder()
                .showTitle(false)
                .setContent(pushInfo.getAlertMessage())
                .setContentGravity(Gravity.CENTER)
                .setCancelButton(pushInfo.getAlertCancelTitle(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filletCommonDialog.dismissAllowingStateLoss();
                    }
                })
                .setConfirmButton(pushInfo.getAlertConfirmTitle(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filletCommonDialog.dismissAllowingStateLoss();
                        goActivity(pushInfo);
                    }
                }).create();
        filletCommonDialog.show(getSupportFragmentManager(), null);
    }

    /**
     * 推送判断点击确定进入什么页面
     */
    private void goActivity(PushInfo pushInfo) {
        if (pushInfo != null) {
            int jumpCode = Integer.valueOf(pushInfo.getAppViewId());
            switch (jumpCode) {
                case GetuiAppViewIndex.DetailOrderActivity://订单详情
                    if (pushInfo.getOrder() != null && !TextUtils.isEmpty(pushInfo.getOrder().getOrderId())) {
                        if (!TextUtils.isEmpty(pushInfo.getIsKdEOrder()) && TextUtils.equals("1", pushInfo.getIsKdEOrder())) {
                            ExpressOrderTrackingActivity.start(this, pushInfo.getOrder().getOrderId());
                        } else if (!TextUtils.isEmpty(pushInfo.getIsKdEOrder()) && TextUtils.equals("2", pushInfo.getIsKdEOrder())) {
                            ErrandServiceDetailActivity.start(this, pushInfo.getOrder().getOrderId());
                        } else {
                            Intent intent = new Intent(this, ClientOrderDetailActivity.class);
                            intent.putExtra(StaticData.ORDER_ID, pushInfo.getOrder().getOrderId());
                            startActivity(intent);
                        }
                    }
                    break;
                case GetuiAppViewIndex.LoginPopUpWindow:
                    mNavigator.login(MainActivity.this, GetuiAppViewIndex.LoginPopUpWindow);
                    break;
                case GetuiAppViewIndex.LoginActivity:
                    mNavigator.login(MainActivity.this, GetuiAppViewIndex.LoginActivity);
                    break;
                case GetuiAppViewIndex.RegisterActivity:
                    if (pushInfo.getRecommendInfoRegister() != null && !TextUtils.isEmpty(pushInfo.getRecommendInfoRegister().getRecommendCode())) {
                        Intent registerActivityIntent = new Intent(this, RegisterActivity.class);
                        registerActivityIntent.putExtra("code", pushInfo.getRecommendInfoRegister().getRecommendCode());
                        startActivity(registerActivityIntent);
                    }
                    break;
                case GetuiAppViewIndex.MainAcitivity:
                    break;
                case GetuiAppViewIndex.MyCollectionActivity:
                    startActivity(new Intent(this, NewCollectionActivity.class));
                    break;
                case GetuiAppViewIndex.MessageTextActivity:
                    if (pushInfo.getMessage() != null) {
                        Intent emptyMessageActivityIntent = new Intent(this, EmptyMessageActivity.class);
                        emptyMessageActivityIntent.putExtra(MESSAGE_TITLE, pushInfo.getMessage().getTitle());
                        emptyMessageActivityIntent.putExtra(MESSAGE_CONTENT, pushInfo.getMessage().getContent());
                        startActivity(emptyMessageActivityIntent);
                    }
                    break;
                case GetuiAppViewIndex.WorkerDetail:
                    if (pushInfo.getWorker() != null && !TextUtils.isEmpty(pushInfo.getWorker().getWorkerId())) {
                        Intent workerDetailActivityIntent = new Intent(this, WorkerDetailActivity.class);
                        workerDetailActivityIntent.putExtra(WORKER_ID, pushInfo.getWorker().getWorkerId());
                        startActivity(workerDetailActivityIntent);
                    }
                    break;
                case GetuiAppViewIndex.MerchantDetail:
                    if (pushInfo.getMerchant() != null && !TextUtils.isEmpty(pushInfo.getMerchant().getMerchantId())) {
                        Intent businessDetailActivityIntent = new Intent(this, BusinessDetailActivity.class);
                        businessDetailActivityIntent.putExtra(BUSINESS_ID, pushInfo.getMerchant().getMerchantId());
                        startActivity(businessDetailActivityIntent);
                    }
                    break;
                case GetuiAppViewIndex.MerchantServiceDetail:
                    //入口已隐藏，暂时不需要
                    break;
                case GetuiAppViewIndex.ShareActivity:
                    //入口已经隐藏，暂时不需要
                    break;
                case GetuiAppViewIndex.PersonalInfoActivity:
                    startActivity(new Intent(this, PersonalMessageActivity.class));
                    break;
                case GetuiAppViewIndex.PersonCenterFragment:
                    mainBottomNavigation.setBottomNavigationClick(MainBottomNavigation.MY);
                    break;
                case GetuiAppViewIndex.SearchActivity:
                    mMainContentFragment.searchText();
                    break;
                case GetuiAppViewIndex.MyQrCodeActivity:
                    startActivity(new Intent(this, MyQrCodeActivity.class));
                    break;
                case GetuiAppViewIndex.MainActivityForCallLogFragment:
                    startActivity(new Intent(this, CallLogsActivity.class));
                    break;
                case GetuiAppViewIndex.MoreActivity://更多页面
                    startActivity(new Intent(this, MoreActivity.class));
                    break;
                case GetuiAppViewIndex.FeedbackActivity://投诉反馈页面
                    startActivity(new Intent(this, FeedbackActivity.class));
                    break;
                case GetuiAppViewIndex.CouponActivity://我的优惠券页面
                    startActivity(new Intent(this, NewRedPacketActivity.class));
                    break;
                case GetuiAppViewIndex.PlaceOrderActivity:
                    if (pushInfo.getId() != null && !TextUtils.isEmpty(pushInfo.getType())) {
                        Intent placeOrderActivityIntent = new Intent(this, PlaceOrderActivity.class);
                        int type = IServiceInfo.TYPE_WORKER;
                        if (TextUtils.equals("3", pushInfo.getType()))
                            type = IServiceInfo.TYPE_BUSINESS;
                        placeOrderActivityIntent.putExtra("ID", pushInfo.getId());
                        placeOrderActivityIntent.putExtra("PriceType", type);
                        startActivity(placeOrderActivityIntent);
                    } else {
                        startActivity(new Intent(this, ConfirmOrderActivity.class));
                    }
                    break;
                case GetuiAppViewIndex.PayActivity:
                    if (pushInfo.getOrder() != null && !TextUtils.isEmpty(pushInfo.getOrder().getOrderId())) {
                        Intent payActivityIntent = new Intent(this, PayActivity.class);
                        payActivityIntent.putExtra(Constant.OrderId, pushInfo.getOrder().getOrderId());
                        startActivity(payActivityIntent);
                    }
                    break;
                case GetuiAppViewIndex.OrderManageActivity://订单管理页
                    mainBottomNavigation.setBottomNavigationClick(MainBottomNavigation.ORDER);
                    break;
                case GetuiAppViewIndex.MyCommentActivity://我的评价页面
                    startActivity(new Intent(this, MyCommentActivity.class));
                    break;
                case GetuiAppViewIndex.AccountActivity://账户页面
                    startActivity(new Intent(this, WalletActivity.class));
                    break;
                case GetuiAppViewIndex.RechargeActivity://充值页面
                    startActivity(new Intent(this, RechargeChooseActivity.class));
                    break;
                case GetuiAppViewIndex.ConfirmComplete://确认完成
                    break;
                case GetuiAppViewIndex.InvitationCode://输入邀请码
                    if (pushInfo.getRecommendInfoRegister() != null && !TextUtils.isEmpty(pushInfo.getRecommendInfoRegister().getRecommendCode())) {
                        mMainContentFragment.showEditDialog(pushInfo.getRecommendInfoRegister().getRecommendCode());
                    }
                    break;
                case GetuiAppViewIndex.NotifyCustomerPayment: //通知客户支付
                    if (pushInfo.getOrderToPay() != null && !TextUtils.isEmpty(pushInfo.getOrderToPay().getOrderId())) {
                        Intent intent = new Intent(MainActivity.this, PayActivity.class);
                        intent.putExtra(Constant.OrderId, pushInfo.getOrderToPay().getOrderId());//传递
                        startActivity(intent);
                    }
                    break;
            }
        }
    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        if (null != savedInstanceState) {
//            Intent intent = new Intent(this, SplashActivity.class);
////            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        } else {
//            super.onCreate(savedInstanceState);
//        }
//        LogUtils.i("TAG", "MainActivity:onCreate" + savedInstanceState);
//
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            handlePushInfo(pushInfo);
    }


    private void handlePushInfo(PushInfo pushInfo) {
        if (pushInfo == null) return;
        String appViewId = pushInfo.getAppViewId();
        if (appViewId.isEmpty()) return;
        if (Integer.valueOf(appViewId) == GetuiAppViewIndex.ConfirmComplete) {
            mMainContentFragment.showConfirmCompleleteWindow(pushInfo);
        }
    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        mComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .bannerModule(new BannerModule())
                .firstOrderInfoModule(new FirstOrderInfoModule())
                .build();
        mComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public MainComponent getComponent() {
        return mComponent;
    }

    @Override
    public void version(int count, String version) {
        activityCount = count;
    }


    private int activityCount;

    @Override
    public void renderBanner(List<BannerInfo> bannersInfo) {
    }


    @Subscribe
    public void onMainThread(CloseDialogEvent closeDialogEvent) {
        if (closeDialogEvent != null) {
            LoginDialogFragment loginDialogFragment = getLoginDialogFragment();
            if (loginDialogFragment != null && loginDialogFragment.getDialog() != null && loginDialogFragment.getDialog().isShowing())
                loginDialogFragment.dismiss();
        }
    }

    /**
     * 首页打开，活动弹框界面【如，新手红包等】
     *
     * @param firstOpenAppInfo
     */
    @Override
    public void render(FirstOpenAppInfo firstOpenAppInfo) {
    }

    @Override
    public void popuver(String version) {
        SharedPreferences preferences = getContext().getSharedPreferences("POPU_VERSION", MODE_PRIVATE);
        String lastVersion = preferences.getString("POPU_VERSION", "0");
        if (version.compareTo(lastVersion) > 0) { //如果有版本更新，客户端重新计数显示弹框的次数，并重新获取活动信息
            preferences.edit().putString("POPU_VERSION", version).commit();
            getFirstAppOpenInfo();//重新获取活动信息
            preferences = getContext().getSharedPreferences(global.getFirstPrefNewcomerActive(), MODE_PRIVATE);
            preferences.edit().putInt(global.PREF_KEY_FOR_NEWCOMER_ACTIVE, 1).apply();

        }
    }

    //首页活动，红包弹框接口
    @Override
    public void renderFirstOpenAppInfo(NewFirstOpenAppInfo newFirstOpenAppInfo) {
        this.newFirstOpenAppInfo = newFirstOpenAppInfo;
        if (this.newFirstOpenAppInfo != null && !TextUtils.isEmpty(this.newFirstOpenAppInfo.getImageUrl()) && !TextUtils.isEmpty(this.newFirstOpenAppInfo.getOpenUrl())) {
            //获取用户本地上一次活动记录
            NewFirstOpenAppInfo object = PreferencesUtil.getObject(StaticData.HISTORY_ACTION_INFO, NewFirstOpenAppInfo.class);
            Date currentDate = new Date();
            String dateStr = DateUtils.convertDateToStr(currentDate);
            if (object == null) {//首次获取
                object = newFirstOpenAppInfo;
                object.setActionTime(dateStr);
                object.setCurrentDialogShowNum(newFirstOpenAppInfo.getDayOfTimes() == -1 ? -1 : newFirstOpenAppInfo.getDayOfTimes() - 1);
                showActionDiaog();
            } else {
                //比较活动id和时间有变动再去更新弹框的显示次数，没有变动的话，就按照上一次规则显示
                if (!TextUtils.equals(object.getId(), newFirstOpenAppInfo.getId()) || !object.getActionTime().equals(dateStr)) {
                    object = newFirstOpenAppInfo;
                    object.setActionTime(dateStr);
                    object.setCurrentDialogShowNum(newFirstOpenAppInfo.getDayOfTimes() == -1 ? -1 : newFirstOpenAppInfo.getDayOfTimes());
                }
                //当天次数没用完或者无限次才可以弹框
                if (object.getCurrentDialogShowNum() < 0 || object.getCurrentDialogShowNum() > 0) {
                    showActionDiaog();
                }
                if (object.getCurrentDialogShowNum() != 0)
                    object.setCurrentDialogShowNum(object.getCurrentDialogShowNum() - 1);
            }
            PreferencesUtil.saveObject(StaticData.HISTORY_ACTION_INFO, object);
        }
    }


    public void showActionDiaog() {
        //显示弹框view 布局
        activityRel.setVisibility(View.VISIBLE);
        Glide.with(this).load(this.newFirstOpenAppInfo.getImageUrl()).error(R.mipmap.action_dialog_default).placeholder(R.mipmap.action_dialog_default).fitCenter().into(activityImageView);
    }


    /**
     * 显示红点
     */
    @Override
    public void addRedDot() {
        mainBottomNavigation.setTipRedDotShow(true);
    }


    /**
     * tab切换
     *
     * @param index
     */
    @Override
    public void onValueSelected(int index) {
        LogUtils.i("MainActivity:onValueSelected");
//        homeTabPay.setVisibility(index == MainBottomNavigation.TAB_HOME ? View.VISIBLE : View.GONE);
        switch (index) {
            case MainBottomNavigation.TAB_HOME: {
                LogUtils.i("TAG", "HomePage:showFragment\t" + homePageFragment.isAdded());
                showFragment(R.id.fragment_content, homePageFragment);
            }
            break;
            case MainBottomNavigation.NEARBY: {
                showFragment(R.id.fragment_content, mMainContentFragment);
            }
            break;
            case MainBottomNavigation.MY_TIP: {
                //设置小红点为隐藏
                mainBottomNavigation.setTipRedDotShow(false);
                if (NetUtils.isConnected(MainActivity.this)) {
                    //进入环信客服
                    ImLoginActivity.start(MainActivity.this, null);
                    showFragment(R.id.fragment_content, orderManageFragment);
                } else {
                    showMessage(R.string.network_error);
                }
                mainBottomNavigation.setBottomNavigationClick(MainBottomNavigation.TAB_HOME);
            }
            break;
            case MainBottomNavigation.ORDER: {
                showFragment(R.id.fragment_content, orderManageFragment);
                mainBottomNavigation.post(new Runnable() {
                    @Override
                    public void run() {
                        orderManageFragment.getOrderListPop();
                    }
                });
            }
            break;
            case MainBottomNavigation.MY: {
                showFragment(R.id.fragment_content, personalCenterFragment);
            }
            break;
        }
    }



    /**
     * 监听网络是否连接广播
     */
    public class NetWorkConnectionReceiver extends BroadcastReceiver {
        private int netStatus = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            netStatus = NetUtils.getNetWorkState(context);
            Activity currentActivity = SimpleTinkerInApplicationLike.getCurrentActivity();
            if (currentActivity != null && currentActivity instanceof BaseActivity && !currentActivity.isFinishing()) {
                //回调处于栈顶的activity的onNetWorkStateChage方法
                CommonBaseActivity activity = (CommonBaseActivity) currentActivity;
                activity.onNetWorkStateChange(netStatus);
            }
        }

        public boolean isConnected() {
            //无网络连接
            if (netStatus == NetUtils.NETWORK_NONE) {
                return false;
            } else {
                //WiFi连接或者移动网络连接
                return true;
            }
        }

    }

    //注册网络状态监听器
    private void initNetWorkStatusListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkConnectionReceiver, intentFilter);
    }

    @Override
    public void onNetWorkStateChange(int netStatus) {
        super.onNetWorkStateChange(netStatus);
        //如果首页信息没有缓存的请求下，网络加载失败，等网络恢复后再次请求
        //
//        getHomeListData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (commonAppPreferences != null)
            commonAppPreferences.setImSendAddress("0");
//        commonAppPreferences.setLocalAddress("","","");
        imLogout();
        //
        if (netWorkConnectionReceiver != null)
            unregisterReceiver(netWorkConnectionReceiver);
        SimpleTinkerInApplicationLike.clearActivity();
        //
        android.os.Process.killProcess(android.os.Process.myPid());
        LogUtils.i("TAG", "MainActivity:onDestroy()");
    }


    private void imLogout() {
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(int i, String s) {
                ChatClient.getInstance().logout(false, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (homePageFragment != null) {
            homePageFragment.onActivityResult(requestCode, resultCode, data);
        }
        switch (requestCode) {
            case StaticData.ORDER_DETAILS_FOR_RESULT:
                //⦁	进入订单详情页，再返回上一页，返回的是“订单列表”页
                mainBottomNavigation.setBottomNavigationClick(MainBottomNavigation.ORDER);
                break;


        }
    }
}

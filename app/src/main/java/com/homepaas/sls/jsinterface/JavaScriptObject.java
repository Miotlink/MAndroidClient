package com.homepaas.sls.jsinterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.R;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.component.DaggerPersonalInfoComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.event.DemandRemarkEvent;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.event.RefreshPersonalCenterFragmentEvent;
import com.homepaas.sls.event.ZoomActivity;
import com.homepaas.sls.jsinterface.entity.Command;
import com.homepaas.sls.jsinterface.entity.CommonWebInteractiveEntity;
import com.homepaas.sls.jsinterface.entity.DetailCommand;
import com.homepaas.sls.jsinterface.entity.DetailPricCommand;
import com.homepaas.sls.jsinterface.entity.DirectOrderCommand;
import com.homepaas.sls.jsinterface.entity.GoBackFirstWebEntity;
import com.homepaas.sls.jsinterface.entity.JsBridgeEntity;
import com.homepaas.sls.jsinterface.entity.NegotiableCommand;
import com.homepaas.sls.jsinterface.entity.ObjectApns;
import com.homepaas.sls.jsinterface.entity.PricingCommand;
import com.homepaas.sls.jsinterface.entity.RedCoupsEntity;
import com.homepaas.sls.jsinterface.entity.SuccessCommand;
import com.homepaas.sls.mvp.presenter.jsinterface.JsPresenter;
import com.homepaas.sls.mvp.view.jsinterface.JsInterfaceView;
import com.homepaas.sls.ui.FirstOpenAppActivity;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.WebViewActivity;
import com.homepaas.sls.ui.account.RechargeChooseActivity;
import com.homepaas.sls.ui.account.WalletActivity;
import com.homepaas.sls.ui.comment.MyCommentActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.homepage_3_3_0.DetailWebActivity;
import com.homepaas.sls.ui.imchating.ImLoginActivity;
import com.homepaas.sls.ui.login.FastLoginActivity;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.login.RegisterActivity;
import com.homepaas.sls.ui.newdetail.MerchantWorkerActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.CommonPlaceOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ExpressOrderTrackingActivity;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.order.servicemerchant.ServiceMerchantActivity;
import com.homepaas.sls.ui.personalcenter.CallLogsActivity;
import com.homepaas.sls.ui.personalcenter.collection.NewCollectionActivity;
import com.homepaas.sls.ui.personalcenter.more.FeedbackActivity;
import com.homepaas.sls.ui.personalcenter.more.MoreActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.PersonalMessageActivity;
import com.homepaas.sls.ui.qrcode.MyQrCodeActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewRedPacketActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.UseNewRedPacketActivity;
import com.homepaas.sls.ui.search.AllCategoriesActivity;
import com.homepaas.sls.ui.search.NewSearchActivity;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.MODE_CHOOSE;

/**
 * Created by Administrator on 2016/7/11.
 */

public class JavaScriptObject implements JsInterfaceView {

    private static final String TAG = "JavaScriptObject";
    public static final String JSInteractor = "JSInteractor";
    private Activity mContext;
    static Gson gson = new Gson();
    @Inject
    JsPresenter jsPresenter;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    private boolean isRecommendationCode = false;
    private LoginDialogFragment loginDialogFragment;
    private CommonAppPreferences commonAppPreferences;

    /**
     * Instantiate the interface and set the context
     */
    public JavaScriptObject(Activity c) {
        mContext = c;
        DaggerPersonalInfoComponent.builder().activityModule(new ActivityModule(c))
                .applicationComponent(((CommonBaseActivity) c).getApplicationComponent()).build().inject(this);
        jsPresenter.setJsInterfaceView(this);
        commonAppPreferences = new CommonAppPreferences(mContext);
    }

    @JavascriptInterface
    public void openAndroidRegist(String json) {
        Command data = gson.fromJson(json, Command.class);
        if (TextUtils.equals(Protocol.Register, data.getStatus().getCode())) {
            Protocol.loginSuccessUrl = data.getData().getUrl();
            Intent intent = new Intent(mContext, RegisterActivity.class);
            intent.putExtra(JSInteractor, Protocol.loginSuccessUrl);
            mContext.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void successGetAndroidTicket(String json) {
        SuccessCommand data = gson.fromJson(json, SuccessCommand.class);
        ((FirstOpenAppActivity) mContext).onSuccessGetTicket(data.getData().getUrl());
    }

    @JavascriptInterface
    public void closeAndroidWebView(String json) {
        ((FirstOpenAppActivity) mContext).finish();
    }

    @JavascriptInterface
    public void getHongBaoAndroidSucceed(String json) {
        ((FirstOpenAppActivity) mContext).onSuccessGetHongbao();
    }


    /**
     * 返回token给h5
     *
     * @param json
     */
    @JavascriptInterface
    public void getAndroidToken(String json) {
        Log.i(TAG, "getAndroidToken: " + json);
        Command command = gson.fromJson(json, Command.class);
        if (TextUtils.equals(Protocol.getAndroidToken, command.getStatus().getCode())) {
            jsPresenter.getToken();
        }
    }

    @JavascriptInterface
    public void openAndroidLogin(String data) {
        Log.i(TAG, "openAndroidLogin: " + data);
        Command command = gson.fromJson(data, Command.class);
        Intent intent = new Intent(mContext, FastLoginActivity.class);
        intent.putExtra(JSInteractor, command.getData().getUrl());
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void checkMyReward() {
        isRecommendationCode = true;
        jsPresenter.getToken();
    }

    @JavascriptInterface
    public void closeWebViewActivity() {
        EventBus.getDefault().post(new RefreshPersonalCenterFragmentEvent());
        ((WebViewActivity) mContext).finish();

    }

    @JavascriptInterface
    public void shareAndroidTicket(String json) {
        Command shareInfo = gson.fromJson(json, Command.class);
        ((FirstOpenAppActivity) mContext).share((FirstOpenAppActivity) mContext, shareInfo);
    }

    @JavascriptInterface
    public void androidZoomWebView(String json) {
        RedCoupsEntity redCoupsEntity = gson.fromJson(json, RedCoupsEntity.class);
        if (redCoupsEntity != null && !TextUtils.isEmpty(redCoupsEntity.getBody().getTitle()) && !TextUtils.isEmpty(redCoupsEntity.getBody().getUrl())) {
            PushInfo pushInfo = new PushInfo();
            pushInfo.setTitle(redCoupsEntity.getBody().getTitle());
            pushInfo.setUrl(redCoupsEntity.getBody().getUrl());
            pushInfo.setIsShare(null);
            WebViewActivity.start(mContext, pushInfo);
            EventBus.getDefault().post(new ZoomActivity());
        }
    }

    @JavascriptInterface
    public void androidGoToCreateOrder(String json) {
//        json="{\"TypeId\":\"136\",\"TypeName\":\"空调清洗\",\"Children\":[{\"TypeId\":\"300\",\"TypeName\":\"挂式空调清洗\",\"Children\":null},{\"TypeId\":\"301\",\"TypeName\":\"立式空调清洗\",\"Children\":null},{\"TypeId\":\"302\",\"TypeName\":\"中央空调清洗\",\"Children\":null}]}";
        DirectOrderCommand directOrderCommand = gson.fromJson(json, DirectOrderCommand.class);
        if (directOrderCommand != null) {
            if (!TextUtils.isEmpty(directOrderCommand.getToken())) {
                try {
                    personalInfoPDataSource.saveToken(directOrderCommand.getToken());
                } catch (PersistDataException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
            intent.putExtra("h5service", directOrderCommand);
            mContext.startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
//            intent.putExtra("h5service",command);
            mContext.startActivity(intent);
        }
    }

    /**
     * H5跳转到支付页面
     *
     * @param json
     */
    @JavascriptInterface
    public void androidjsBridge(String json) {
        JsBridgeEntity jsBridgeEntity = gson.fromJson(json, JsBridgeEntity.class);
        if (jsBridgeEntity != null && !TextUtils.isEmpty(jsBridgeEntity.getOrderId())) {
            Intent intent = new Intent(mContext, PayActivity.class);
            intent.putExtra(com.homepaas.sls.ui.order.Constant.OrderId, jsBridgeEntity.getOrderId());//传递
            intent.putExtra(StaticData.ConfirmGO, "0");
            mContext.startActivity(intent);
        }
    }


    //FIXME!!!只能用在WebViewActivity
    @JavascriptInterface
    public void androidSetShare(String str) {
        if (mContext instanceof WebViewActivity) {
            if ("1".equals(str)) {
                ((WebViewActivity) mContext).setShare(str);
            } else {
                ((WebViewActivity) mContext).setShare("0");
            }

        }
    }

    @JavascriptInterface
    public void closeWindow() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).finish();
            ((Activity) mContext).overridePendingTransition(0, R.anim.em_activity_close);
        }
    }

    @Override
    public void
    onToken(String token) {
        Log.i(TAG, "onToken: " + (token == null));
        ((FirstOpenAppActivity) mContext).onSuccessToken(token);
        if (isRecommendationCode) {
            ((WebViewActivity) mContext).loadNewUrl(token);
            isRecommendationCode = false;
        }
    }


    //以下是3.3.0服务详情页面H5交互操作
    @JavascriptInterface
    public void androidIsLogin(String json) {
        NegotiableCommand negotiableCommand = gson.fromJson(json, NegotiableCommand.class);
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        if (negotiableCommand != null && !TextUtils.isEmpty(negotiableCommand.getData().getUrl())) {
            if (TextUtils.isEmpty(token)) {
                if (mContext instanceof WebViewActivity) {
                    ((WebViewActivity) mContext).showLoginDialog(negotiableCommand.getData().getUrl());
                } else {
                    if (mContext instanceof DetailWebActivity)
                        ((DetailWebActivity) mContext).showLoginDialog(negotiableCommand.getData().getUrl());
                    if (mContext instanceof ServiceMerchantActivity)
                        ((ServiceMerchantActivity) mContext).showLoginDialog(negotiableCommand.getData().getUrl());

                }
            } else {
                if (mContext instanceof WebViewActivity) {
                    ((WebViewActivity) mContext).reLoadUrl(negotiableCommand.getData().getUrl());
                } else {
                    if (mContext instanceof DetailWebActivity)
                        ((DetailWebActivity) mContext).loadNewUrl(negotiableCommand.getData().getUrl());
                    if (mContext instanceof ServiceMerchantActivity)
                        ((ServiceMerchantActivity) mContext).loadNewUrl(negotiableCommand.getData().getUrl());
                }
            }
        }

    }

    private String token;
    private String telPhone;

    @JavascriptInterface
    public String androidGetDetailData() {
        ServiceItem serviceItem;
        if (mContext instanceof DetailWebActivity) {
            serviceItem = ((DetailWebActivity) mContext).getServiceItem();
        } else {
            serviceItem = ((ServiceMerchantActivity) mContext).getServiceItem();
        }
        try {
            token = personalInfoPDataSource.getToken();
            telPhone = personalInfoPDataSource.getTelphone();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        DetailCommand detailCommand = new DetailCommand(token, serviceItem.getServiceId(), telPhone);
        return gson.toJson(detailCommand);
    }

    @JavascriptInterface
    public void androidPushOrderId(String json) {//面议服务
        NegotiableCommand negotiableCommand = gson.fromJson(json, NegotiableCommand.class);
        if (negotiableCommand != null && !TextUtils.isEmpty(negotiableCommand.getData().getOrderId())) {
            ClientOrderDetailActivity.start(mContext, negotiableCommand.getData().getOrderId());
            ActivityCompat.finishAfterTransition(mContext);
        }

    }

    @JavascriptInterface
    public void androidToConfirmOrder(String json) {//定价服务
        PricingCommand pricingCommand = gson.fromJson(json, PricingCommand.class);
        if (pricingCommand != null && !TextUtils.isEmpty(pricingCommand.getData().getServiceId()) && !TextUtils.isEmpty(pricingCommand.getData().getServiceName())) {
            String serviceId = pricingCommand.getData().getServiceId();
            String serviceName = pricingCommand.getData().getServiceName();
            if (mContext instanceof ServiceMerchantActivity)//非标订单服务tab 单击立即预约按钮进入下单页，相当于 助家为您推荐商家一样的逻辑
            {
                CommonPlaceOrderActivity.start(mContext, serviceId, serviceName, "0", "-1", "", serviceId, "",serviceId);
            } else {
                CommonPlaceOrderActivity.start(mContext, serviceId, serviceName, "0", "", "", "", "",serviceId);
            }
//            ActivityCompat.finishAfterTransition((DetailWebActivity)mContext);
        }

    }

    @JavascriptInterface
    public String androidGetServiceId() {//定价服务
        ServiceItem serviceItem;
        if (mContext instanceof DetailWebActivity) {
            serviceItem = ((DetailWebActivity) mContext).getServiceItem();
        } else {
            serviceItem = ((ServiceMerchantActivity) mContext).getServiceItem();
        }

        CommonAppPreferences commonAppPreferences = new CommonAppPreferences(mContext);
        String lat = commonAppPreferences.getLatitude();
        String lng = commonAppPreferences.getLongitude();
        Log.e("serviceId",serviceItem.getServiceId()+"-------------");
        DetailPricCommand detailPricCommand = new DetailPricCommand(serviceItem.getServiceId(), lng, lat);
        return gson.toJson(detailPricCommand);
    }

    @JavascriptInterface
    public String androidPushToken() {
        String token = "";
        try {
            token = personalInfoPDataSource.getToken();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(token)) {
            ((WebViewActivity) mContext).showLoginDialog();
        }
        return token;
    }

    @JavascriptInterface
    public void androidGoBackFirstWeb(String json) {
        GoBackFirstWebEntity goBackFirstWebEntity = gson.fromJson(json, GoBackFirstWebEntity.class);
        if (goBackFirstWebEntity != null) {
            ((WebViewActivity) mContext).goBackFirstWeb(goBackFirstWebEntity.getBody().getMessage());
        }
    }

    @JavascriptInterface
    public void closeAndroidWebView() {
        mContext.finish();
//        if (mContext instanceof WebViewActivity) {
//            ((WebViewActivity) mContext).finish();
//        } else if (mContext instanceof DetailWebActivity) {
//            ((DetailWebActivity) mContext).finish();
//        } else if (mContext instanceof GeneralWebViewActivity) {
//            ((GeneralWebViewActivity) mContext).finish();
//        } else if (mContext instanceof FirstOpenAppActivity) {
//            ((FirstOpenAppActivity) mContext).finish();
//        } else if (mContext instanceof ForwardWebViewActivity) {
//            ((ForwardWebViewActivity) mContext).finish();
//        }
    }


    /**
     * 通用交互，以后交互都用这个
     * <p>
     * h5传递数据客户端，规范如下地址
     * http://192.168.1.191:3001/Web/app_standard/CommunicationStandard.html
     */
    @JavascriptInterface
    public String androidWebJsHandle(String json) {
        String token = "";
        try {
            token = personalInfoPDataSource.getToken();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        CommonWebInteractiveEntity commonWebInteractiveEntity = gson.fromJson(json, CommonWebInteractiveEntity.class);
        if (commonWebInteractiveEntity != null && commonWebInteractiveEntity.getBody() != null && commonWebInteractiveEntity.getBody().getObjectApns() != null) {
            ObjectApns objectApns = commonWebInteractiveEntity.getBody().getObjectApns();
            String type = objectApns.getType();
            String appViewId = objectApns.getAppViewId();
            if (!TextUtils.isEmpty(type)) {
                if (TextUtils.equals(type, "0")) {//默认信息
                    if (!TextUtils.isEmpty(objectApns.getNeedAlert()) && !TextUtils.equals("1", objectApns.getNeedAlert())) {
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(mContext, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("ObjectApns", objectApns);
                        intent.putExtras(bundle2);
                        mContext.startActivity(intent);
                    }
                } else if (TextUtils.equals(type, "1") && !TextUtils.isEmpty(appViewId)) {//内链接
                    return webToApp(objectApns, token);
                } else if (TextUtils.equals(type, "2")) {//跳转新web
                    String newurl = objectApns.getJumpUrl();
                    if (!TextUtils.isEmpty(newurl)) {
                        if (mContext instanceof WebViewActivity)
                            ((WebViewActivity) mContext).reLoadUrl(newurl);
                        if (mContext instanceof DetailWebActivity)
                            ((DetailWebActivity) mContext).loadNewUrl(newurl);
                        if (mContext instanceof ServiceMerchantActivity) {
                            ((ServiceMerchantActivity) mContext).loadNewUrl(newurl);
                        }
                    }
//                    h5ToH5(objectApns);
                } else {//此web刷新url
                    String newurl = objectApns.getJumpUrl();
                    if (!TextUtils.isEmpty(newurl)) {
                        if (mContext instanceof WebViewActivity)
                            ((WebViewActivity) mContext).reLoadUrl(newurl);
                        if (mContext instanceof DetailWebActivity)
                            ((DetailWebActivity) mContext).loadNewUrl(newurl);
                        if (mContext instanceof ServiceMerchantActivity)
                            ((ServiceMerchantActivity) mContext).loadNewUrl(newurl);
                    }
                }
            }
        }
        return token;
    }

    private void h5ToH5(ObjectApns objectApns) {
        ServiceItem serviceItem = objectApns.getServiceItem();
        if (serviceItem == null) {
            //跳转到全部类目页面
            AllCategoriesActivity.start(mContext, null, null);
        } else if (com.homepaas.sls.domain.param.Constant.SERVICE_PRODUCT.equals(serviceItem.getStructureType())) {
            //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
            if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
                //跳转详情页面
                DetailWebActivity.start(mContext, serviceItem);
            } else {
                //跳转到非标订单详情页面
                ServiceMerchantActivity.start(mContext, serviceItem);
            }
        }
    }

    /**
     * H5跳转到app各个界面
     *
     * @param objectApns
     */
    private String webToApp(ObjectApns objectApns, String token) {
        int jumpCode = Integer.parseInt(objectApns.getAppViewId());
        switch (jumpCode) {
            case JavaScriptInteractiveIndex.loginInWebview:
                if (objectApns != null && objectApns.getLoginBody() != null) {
                    if (!TextUtils.isEmpty(objectApns.getLoginBody().getToken())) {
                        try {
                            personalInfoPDataSource.saveToken(objectApns.getLoginBody().getToken());
                            PreferencesUtil.saveBoolean(StaticData.USER_LOGIN_STATUE, true);
                            EventPersonalInfo info = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
                            info.setLogin(true);
                            EventBus.getDefault().postSticky(info);
                        } catch (PersistDataException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!TextUtils.isEmpty(objectApns.getLoginBody().getUserId())) {
                        GrowingIO growingIO = GrowingIO.getInstance();
                        growingIO.setCS1("user_id", objectApns.getLoginBody().getUserId());
                    }
                }
                break;
            case JavaScriptInteractiveIndex.getToken:
                break;
            case JavaScriptInteractiveIndex.DEMAND_REMARK://特殊服务类型填写需求后返回的需求备注信息
                mContext.finish();
                EventBus.getDefault().postSticky(new DemandRemarkEvent(objectApns.getContentAll().getContent()));
                break;
            case JavaScriptInteractiveIndex.loginPopupView: //登录弹窗,登录成功返回{"Token":"String"}
                if (TextUtils.isEmpty(token)) {
                    ((Activity) mContext).startActivityForResult(new Intent(mContext, FastLoginActivity.class), JavaScriptInteractiveIndex.loginPopupView);
                } else {
                    return token;
                }
                break;
            case JavaScriptInteractiveIndex.loginViewControlller: //登录界面，登录成功需要返回token
                if (TextUtils.isEmpty(token)) {
                    ((Activity) mContext).startActivityForResult(new Intent(mContext, FastLoginActivity.class), JavaScriptInteractiveIndex.loginViewControlller);
                } else {
                    return token;
                }
                break;
            case JavaScriptInteractiveIndex.registerViewController: //注册，可带入邀请码，成功返回{"Token":"String"}
                //3.4.0后隐藏注册入口
                Toast.makeText(mContext, "3.4.0后隐藏注册入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.homePageViewController: //首页
                Intent intent4 = new Intent();
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent4.setClass(mContext, MainActivity.class);
                intent4.putExtra(StaticData.HOME_PAGE_INDEX, 0);
                mContext.startActivity(intent4);
                break;
            case JavaScriptInteractiveIndex.textSearchServiceViewController: //首页文字搜索
                mContext.startActivity(new Intent(mContext, NewSearchActivity.class));
                ((Activity) mContext).finish();
                break;
            case JavaScriptInteractiveIndex.personalCenterViewController: //个人中心
                Intent intent6 = new Intent();
                intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent6.setClass(mContext, MainActivity.class);
                intent6.putExtra(StaticData.HOME_PAGE_INDEX, 4);
                mContext.startActivity(intent6);
                break;
            case JavaScriptInteractiveIndex.myQRCodeViewController: //我的二维码
                mContext.startActivity(new Intent(mContext, MyQrCodeActivity.class));
                break;
            case JavaScriptInteractiveIndex.mineInfoViewController: //个人信息
                mContext.startActivity(new Intent(mContext, PersonalMessageActivity.class));
                break;
            case JavaScriptInteractiveIndex.mineCallRecordViewController: //通话记录
                mContext.startActivity(new Intent(mContext, CallLogsActivity.class));
                break;
            case JavaScriptInteractiveIndex.mineCollectionViewController: //收藏的工人/商户，需要工人/商户id，工人or商户判断type
                mContext.startActivity(new Intent(mContext, NewCollectionActivity.class));
//                mContext.startActivity(new Intent(mContext, MyCommentActivity.class));
                break;
            case JavaScriptInteractiveIndex.mineShareViewController: //个人中心分享页面
                Toast.makeText(mContext, "已经隐藏分享入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.mineMoreViewController: //个人中心更多页面
                mContext.startActivity(new Intent(mContext, MoreActivity.class));
                break;
            case JavaScriptInteractiveIndex.problemFeedBackViewController: //问题反馈页面
                mContext.startActivity(new Intent(mContext, FeedbackActivity.class));
                break;
            case JavaScriptInteractiveIndex.couponCenterViewController: //红包列表界面，不需要返回H5
                mContext.startActivity(new Intent(mContext, NewRedPacketActivity.class));
                ((Activity) mContext).finish();
                break;
            case JavaScriptInteractiveIndex.useCouponCenterViewController://选择使用红包页面
                if (objectApns != null && objectApns.getDisCountInfo() != null) {
                    Intent intent2 = new Intent(mContext, UseNewRedPacketActivity.class);
                    intent2.putExtra(StaticData.TOTAL_MONEY, objectApns.getDisCountInfo().getTotalMoney());
                    intent2.putExtra(StaticData.IS_NOT_USE_COUPON, TextUtils.equals("0", objectApns.getDisCountInfo().getIsNotUseCoupon()));
                    intent2.putExtra(StaticData.COUPON_ID, objectApns.getDisCountInfo().getCouponId());
                    intent2.putExtra(StaticData.SERVICE_ID, objectApns.getDisCountInfo().getServiceId());
                    ((Activity) mContext).startActivityForResult(intent2, JavaScriptInteractiveIndex.useCouponCenterViewController);
                }
                break;

            case JavaScriptInteractiveIndex.createOrderViewController: //旧版一键下单
                if (objectApns != null && objectApns.getDirectOrderCommand() != null) {
                    DirectOrderCommand directOrderCommand = objectApns.getDirectOrderCommand();
                    if (!TextUtils.isEmpty(directOrderCommand.getToken())) {
                        try {
                            personalInfoPDataSource.saveToken(directOrderCommand.getToken());
                        } catch (PersistDataException e) {
                            e.printStackTrace();
                        }
                    }
                    Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                    intent.putExtra("h5service", directOrderCommand);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                } else {
                    Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }
                break;
            case JavaScriptInteractiveIndex.payForOrderViewController: //支付订单,需要订单id，返回{"PaySucceed":"/"} 失败成功
                if (objectApns != null && objectApns.getOrder2Pay() != null && objectApns.getOrder2Pay().getOrderId() != null) {
                    Intent intent = new Intent(mContext, PayActivity.class);
                    intent.putExtra(Constant.OrderId, objectApns.getOrder2Pay().getOrderId());
                    intent.putExtra(StaticData.ConfirmGO, "0");
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }
                break;
            case JavaScriptInteractiveIndex.orderManageViewController: //订单列表
                Intent intent5 = new Intent();
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent5.setClass(mContext, MainActivity.class);
                intent5.putExtra(StaticData.HOME_PAGE_INDEX, 3);
                mContext.startActivity(intent5);

                break;
            case JavaScriptInteractiveIndex.orderDetailViewControlller: //订单详情，需要订单id
                if (objectApns != null && objectApns.getOrder() != null) {
                    ClientOrderDetailActivity.start(mContext, objectApns.getOrder().getOrderId());
                    ((Activity) mContext).finish();
                }
                break;
            case JavaScriptInteractiveIndex.expressOrderDetailViewControlller://快递详情
                if (objectApns != null && objectApns.getOrder() != null) {
                    ExpressOrderTrackingActivity.start(mContext, objectApns.getOrder().getOrderId());
                    ((Activity) mContext).finish();
                }
                break;
            case JavaScriptInteractiveIndex.wokerDetailViewController: //工人详情，需要工人id
                if (objectApns != null && objectApns.getWorker() != null) {
                    MerchantWorkerActivity.start(mContext, Constant.SERVICE_TYPE_WORKER, objectApns.getWorker().getWorkerId());
                }
                break;
            case JavaScriptInteractiveIndex.merchantDetailViewController: //商户详情，需要商户id
                if (objectApns != null && objectApns.getMerchant() != null) {
                    MerchantWorkerActivity.start(mContext, Constant.SERVICE_TYPE_BUSINESS, objectApns.getMerchant().getMerchantId());
                }
                break;
            case JavaScriptInteractiveIndex.merchantServiceViewController: //商户服务详情，需要商户id
                Toast.makeText(mContext, "已经隐藏商户服务详情入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.messageTextViewController: //文字展示，需要消息内容
                Toast.makeText(mContext, "已经隐藏文字展示入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.mineEvaluationViewController: //我的评论
                mContext.startActivity(new Intent(mContext, MyCommentActivity.class));
                ((Activity) mContext).finish();
                break;
            case JavaScriptInteractiveIndex.accountManageViewController: //账户中心
                mContext.startActivity(new Intent(mContext, WalletActivity.class));
                ((Activity) mContext).finish();
                break;
            case JavaScriptInteractiveIndex.accountRechargeViewController: //充值中心
                mContext.startActivity(new Intent(mContext, RechargeChooseActivity.class));
                ((Activity) mContext).finish();
                break;
            case JavaScriptInteractiveIndex.invitationCodeAlertController: //输入邀请码
                Toast.makeText(mContext, "已经隐藏输入邀请码入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.pleasePayForOrder:  //提醒支付订单,需要订单id
                Toast.makeText(mContext, "已经隐藏提醒支付订单入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.getRedPackageSucceed: //提醒红包已领取成功，首页弹窗下次不再弹
                Toast.makeText(mContext, "已经隐藏提醒红包已领取成功入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.getAppToken: //返回{"Token":"String"} 如果没有登录 会提示登录
                Toast.makeText(mContext, "已经隐藏入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.webFillOut: //web弹窗时调用，变大填充整个界面
                Toast.makeText(mContext, "已经隐藏入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.getAccount:  //获取用户手机号
                Toast.makeText(mContext, "已经隐藏入口", Toast.LENGTH_SHORT).show();
                break;
            case JavaScriptInteractiveIndex.closeWeb:  //关闭弹窗或者web
                ((Activity) mContext).finish();
                break;
            case JavaScriptInteractiveIndex.newPlaceOrder: //新下单页面
                if (objectApns.getServiceOrder() != null && !TextUtils.isEmpty(objectApns.getServiceOrder().getServiceId())) {
                    if (mContext instanceof ServiceMerchantActivity)//非标订单服务tab 单击立即预约按钮进入下单页，相当于 助家为您推荐商家一样的逻辑
                    {
                        CommonPlaceOrderActivity.start(mContext, objectApns.getServiceOrder().getServiceId(), objectApns.getServiceOrder().getServiceName(), objectApns.getServiceOrder().getIsActivityHandle(), "-1", "",  objectApns.getServiceOrder().getServiceId(), "",objectApns.getServiceOrder().getServiceId());
                    } else {
                        CommonPlaceOrderActivity.start(mContext, objectApns.getServiceOrder().getServiceId(), objectApns.getServiceOrder().getServiceName(), objectApns.getServiceOrder().getIsActivityHandle(), "", "", "", "",objectApns.getServiceOrder().getServiceId());
                    }
                    ((Activity) mContext).finish();
                }
                break;
            case JavaScriptInteractiveIndex.webChooseAddress:  //H5获取地址
                Intent intent1 = new Intent(mContext, AddressManageActivity.class);
                intent1.putExtra(StaticData.MODE, MODE_CHOOSE);
                intent1.putExtra(StaticData.ADDRESS_POSITION, -1);
                ((Activity) mContext).startActivityForResult(intent1, JavaScriptInteractiveIndex.webChooseAddress);
                break;
            case JavaScriptInteractiveIndex.callBackVersionCode:  //返回versionCode给H5
                return commonAppPreferences.getVersionName();
            case JavaScriptInteractiveIndex.customerServiceController://小秘书
                if (objectApns != null && objectApns.getCustomerService() != null) {
                    ServiceItem serviceItem = new ServiceItem();
                    serviceItem.setServiceName(objectApns.getCustomerService().getName());
                    serviceItem.setPrice(objectApns.getCustomerService().getPrice());
                    serviceItem.setIconUrl(objectApns.getCustomerService().getImageUrl());
                    serviceItem.setDetailUrl(objectApns.getCustomerService().getDetailUrl());
                    ImLoginActivity.start(mContext, serviceItem);
                } else {
                    ImLoginActivity.start(mContext, null);
                }

                break;
            case JavaScriptInteractiveIndex.CLOSE_WEBVIEW:
                //关闭当前weView
                ((Activity) mContext).finish();
                break;
            case JavaScriptInteractiveIndex.h5toServiceDetailController:
                h5ToH5(objectApns);
                break;

            default:

        }
        return "";
    }

}

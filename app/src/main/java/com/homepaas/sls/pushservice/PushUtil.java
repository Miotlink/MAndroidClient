package com.homepaas.sls.pushservice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.domain.entity.AppViewPage;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.WebViewDetail;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.WebViewActivity;
import com.homepaas.sls.ui.account.RechargeActivity;
import com.homepaas.sls.ui.account.RechargeChooseActivity;
import com.homepaas.sls.ui.account.WalletActivity;
import com.homepaas.sls.ui.comment.MyCommentActivity;
import com.homepaas.sls.ui.detail.BusinessDetailActivity;
import com.homepaas.sls.ui.detail.WorkerDetailActivity;
import com.homepaas.sls.ui.login.LoginActivity;
import com.homepaas.sls.ui.login.RegisterActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.detail.DetailOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ErrandServiceDetailActivity;
import com.homepaas.sls.ui.order.directOrder.ExpressOrderTrackingActivity;
import com.homepaas.sls.ui.order.manage.OrderManageActivity;
import com.homepaas.sls.ui.order.orderplace.PlaceOrderActivity;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.personalcenter.CallLogsActivity;
import com.homepaas.sls.ui.personalcenter.EmptyMessageActivity;
import com.homepaas.sls.ui.personalcenter.collection.NewCollectionActivity;
import com.homepaas.sls.ui.personalcenter.more.FeedbackActivity;
import com.homepaas.sls.ui.personalcenter.more.MoreActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.PersonalMessageActivity;
import com.homepaas.sls.ui.qrcode.MyQrCodeActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewRedPacketActivity;
import com.homepaas.sls.util.StaticData;

import java.util.List;

import javax.inject.Singleton;

import static com.homepaas.sls.navigation.Navigator.BUSINESS_ID;
import static com.homepaas.sls.navigation.Navigator.MESSAGE_CONTENT;
import static com.homepaas.sls.navigation.Navigator.MESSAGE_TITLE;
import static com.homepaas.sls.navigation.Navigator.WORKER_ID;

/**
 * Created by Administrator on 2016/7/9.
 * 解析透传消息，添加点击通知的跳转意图
 */
@Singleton
public class PushUtil {
    private static final String TAG = "PushUtil";
    private static Gson gson = new Gson();
    private static final String HOMEPAAS_PROCESS_NAME = BuildConfig.APPLICATION_ID;


    public void parseMessage(Context context, String payloadData) {

        Log.i(TAG, "parseMessage: ------------EnterIn");
        if (payloadData == null) {
            return;
        }
        Log.i("PushInfo String:", payloadData);
        PushInfo pushInfo = gson.fromJson(payloadData, PushInfo.class);
        if (pushInfo != null) {
            if (isAppActive(context)) {        //app存活,直接跳转对应的页面
                if (BuildConfig.DEBUG)
                    Log.i(TAG, "parseMessage: ***********App Alive");
                parsePushInfo(context, pushInfo);
            } else {//app未启动
                if (BuildConfig.DEBUG)
                    Log.i(TAG, "parseMessage: ++++++++++App DEAD And Start");
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(HOMEPAAS_PROCESS_NAME);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PushInfo", pushInfo);
                launchIntent.putExtras(bundle);
                context.startActivity(launchIntent);
            }
        }
    }

    /**
     * 针对app没有启动的情况下，传递Bundle参数，解析Bundle内的信息
     *
     * @param bundle
     */
    public void parseBundle(Context context, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        PushInfo pushInfo = (PushInfo) bundle.getSerializable("PushInfo");
        if (pushInfo != null) {
            parsePushInfo(context, pushInfo);
        }
    }

    public void parsePushInfo(Context context, PushInfo pushInfo) {
        if ("1".equals(pushInfo.getJumpType())) {
            if (pushInfo != null) {
                if (!TextUtils.isEmpty(pushInfo.getNeedAlert())) {
                    if (TextUtils.equals(pushInfo.getNeedAlert(), "0")) {
                        startInternalView(context, pushInfo);
                    } else {
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(context, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("PushInfo", pushInfo);
                        intent.putExtras(bundle2);
                        context.startActivity(intent);
                    }
                } else {
                    startInternalView(context, pushInfo);
                }
            }
        } else if ("2".equals(pushInfo.getJumpType())) {
            String sharePic = pushInfo.getSharePic();
            String shareDescription = pushInfo.getShareDescription();
            String shareTitle = pushInfo.getShareTitle();
            String shareUrl = pushInfo.getShareUrl();
            String url = pushInfo.getUrl();
//            if (sharePic != null & shareDescription != null & shareTitle != null & shareUrl != null)
            if (url != null) {//没有链接则不跳转
                Log.i(TAG, "parsePushInfo: Jump-----WebView-------");
                startWebView(context, pushInfo);
            }
        }
    }

    public void startWebView(Context context, PushInfo pushInfo) {
        WebViewActivity.start(context, pushInfo);
    }

    public void startWebView(Context context, WebViewDetail webViewDetail) {
        WebViewActivity.start(context, webViewDetail);
    }

    public void startInternalView(Context context, PushInfo pushInfo) {//跳内页，考虑前台后台的因素
        Intent intent = new Intent();
//        if (!isAppActive(context))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Intent mainIntent = new Intent(context,MainActivity.class);
//        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int jumpCode = Integer.valueOf(pushInfo.getAppViewId());
        // TODO: 2016/7/14 跳到个人中心没做
        switch (jumpCode) {
            case GetuiAppViewIndex.DetailOrderActivity://订单详情
                if (pushInfo.getOrder() != null && !TextUtils.isEmpty(pushInfo.getOrder().getOrderId())) {
                    if (!TextUtils.isEmpty(pushInfo.getIsKdEOrder()) && TextUtils.equals("1", pushInfo.getIsKdEOrder())) {
                        intent.setClass(context, ExpressOrderTrackingActivity.class);
                        intent.putExtra(StaticData.ORDER_ID, pushInfo.getOrder().getOrderId());
                    } else if (!TextUtils.isEmpty(pushInfo.getIsKdEOrder()) && TextUtils.equals("2", pushInfo.getIsKdEOrder())) {
                        intent.setClass(context, ErrandServiceDetailActivity.class);
                        intent.putExtra(StaticData.ORDER_ID, pushInfo.getOrder().getOrderId());
                    } else {
                        intent.setClass(context, ClientOrderDetailActivity.class);
                        intent.putExtra(StaticData.ORDER_ID, pushInfo.getOrder().getOrderId());
                    }
                } else return;
                break;
            case GetuiAppViewIndex.LoginPopUpWindow:
                intent.setClass(context, MainActivity.class);
                intent.putExtra("LoginPopUpWindow", jumpCode);
                break;
            case GetuiAppViewIndex.LoginActivity:
                intent.setClass(context, LoginActivity.class);
                break;
            case GetuiAppViewIndex.RegisterActivity:
                if (pushInfo.getRecommendInfoRegister() != null && !TextUtils.isEmpty(pushInfo.getRecommendInfoRegister().getRecommendCode())) {
                    intent.setClass(context, RegisterActivity.class);
                    intent.putExtra("code", pushInfo.getRecommendInfoRegister().getRecommendCode());
                } else return;
                break;
            case GetuiAppViewIndex.MainAcitivity:
                intent.setClass(context, MainActivity.class);
                break;
            case GetuiAppViewIndex.MyCollectionActivity:
                intent.setClass(context, NewCollectionActivity.class);
                break;
            case GetuiAppViewIndex.MessageTextActivity:
                intent.setClass(context, EmptyMessageActivity.class);
                if (pushInfo.getMessage() != null) {
                    intent.putExtra(MESSAGE_TITLE, pushInfo.getMessage().getTitle());
                    intent.putExtra(MESSAGE_CONTENT, pushInfo.getMessage().getContent());
                } else return;
                break;
            case GetuiAppViewIndex.WorkerDetail:
                if (pushInfo.getWorker() != null && !TextUtils.isEmpty(pushInfo.getWorker().getWorkerId())) {
                    intent.setClass(context, WorkerDetailActivity.class);
                    intent.putExtra(WORKER_ID, pushInfo.getWorker().getWorkerId());
                } else return;
                break;
            case GetuiAppViewIndex.MerchantDetail:
                intent.setClass(context, BusinessDetailActivity.class);
                if (pushInfo.getMerchant() != null && !TextUtils.isEmpty(pushInfo.getMerchant().getMerchantId())) {
                    intent.putExtra(BUSINESS_ID, pushInfo.getMerchant().getMerchantId());
                } else return;
                break;
            case GetuiAppViewIndex.MerchantServiceDetail:
                //入口已隐藏，暂时不需要
                break;
            case GetuiAppViewIndex.ShareActivity:
                //入口已经隐藏，暂时不需要
                break;
            case GetuiAppViewIndex.PersonalInfoActivity:
                intent.setClass(context, PersonalMessageActivity.class);
                break;
            case GetuiAppViewIndex.PersonCenterFragment:
                intent.setClass(context, MainActivity.class);
                intent.putExtra("PersonCenterFragment", jumpCode);
                break;
            case GetuiAppViewIndex.SearchActivity:
                intent.setClass(context, MainActivity.class);
                intent.putExtra("SearchActivity", jumpCode);
                break;
            case GetuiAppViewIndex.MyQrCodeActivity:
                intent.setClass(context, MyQrCodeActivity.class);
                //需要获取PersonInfomodel(pic,code,name,phoneNumber等信息)
                break;
            case GetuiAppViewIndex.MainActivityForCallLogFragment:
                intent.setClass(context, CallLogsActivity.class);
                break;
            case GetuiAppViewIndex.MoreActivity://更多页面
                intent.setClass(context, MoreActivity.class);
                break;
            case GetuiAppViewIndex.FeedbackActivity://投诉反馈页面
                intent.setClass(context, FeedbackActivity.class);
                break;
            case GetuiAppViewIndex.CouponActivity://我的优惠券页面
                if (!TextUtils.isEmpty(pushInfo.getNeedAlert()) && TextUtils.equals(pushInfo.getNeedAlert(), "0")) {
                    intent.setClass(context, NewRedPacketActivity.class);
                } else return;
                break;
            case GetuiAppViewIndex.PlaceOrderActivity:
                if (pushInfo.getId() != null && pushInfo.getType() != null) {
                    intent.setClass(context, PlaceOrderActivity.class);
                    int type = IServiceInfo.TYPE_WORKER;
                    if (TextUtils.equals("3", pushInfo.getType()))
                        type = IServiceInfo.TYPE_BUSINESS;
                    intent.putExtra("ID", pushInfo.getId());
                    intent.putExtra("PriceType", type);
                } else {
                    intent.setClass(context, ConfirmOrderActivity.class);
                }
                break;
            case GetuiAppViewIndex.PayActivity:
                if (pushInfo.getOrder() != null && !TextUtils.isEmpty(pushInfo.getOrder().getOrderId())) {
                    intent.setClass(context, PayActivity.class);
                    intent.putExtra(Constant.OrderId, pushInfo.getOrder().getOrderId());
                } else {
                    return;
                }
//                if (pushInfo.getOrderToPay()!=null&&pushInfo.getOrderToPay().getPrice()!=null&&pushInfo.getOrderToPay().getServiceProviderName()!=null) {
//                    intent.putExtra("TotalMoney", pushInfo.getOrderToPay().getPrice());
//                    intent.putExtra("ServiceProviderName", pushInfo.getOrderToPay().getServiceProviderName());
//                }else return;
                break;
            case GetuiAppViewIndex.OrderManageActivity://订单管理页
//                intent.setClass(context, OrderManageActivity.class);
                intent.setClass(context, MainActivity.class);
                intent.putExtra("OrderManageFragment", jumpCode);
                break;
            case GetuiAppViewIndex.MyCommentActivity://我的评价页面
                intent.setClass(context, MyCommentActivity.class);
                break;
            case GetuiAppViewIndex.AccountActivity://账户页面
                intent.setClass(context, WalletActivity.class);
                break;
            case GetuiAppViewIndex.RechargeActivity://充值页面
                intent.setClass(context, RechargeChooseActivity.class);
                break;
            case GetuiAppViewIndex.ConfirmComplete://确认完成
                intent.setClass(context, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PushInfo", pushInfo);
                intent.putExtras(bundle);
                break;
            case GetuiAppViewIndex.InvitationCode://输入邀请码
                intent.setClass(context, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("InvitationCode", GetuiAppViewIndex.InvitationCode);
//                bundle1.putSerializable("PushInfo_Recommend",pushInfo);
                if (pushInfo.getRecommendInfoRegister() != null) {
                    bundle1.putString("code", pushInfo.getRecommendInfoRegister().getRecommendCode());
                }
                intent.putExtras(bundle1);
                break;
            case GetuiAppViewIndex.NotifyCustomerPayment: //通知客户支付
                intent.setClass(context, PayActivity.class);
                if (pushInfo.getOrderToPay() != null && !TextUtils.isEmpty(pushInfo.getOrderToPay().getOrderId())) {
                    intent.putExtra(Constant.OrderId, pushInfo.getOrderToPay().getOrderId());
                } else return;
                break;
            default:
                intent.setClass(context, MainActivity.class);
                break;
        }
        context.startActivity(intent);
//        if (jumpCode != GetuiAppViewIndex.MainAcitivity) //如果目标Activity不是主页，则需要优先启动主页
//            context.startActivities(new Intent[]{mainIntent, intent});//启动MainActivity，然后启动目标Activity
//        else context.startActivity(intent);
//        //目标是主页，则直接跳转主页
    }

    public void startInternalView(Context context, AppViewPage appViewPage) {//跳内页，考虑前台后台的因素
        Intent intent = new Intent();
        if (!isAppActive(context))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int jumpCode = Integer.valueOf(appViewPage.getAppViewId());
        // TODO: 2016/7/14 跳到个人中心没做
        switch (jumpCode) {
            case GetuiAppViewIndex.DetailOrderActivity://订单详情
                if (appViewPage.getOrder() != null && appViewPage.getOrder().getOrderId() != null) {
                    intent.setClass(context, DetailOrderActivity.class);
                    intent.putExtra(Constant.OrderId, appViewPage.getOrder().getOrderId());
                } else return;
                break;
            case GetuiAppViewIndex.LoginActivity:
                intent.setClass(context, LoginActivity.class);
                break;
            case GetuiAppViewIndex.RegisterActivity:
                intent.setClass(context, RegisterActivity.class);
                break;
            case GetuiAppViewIndex.MainAcitivity:
                intent.setClass(context, MainActivity.class);
                break;
            case GetuiAppViewIndex.MyCollectionActivity:
                intent.setClass(context, NewCollectionActivity.class);
                break;
            case GetuiAppViewIndex.PersonalInfoActivity:
                intent.setClass(context, PersonalMessageActivity.class);
                break;
            case GetuiAppViewIndex.MainActivityForCallLogFragment:
                intent.setClass(context, CallLogsActivity.class);
                break;
            case GetuiAppViewIndex.MoreActivity://更多页面
                intent.setClass(context, MoreActivity.class);
                break;
            case GetuiAppViewIndex.FeedbackActivity://投诉反馈页面
                intent.setClass(context, FeedbackActivity.class);
                break;
            case GetuiAppViewIndex.CouponActivity://我的优惠券页面
//                intent.setClass(context, CouponActivity.class);
                intent.setClass(context, NewRedPacketActivity.class);
                break;
            case GetuiAppViewIndex.PlaceOrderActivity:
                intent.setClass(context, PlaceOrderActivity.class);
                if (appViewPage.getServiceProvider() != null && appViewPage.getServiceProvider().getSpId() != null) {
                    int type = IServiceInfo.TYPE_WORKER;
                    if (TextUtils.equals("3", appViewPage.getServiceProvider().getSpType()))
                        type = IServiceInfo.TYPE_BUSINESS;
                    intent.putExtra("ID", appViewPage.getServiceProvider().getSpId());
                    intent.putExtra("PriceType", type);
                } else return;
                break;
            case GetuiAppViewIndex.PayActivity:
                intent.setClass(context, PayActivity.class);
                if (appViewPage.getOrder2Pay() != null && appViewPage.getOrder2Pay().getPrice() != null && appViewPage.getOrder2Pay().getSpName() != null) {
                    intent.putExtra("TotalMoney", appViewPage.getOrder2Pay().getPrice());
                    intent.putExtra("ServiceProviderName", appViewPage.getOrder2Pay().getSpName());
                } else return;
                break;
            case GetuiAppViewIndex.OrderManageActivity://订单管理页
                intent.setClass(context, OrderManageActivity.class);
                break;
            case GetuiAppViewIndex.MyCommentActivity://我的评价页面
                intent.setClass(context, MyCommentActivity.class);
                break;
            case GetuiAppViewIndex.AccountActivity://账户页面
                intent.setClass(context, WalletActivity.class);
                break;
            case GetuiAppViewIndex.RechargeActivity://充值页面
                intent.setClass(context, RechargeActivity.class);
                break;
            case GetuiAppViewIndex.ConfirmComplete://确认完成
                intent.setClass(context, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PushInfo", appViewPage);
                intent.putExtras(bundle);
                break;

            default:
                intent.setClass(context, MainActivity.class);
                break;
        }
//      context.startActivity(intent);
        if (jumpCode != GetuiAppViewIndex.MainAcitivity)//如果目标Activity不是主页，则需要优先启动主页
            context.startActivities(new Intent[]{mainIntent, intent});//启动MainActivity，然后启动目标Activity
        else context.startActivity(intent);//目标是主页，则直接跳转主页
    }

    /**
     * 判断所有进程总是否有助家生活进程，确定app是否存活
     *
     * @param context
     * @return
     */
    private boolean isAppActive(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i);
            if (runningAppProcessInfo.processName.equals(HOMEPAAS_PROCESS_NAME))
                return true;
        }
        return false;
    }


}

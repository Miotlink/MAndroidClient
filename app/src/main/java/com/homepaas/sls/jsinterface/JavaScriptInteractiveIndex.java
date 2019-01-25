package com.homepaas.sls.jsinterface;

/**
 * Created by JWC on 2017/6/1.
 */

public class JavaScriptInteractiveIndex {
    public static final int CLOSE_WEBVIEW = 10040;//10040    关闭当前webview
    public static final int loginInWebview = 998;
    public static final int getToken = 999;
    public static final int loginPopupView = 1000;//登录弹窗,登录成功返回{"Token":"String"}
    public static final int DEMAND_REMARK = 100172;//100172       特殊服务类型填写需求后返回的需求备注信息

    public static final int loginViewControlller = 1001;//登录界面，登录成功不返回

    public static final int registerViewController = 1002;//注册，可带入邀请码，成功返回{"Token":"String"}
    public static final int homePageViewController = 1003;//首页
    public static final int textSearchServiceViewController = 1004;//首页文字搜索
    public static final int personalCenterViewController = 1005;//个人中心
    public static final int myQRCodeViewController = 1006;//我的二维码
    public static final int mineInfoViewController = 1007;//个人信息
    public static final int mineCallRecordViewController = 1008;//通话记录
    public static final int mineCollectionViewController = 1009;//收藏的工人/商户，需要工人/商户id，工人or商户判断type
    public static final int mineShareViewController = 10010;//个人中心分享页面
    public static final int mineMoreViewController = 10011;//个人中心更多页面
    public static final int problemFeedBackViewController = 10012;//问题反馈页面

    public static final int couponCenterViewController = 10013;//红包界面
    public static final int createOrderViewController = 10014;//旧版一键下单
    public static final int payForOrderViewController = 10015;//支付订单,需要订单id，返回{"PaySucceed":"0/1"} 0失败1成功

    public static final int orderManageViewController = 10016;//订单列表
    public static final int orderDetailViewControlller = 10017;//订单详情，需要订单id
    public static final int expressOrderDetailViewControlller = 100171;//快递订单详情，需要订单id
    public static final int wokerDetailViewController = 10018;//工人详情，需要工人id
    public static final int merchantDetailViewController = 10019;//商户详情，需要商户id
    public static final int merchantServiceViewController = 10020;//商户服务详情，需要商户id
    public static final int messageTextViewController = 10021;//文字展示，需要消息内容
    public static final int mineEvaluationViewController = 10022;//我的评论
    public static final int accountManageViewController = 10023;//账户中心
    public static final int accountRechargeViewController = 10024;//充值中心
    public static final int invitationCodeAlertController = 10026;//输入邀请码
    public static final int pleasePayForOrder = 10027; //提醒支付订单,需要订单id
    public static final int getRedPackageSucceed = 10028;//提醒红包已领取成功，首页弹窗下次不再弹
    public static final int getAppToken = 10029;//返回{"Token":"String"} 如果没有登录 会提示登录
    public static final int webFillOut = 10030;//web弹窗时调用，变大填充整个界面
    public static final int getAccount = 10031; //获取用户手机号
    public static final int closeWeb = 10032; //关闭弹窗或者web

    public static final int newPlaceOrder = 10033; //新下单页面
    public static final int webChooseAddress = 10034; //服务地址页面
    public static final int callBackVersionCode = 10035;//返回versionCode给H5
    public static final int useCouponCenterViewController = 10036;//使用红包选择页面，跟订单相关，需要orderId
    public static final int customerServiceController = 10037;//小秘书页面
    public static final int h5toServiceDetailController = 10039;


}

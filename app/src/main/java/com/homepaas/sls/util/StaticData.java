package com.homepaas.sls.util;

/**
 * Created by JWC on 2017/2/22.
 */

public class StaticData {

    public static final String COLLEACTION_WORKER = "COLLEACTION_WORKER";
    public static final String COLLEACTION_MERCHANT = "COLLEACTION_MERCHANT";


    public static final String PAY_HISTORY_INFO = "PayHistoryInfo";

    public static final String HISTORY_ACTION_INFO = "HISTORY_ACTION_INFO";
    //forresult
    public static final int ORDER_DETAILS_FOR_RESULT = 100;
    public static final String USER_ID = "USER_ID";
    public static final String USER_SELECT_SERVICE_OR_WORKER_NAME = "USER_SELECT_SERVICE_OR_WORKER_NAME";


    // 商户主页到店支付成功网页返回到地图主页，订单详情到地图主页等
    public static final String RETURN_MAP_FRAGMENT = "renturnMapFragment";

    //用户是否登录布尔值标识
    public static final String USER_LOGIN_STATUE = "user_login_statue";
    //环信客服是否是登录状态 应用被杀死不会走activity的代码
    public static final String IS_IM_LOGOUT = "is_im_logout";
    public static final String SEND_CAPTCH_COUNT = "SendCaptchCount";
    public static final String CAPTCH_COUNT_ID = "CaptchCountId";
    public static final String CAPTCH_COUNT_PHONE = "CaptchCountPhone";
    public static final String IS_PAY_COUPON = "IsPayCoupon";
    //商户主页商户的id
    public static final String MERCHANT_SERVICE_ID = "merchantServiceId";

    //商户主页商户的名字
    public static final String MERCHANT_NAME = "merchantName";

    //商户主页地址
    public static final String MERCHANT_ADDRESS = "merchantAddress";

    //商户主页头像url
    public static final String MERCHANT_PHOTO_URL = "merchantPhotoUrl";

    //是否从定价服务(0)直接到支付页面，不经过订单详情页,(1）从订单列表过来
    public static String ConfirmGO = "ConfirmGo";

    //从搜索页面还是一键下单按钮进入一键下单键界面,(0搜索页面，1一键下单按钮)
    public static String SEARCH_OR_BUTTON = "searchOrButton";

    //搜索页面传入一键下单的serviceTypeId
    public static String SEARCH_SERVICE_TYPE_ID = "searchTypeId";

    //搜索页面传入一键下单的名子
    public static String SEARCH_SERVICE_NAME = "searchServiceName";

    //维度
    public static String LATITUDE = "latitude";

    //经度
    public static String LONGITUDE = "longitude";
    //city
    public static String CITY = "city";



    public static final String SECOND_LEVEL_SERVICE_ID = "secondLevelServiceId";

    //serviceId服务Id
    public static final String SERVICE_ID = "ServiceId";
    public static final String TOTAL_MONEY = "TotalMoney";
    public static final String IS_NOT_USE_COUPON = "IsNotUseCoupon";
    public static final String COUPON_ID = "coupon_id";

    public static final String EXPRESS_ORDER_ID = "expressOrderId";
    //service名字
    public static final String SERVICE_NAME = "serviceName";

    //服务地址选择position
    public static final String ADDRESS_POSITION = "address_position";
    public static final String ADDRESS = "ADDRESS";
    //当前定位的城市
    public static final String LOCATION_CITY = "LOCATION_CITY";
    //非标订单中用户定位的信息实体
    public static final String ADDRESS_LOCATION = "ADDRESS_LOCATION";
    //新下单传递服务的时间集合
    public static final String SCHEDULE_List = "scheduleList";

    //下单选择时间（2017/03/09 今天）返回值
    public static final String SERVICE_SCHEDULE = "serviceSchedule";

    //下单选择时间(18:00)返回值
    public static final String SERVICE_TIME = "serviceTime";
    public static final String SERVICE_INFO = "SERVICE_INFO";

    //地址查看模式
    public static final String MODE = "Mode";

    //环信的账号
    public static final String IM_USERNAME = "imUserName";

    //环信的密码
    public static final String IM_PWD = "imPwd";

    public static final String HOME_PAGE_INDEX = "homePageIndex";

    //用户登录状态返回值
    public static final String LOGIN_STATUE = "Status";

    //没登录的时候临时环信账号
    public static final String TEMPORARY_IM_USERNAME = "temporaryImUserName";

    //没登录的时候临时密码
    public static final String TEMPORARY_IM_PWD = "temporaryImPwd";

    //环信的IM服务号
    public static final String IM_SERVICE = "kefuchannelimid_787415";

    //老搜索历史记录
    public static final String OLD_HISTORY_SERVICE = "oldHistoryService";

    //新搜索历史记录
    public static final String NEW_HISTORY_SERVICE = "newHistoryService";

    //存储的sharedPreferences的key
    public static final String SPF_NAME = "spfName";

    //传递的服务
    public static final String SERVICE_ITEM = "serviceItem";

    //头像url
    public static final String HEAR_PORTIAIT = "headPortrait";

    //环信第一次发送地址后不再发送
    public static final String IM_SEND_ADDRESS = "imSendAddress";

    //备注
    public static final String ORDER_REMARK = "orderRemark";

    //是否是活动跳转到新下单页
    public static final String IS_ACTIVITY = "isActivity";


    //    二级服务id
    public static final String SECOND_LEVEL = "SECOND_LEVEL";

    //    类型，2：工人，3：商户
    public static final String PROVIDER_USER_TYPE = "providerUserType";
    //用户选择地址记录
    public static final String ADDRESS_ID = "ADDRESS_ID";
    //    二级服务id
    public static final String ORDER_TYPE = "ORDER_TYPE";
    //商家id
    public static final String MERCHANT_ID = "MERCHANT_ID";
    public static final String IS_ORDER_ACTIVITY = "IS_ORDER_ACTIVITY";
    public static final String MERCHANT_ORWORKER_NAME = "merchantOrWorkerName";


    //工人Id
    public static final String WORKER_ID = "workerId";

    //商户Id
    public static final String BUSINESS_ID = "businessId";
    //IM 欢迎语是否提示过
    public static final String IM_WELCOME = "im_welcome";
    //申请赔付选择原因的position
    public static final String REASON_SELECT_POSITION = "reasonSelectPosition";
    //申请赔付选择原因的ID
    public static final String REASON_SELECT_ID = "reasonSelectId";
    //工人迟到选择时间的position
    public static final String CHOOSE_LATE_TIME_POSITION = "lateTimeSelectPosition";
    //选中的是不是工人迟到这一项
    public static final String WORKER_LATE_ITEM = "workerLateItem";
    //订单Id
    public static final String ORDER_ID = "orderId";
    //查看赔付的原因
    public static final String CLAIMS_REASONS = "claimsReasons";


    //取消说明
    public static final String CANCEL_MEG = "cancelMsg";
    //版本号
    public static final String VERSION_NAME = "versionName";
    //支付的金额
    public static final String PAY_MONEY = "payMoney";
    //分页加载的订单或者其他加载更多的时候，pagesize=10；
    public static final String PAGE_SIZE = "10";
    //订单列表全部
    public static final String ORDER_LIST_ALL = "0";
    //订单列表待支付
    public static final String ORDER_LIST_TOPAY = "1";
    //订单列表待确认
    public static final String ORDER_LIST_TOCONFIRM = "2";
    //订单列表待评价
    public static final String ORDER_LIST_TOEVALUATE = "3";
    //订单列表待接单
    public static final String ORDER_LIST_TOTAKE = "4";
    //当前订单
    public static final String ORDER_LIST_CURRENT = "6";
    //历史订单
    public static final String ORDER_LIST_HISTORY = "7";

    public static final String CUR_CITY = "CurentCity";
    public static final String SELECTED_CITY = "SelectedCity";
    //订单编号
    public static final String ORDER_CODE = "orderCode";
    //    评价服务详情
    public static final String EVALUATION_SERVICE_DETAILS = "EVALUATION_SERVICE_DETAILS";

    //服务地址
    public static final String SERVICE_ADDRESS = "serviceAddress";
    //客户名字
    public static final String CLIENT_NAME = "clientName";
    //什么类型的订单
    public static final String IS_KD_ORDER = "isKdOrder";
    //全部服务
    public static final String ALL_SERVICES = "allServices";

    //历史订单
    public static final String HISTORY_ORDER = "HISTORY_ORDER";
    //全部订单
    public static final String ALL_ORDER_LIST = "ALL_ORDER_LIST";
    //待接单列表
    public static final String WAITING_LIST = "WAITING_LIST";
    //待付款列表
    public static final String OBLIGATION_LIST = "OBLIGATION_LIST";
    //待确认列表
    public static final String TBC_LIST = "TBC_LIST";
    //待评价
    public static final String NO_EVALUATE = "evaluate";
    //附近地图元素数据
    public static final String NEARBY = "nearby";
    //超级优惠
    public static final String DISCOUNTS = "discounts";
    //附近布尔值
    public static final String HAS_WHOLEMER_CHANT = "has_wholemer_chant";

    //城市列表
    public static final String CITY_LIST = "city_list";
    //缓存时间
    public static final String CACHE_TIME = "cacheTime";
    //首页banner
    public static final String BANNER_DATA = "bannerData";
    //首页
    public static final String HOME_DATA = "home_data";
    //首页缓存时间
    public static final String HOME_PAGE_CACHE_TIME = "homePageCacheTime";
    //首页8个小图标的缓存
    public static final String SHORT_CUT = "shortCut";
    //首页选项列表
    public static final String RECOMMOND_SERVICE = "recommendService";
    //定位地址
    public static final String LOCATION_ADDRESS = "locationAddress";
}

package com.homepaas.sls.domain.param;

/**
 * 定义交互用的枚举常量
 *
 * @author zhudongjie .
 */
public class Constant {



    public static final String IS_NEW_USER_PRF = "IS_NEW_USER_PRF";
    public static final String IS_NEW_USER_FIELD = "IS_NEW_USER_FIELD";
    public static final String NEW_USER_MESSAGE = "NEW_USER_MESSAGE";

    //for search
    public static final String SERVICE_TYPE_WORKER = "1";
    public static final String SERVICE_TYPE_BUSINESS = "2";
    public static final String SERVICE_TYPE_ALL = "0";

    public static final int SERVICE_TYPE_WORKER_INT = 1;
    public static final int SERVICE_TYPE_BUSINESS_INT = 2;
    public static final int SERVICE_TYPE_INT=0;
    public static final int WHOLE_SERVICE_TYPE_WORKER_INT = 3;
    public static final int WHOLE_SERVICE_TYPE_BUSINESS_INT = 4;



    public static final int REGISTER_TYPE_LOGIN = 1;
    public static final int REGISTER_TYPE_DEFAULT = 0;

    public static final int CAPTCHA_QUICK_LOGIN = 4;
    public static final int CAPTCHA_LOGIN = 3;
    public static final int CAPTCHA_PASSWORD = 2;
    public static final int CAPTCHA_REGISTER = 1;

    //search method
    public static final String QUERY_MODE_TEXT = "0";
    public static final String QUERY_MODE_VOICE = "1";
    public static final String QUERY_MODE_TYPE = "2";
    public static final String QUERY_MODE_DEFAULT = "3";

    public static final int SHARE_TYPE_PERSONAL_CENTER = 1;
    public static final int SHARE_TYPE_QRCODE = 2;
    public static final int SHARE_TYPE_WORKER_BUSINESS_MAIN_PAGE = 3;
    public static final int SHARE_TYPE_SERVICE_DETAIL = 4;
    public static final int SHARE_TYPE_ACTIVITY_PAGE = 5;
    public static final int SHARE_TYPE_MY_COUPON = 6;
    public static final int SHARE_TYPE_ORDER_DETAIL = 7;

    //used for evaluate
    public static final String EVALUATION_TYPE_WORKER = "2";
    public static final String EVALUATION_TYPE_BUSINESS = "3";

    public static final String PAY_TYPE_WORKER = "2";
    public static final String PAY_TYPE_BUSINESS = "3";


    //for order list
    public static final String ORDER_LIST_ALL = "0";
    public static final String ORDER_LIST_TOPAY = "1";
    public static final String ORDER_LIST_TOCONFIRM = "2";
    public static final String ORDER_LIST_TOEVALUATE = "3";
    public static final String ORDER_LIST_TOTAKE = "4";
    //orderFrom 订单来源：Android  ios
    public static final String ANDROID_TYPE = "0";

    //gender
    public static final String GENDER_MALE = "0";
    public static final String GENDER_FEMALE = "1";


    public static final String NEGOTIATE = "1";
    public static final String HOURLY_WORKER = "5";//小时工
    public static final String HOURLY_WORKER_WINDOW="733";//小时工（搽玻璃）
    public static final String SERVICE_TYPE_NEG = "0";
    public static final String SERVICE_TYPE_STABLE = "1";//面议

    /**
     * 	SellType String	是出售类型字段,
     * 	SERVICE_PRICE_SPECIFIC:定价类型(包含定价面议混合类型);
     * 	SERVICE_PRICE_UNDEFINED:面议类型;
     * 	SERVICE_SIMPLY:简单类型
     *  default: SERVICE_PRICE_SPECIFIC
     */
    public static final String SERVICE_PRICE_SPECIFIC = "1";
    public static final String SERVICE_PRICE_UNDEFINED = "0";
    public static final String SERVICE_SIMPLY = "2";

    /**
     * 	StructureType String是结构类型字段,
     * 	SERVICE_GROUP:组类型,对应二级;(跳转类目页面)
     * 	SERVICE_PRODUCT:商品类型,对应三级;（跳转详情页面）
     * 	SERVICE_KIND:种类类型,对应四级;（只出现在下单页面）
     * 	default: SERVICE_GROUP
     */
    public static final String SERVICE_GROUP = "0";
    public static final String SERVICE_PRODUCT = "1";
    public static final String SERVICE_KIND = "2";

    public static CharSequence NOT_EVALUATED="0";//未评论


    public static String booleanToString(boolean b) {
        return b ? "1" : "0";
    }

    public static int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static boolean intToBoolean(int i) {
        return i != 0;
    }

    public static boolean intStringToBoolean(String s) {
        return "1".equals(s);
    }



}

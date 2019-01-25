package com.homepaas.sls.data.network;

import com.homepaas.sls.data.entity.AddServiceAlipayEntity;
import com.homepaas.sls.data.entity.AddServiceWxpayEntity;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.data.entity.ComplaintListEntity;
import com.homepaas.sls.data.entity.HomeOrderStatusEntity;
import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.data.network.dataformat.AccountDetailBody;
import com.homepaas.sls.data.network.dataformat.AccountInfoBody;
import com.homepaas.sls.data.network.dataformat.AlipaySignBody;
import com.homepaas.sls.data.network.dataformat.AllOrderBody;
import com.homepaas.sls.data.network.dataformat.BusinessInfoBody;
import com.homepaas.sls.data.network.dataformat.BusinessTagsBody;
import com.homepaas.sls.data.network.dataformat.CollectedBusinessBody;
import com.homepaas.sls.data.network.dataformat.CollectedWorkerBody;
import com.homepaas.sls.data.network.dataformat.CouponContentsBody;
import com.homepaas.sls.data.network.dataformat.CreatedOrderBody;
import com.homepaas.sls.data.network.dataformat.EvaluationBody;
import com.homepaas.sls.data.network.dataformat.LifeServiceDataSegment;
import com.homepaas.sls.data.network.dataformat.MessageBody;
import com.homepaas.sls.data.network.dataformat.ModifyPhotoBody;
import com.homepaas.sls.data.network.dataformat.MyRedPacketBody;
import com.homepaas.sls.data.network.dataformat.OrderDetailBody;
import com.homepaas.sls.data.network.dataformat.OrderPayAliSignBody;
import com.homepaas.sls.data.network.dataformat.OrderPayWXSignBody;
import com.homepaas.sls.data.network.dataformat.PayDetailBody;
import com.homepaas.sls.data.network.dataformat.PersonalInfoDataSegment;
import com.homepaas.sls.data.network.dataformat.ProviderDetailBody;
import com.homepaas.sls.data.network.dataformat.QueryServiceRequest;
import com.homepaas.sls.data.network.dataformat.RechargeListBody;
import com.homepaas.sls.data.network.dataformat.ResetPasswordBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.SearchServiceBody;
import com.homepaas.sls.data.network.dataformat.ServiceContentBody;
import com.homepaas.sls.data.network.dataformat.ShareInfoBody;
import com.homepaas.sls.data.network.dataformat.ShortCutRequest;
import com.homepaas.sls.data.network.dataformat.UnreadMessageBody;
import com.homepaas.sls.data.network.dataformat.WeChatPayBody;
import com.homepaas.sls.data.network.dataformat.WorkerInfoBody;
import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.data.network.dataformat.request.AccountDetailsRequest;
import com.homepaas.sls.data.network.dataformat.request.AliPaySignRequest;
import com.homepaas.sls.data.network.dataformat.request.BusinessOrderServiceListRequest;
import com.homepaas.sls.data.network.dataformat.request.BusinessSercicePriceRequest;
import com.homepaas.sls.data.network.dataformat.request.BusinessServiceListRequest;
import com.homepaas.sls.data.network.dataformat.request.CallLogRequest;
import com.homepaas.sls.data.network.dataformat.request.CheckUpdateBody;
import com.homepaas.sls.data.network.dataformat.request.CollectBusinessRequest;
import com.homepaas.sls.data.network.dataformat.request.CollectWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.ComplaintRequest;
import com.homepaas.sls.data.network.dataformat.request.CreateDirectPayExRequest;
import com.homepaas.sls.data.network.dataformat.request.ExpressDetailRequest;
import com.homepaas.sls.data.network.dataformat.request.GetActivityExRequest;
import com.homepaas.sls.data.network.dataformat.request.GetAtStoreActivityRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessCommentRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessSecServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessServiceListRequest;
import com.homepaas.sls.data.network.dataformat.request.GetCouponContentsRequest;
import com.homepaas.sls.data.network.dataformat.request.GetDescriptionRequest;
import com.homepaas.sls.data.network.dataformat.request.GetKdTrackInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.GetOrderDetailRequest;
import com.homepaas.sls.data.network.dataformat.request.GetOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.GetStarLevelRequest;
import com.homepaas.sls.data.network.dataformat.request.IodOfAlipayRequest;
import com.homepaas.sls.data.network.dataformat.request.IodOfBalancePayRequest;
import com.homepaas.sls.data.network.dataformat.request.IodOfWxpayRequest;
import com.homepaas.sls.data.network.dataformat.request.LoginRequest;
import com.homepaas.sls.data.network.dataformat.request.MerchantServicePriceListRequest;
import com.homepaas.sls.data.network.dataformat.request.MessageListRequest;
import com.homepaas.sls.data.network.dataformat.request.PlaceOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.QueryActivityServicePriceRequest;
import com.homepaas.sls.data.network.dataformat.request.QueryServicePriceRequest;
import com.homepaas.sls.data.network.dataformat.request.RechargeInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.SearchHotServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.SearchServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.SelectServiceOrWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.ServiceIdRequest;
import com.homepaas.sls.data.network.dataformat.request.SettlementListRequest;
import com.homepaas.sls.data.network.dataformat.request.SubmitApplyClaimsRequest;
import com.homepaas.sls.data.network.dataformat.request.SubmitEvaluationRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.network.dataformat.request.TopicRequest;
import com.homepaas.sls.data.network.dataformat.request.TrackOrderStatusRequest;
import com.homepaas.sls.data.network.dataformat.request.WXPaySignRequest;
import com.homepaas.sls.data.network.dataformat.request.WorkerServicePriceListRequest;
import com.homepaas.sls.domain.entity.AccountDetailItemEntry;
import com.homepaas.sls.domain.entity.AccountListEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.AddressIdEntity;
import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.AliPaySignEntry;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.domain.entity.BusinessServicePricesEntity;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.CheckIsReceivedRedCoupsEntry;
import com.homepaas.sls.domain.entity.CityListEntity;
import com.homepaas.sls.domain.entity.CommonCouponInfo;
import com.homepaas.sls.domain.entity.CreateDirectPayExEntity;
import com.homepaas.sls.domain.entity.DescriptionInfo;
import com.homepaas.sls.domain.entity.DirectPayInfo;
import com.homepaas.sls.domain.entity.ExpressDetailOutputEntity;
import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.FirstOpenAppInfoEx;
import com.homepaas.sls.domain.entity.GetAtStoreActivityEntity;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.KdTrackInfoResponse;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.entity.NewFirstOpenAppInfo;
import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.PlaceOrderEntry;
import com.homepaas.sls.domain.entity.PopupVer;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.QueryServicePriceEntry;
import com.homepaas.sls.domain.entity.RechargeInfoResponse;
import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.domain.entity.RecommendServiceEntity;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.domain.entity.SelectServiceOrWorkerEntity;
import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.entity.ServiceTimeStartAtEntry;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.domain.entity.ShortCutEntity;
import com.homepaas.sls.domain.entity.StarLevelInfo;
import com.homepaas.sls.domain.entity.SuperDiscountEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.entity.VerifyTokenBody;
import com.homepaas.sls.domain.entity.WXPaySignEntry;
import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.squareup.okhttp.RequestBody;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PartMap;
import rx.Observable;

/**
 * Created by Administrator on 2015/12/21.
 * 接口函数定义类
 */

public interface RestApi {

    /**
     * 获取服务数据列表
     *
     * @param body
     * @return
     */
    @POST("systemservice/InfoList")
    Call<ResponseWrapper<LifeServiceDataSegment>> getLifeService(@Body RequestBody body);

    /**
     * 获取热门服务
     */
    @POST("systemservice/HotList")
    Call<ResponseWrapper<LifeServiceDataSegment>> getHotService(@Body RequestBody body);

    /**
     * 获取个人信息数据
     *
     * @param body
     * @return
     */
    @POST("clientinfo/Index")
    Call<ResponseWrapper<PersonalInfoDataSegment>> getPersonalInfo(@Body RequestBody body);

    /**
     * 修改头像
     */
    @Multipart
    @POST("clientinfo/SetPhoto")
    Call<ResponseWrapper<ModifyPhotoBody>> modifyPhoto(@PartMap Map<String, RequestBody> params);


    /**
     * 修改昵称
     *
     * @param body
     * @return
     */
    @POST("clientinfo/SetNickName")
    Call<ResponseWrapper<Void>> modifyNickname(@Body RequestBody body);

    /**
     * 验证Token是否有效
     */
    @POST("helper/VerifyToken")
    Call<ResponseWrapper<Void>> checkTokenValid(@Body RequestBody body);

    /**
     * 登录
     */
    @POST("ClientInfo/Login")
    Call<ResponseWrapper<LoginBody>> login(@Body LoginRequest body);

    /**
     * 快捷登录
     */
    @POST("User/QuickLogin")
    Call<ResponseWrapper<LoginBody>> quickLogin(@Body LoginRequest body);

    /**
     * 验证token是否有效
     */
    @POST("Helper/VerifyToken")
    Observable<ResponseWrapper<VerifyTokenBody>> verifyToken(@Body TokenRequest body);

    /**
     * 退出登录
     */
    @POST("clientinfo/LoginOut")
    Call<ResponseWrapper<Void>> logout(@Body RequestBody body);

    /**
     * 用户注册
     */
    @POST("ClientInfo/Register")
    Call<ResponseWrapper<RegisterBody>> register(@Body RequestBody body);

    /**
     * 请求重置密码
     */
    @POST("clientinfo/CheckResetPwd")
    Call<ResponseWrapper<Void>> requestResetPassword(@Body RequestBody body);

    /**
     * 重置密码
     */
    @POST("clientinfo/ResetPwd")
    Call<ResponseWrapper<ResetPasswordBody>> resetPassword(@Body RequestBody body);

    /**
     * 修改密码
     */
    @POST("clientinfo/SetPwd")
    Call<ResponseWrapper<Void>> modifyPassword(@Body RequestBody body);

    /**
     * 发送验证码
     */
    @POST("helper/SendCaptcha")
    Call<ResponseWrapper<CaptchaBody>> sendCaptcha(@Body RequestBody body);

    /**
     * 问题反馈
     */
    @POST("clientfeedback/Index")
    Call<ResponseWrapper<Void>> feedback(@Body RequestBody body);

    /**
     * 获取工人信息
     */
    @POST("worker/Index")
    Call<ResponseWrapper<WorkerInfoBody>> getWorkerInfo(@Body RequestBody body);

    /**
     * 获取商户信息
     */
    @POST("business/Index")
    Call<ResponseWrapper<BusinessInfoBody>> getBusinessInfo(@Body RequestBody body);

    /**
     * 获取工人/商户详情
     */
    @POST("Provider/DetailEx")
    Call<ResponseWrapper<ProviderDetailBody>> getProviderDetailInfo(@Body RequestBody body);


    /**
     * 获取附近服务提供者基础数据列表
     */
    @POST("provider/Index")
    Call<ResponseWrapper<SearchServiceBody>> searchServiceInfo(@Body RequestBody body);

    @POST("Provider/IndexEx2")
    Call<ResponseWrapper<ServiceSearchInfo>> searchService(@Body RequestBody body);

    /**
     * 对工人点赞或取消点赞
     */
    @POST("clientpraise/Worker")
    Call<ResponseWrapper<Void>> likeWorker(@Body RequestBody body);

    /**
     * 收藏工人或取消收藏
     */
    @POST("clientfavorites/Worker")
    Call<ResponseWrapper<Void>> collectWorker(@Body RequestBody body);

    /**
     * 对商户点赞或取消点赞
     */
    @POST("clientpraise/Business")
    Call<ResponseWrapper<Void>> likeBusiness(@Body RequestBody body);

    /**
     * 收藏商户或取消收藏
     */
    @POST("clientfavorites/Business")
    Call<ResponseWrapper<Void>> collectBusiness(@Body RequestBody body);

    /**
     * 获取收藏工人列表
     */
    @POST("clientfavorites/WorkerList")
    Call<ResponseWrapper<CollectedWorkerBody>> getCollectedWorkers(@Body RequestBody body);

    /**
     * 获取收藏商户列表
     */
    @POST("clientfavorites/BusinessList")
    Call<ResponseWrapper<CollectedBusinessBody>> getCollectedBusinesses(@Body RequestBody body);

    /**
     * 是否可以拨打电话
     */
    @POST("callrecord/CheckCallRecord")
    Call<ResponseWrapper<Void>> checkCallable(@Body RequestBody body);

    /**
     * 获取消息列表
     */
    @POST("clientmessage/Count")
    Call<ResponseWrapper<MessageBody>> getMessageList(@Body RequestBody body);

    /**
     * 获取未读消息数量
     */
    @POST("clientmessage/UnreadCount")
    Call<ResponseWrapper<UnreadMessageBody>> getUnreadMessageCount(@Body RequestBody body);

    /**
     * 删除消息
     */
    @POST("clientmessage/Del")
    Call<ResponseWrapper<Void>> deleteMessages(@Body RequestBody body);

    /**
     * 设置已读
     */
    @POST("clientmessage/SetState")
    Call<ResponseWrapper<Void>> readMessages(@Body RequestBody body);

    /**
     * 发送通话记录
     */
    @POST("callrecord/SetCallRecord")
    Call<ResponseWrapper<Void>> sendCallLog(@Body RequestBody body);

    /**
     * 上传个推Id
     */
    @POST("clientinfo/UpdatePushDeviceID")
    Call<ResponseWrapper<Void>> uploadPushDeviceId(@Body RequestBody body);

    /**
     * 清空个推Id
     */
    @POST("clientinfo/ClearPushDeviceID")
    Call<ResponseWrapper<Void>> clearPushDeviceId(@Body RequestBody body);

    /**
     * 请求更新信息
     */
    @POST("helper/AndroidUpdate")
    Call<ResponseWrapper<CheckUpdateBody>> checkUpdate(@Body RequestBody body);


    /**
     * 获取工人服务类别列表
     *
     * @return
     */
    @POST("clientinfo/GetWorkerServiceListEx")
    Call<ResponseWrapper<WorkerServiceTypeInfo>> getWorkerServiceTypeList(@Body RequestBody body);


    /**
     * 下单
     */
    @Multipart
    @POST("OrderInfo/CreateOrderEx")
    Call<ResponseWrapper<CreatedOrderBody>> createOrder(@PartMap Map<String, RequestBody> multipartParams);

    /**
     * 获取订单
     *
     * @param body
     * @return
     */
    @POST("OrderInfo/GetOrderListEx")
    Call<ResponseWrapper<AllOrderBody>> getAllOrder(@Body RequestBody body);

    /**
     * 获取我的红包
     */
    @POST("clientinfo/GetRedEnvelopeList")
    Call<ResponseWrapper<MyRedPacketBody>> getMyRedPacket(@Body RequestBody body);

    /**
     * 获取工人评价
     */
    @POST("ClientEvaluation/GetWorkerEvaluationList")
    Call<ResponseWrapper<EvaluationBody>> getWorkerEvaluations(@Body RequestBody body);

    /**
     * 获取商户评价
     */
    @POST("ClientEvaluation/GetMerchantEvaluationList")
    Call<ResponseWrapper<EvaluationBody>> getBusinessEvaluations(@Body RequestBody body);

    /**
     * 获取商户服务内容
     */
    @POST("ClientInfo/GetMerchantServiceListEx")
    Call<ResponseWrapper<ServiceContentBody>> getBusinessServiceContents(@Body RequestBody body);

    /**
     * 工人号码纠错
     */
    @POST("HelperInfo/ReportWorker")
    Call<ResponseWrapper<Void>> reportWorker(@Body RequestBody body);

    /**
     * 商户号码纠错
     */
    @POST("HelperInfo/ReportMerchant")
    Call<ResponseWrapper<Void>> reportBusiness(@Body RequestBody body);

    /**
     * 获取优惠卷
     */
//    @POST("Coupon/CouponList")
    @POST("User/GetCoupons")
    Call<ResponseWrapper<CouponContentsBody>> getCouponList(@Body RequestBody body);


    /**
     * 我的评价列表
     */
    @POST("ClientEvaluation/GetEvaluationList")
    Call<ResponseWrapper<EvaluationBody>> getMyEvaluations(@Body RequestBody body);

    /**
     * 账户信息
     */
    @POST("Account/GetAccountInfo")
    Call<ResponseWrapper<AccountInfoBody>> getAccountInfo(@Body RequestBody body);

    /**
     * 账户明细
     */
    @POST("Account/GetAccountDetails")
    Call<ResponseWrapper<AccountDetailBody>> getAccountDetails(@Body RequestBody body);

    /**
     * 支付明细
     */
    @POST("Account/GetPaymentDetails")
    Call<ResponseWrapper<PayDetailBody>> getPayDetails(@Body RequestBody body);

    /**
     * 获取充值列表
     */
    @POST("Recharge/GetRechargeListEx")
    Call<ResponseWrapper<RechargeListBody>> getRecharges(@Body RequestBody body);

    /**
     * 获取支付宝支付签名
     */
    @POST("Payment/GetAlipaySign")
    Call<ResponseWrapper<AlipaySignBody>> getAlipaySign(@Body RequestBody body);

    /**
     * 获取微信支付签名
     */
    @POST("Payment/GetWxpaySign")
    Call<ResponseWrapper<WeChatPayBody>> getWeChatSign(@Body RequestBody body);

    /**
     * 评价商户/工人
     */
    @Multipart
    @POST("ClientEvaluation/AddEvaluation")
    Call<ResponseWrapper<Void>> evaluate(@PartMap Map<String, RequestBody> multipartParams);

    /**
     * 评价订单
     */
    @Multipart
    @POST("EvaluationInfo/EvaluateOrder")
    Call<ResponseWrapper<Void>> evaluateOrder(@PartMap Map<String, RequestBody> multipartParams);

    /**
     * 获取订单详情
     */
    @POST("OrderInfo/GetOrderInfoEx")
    Call<ResponseWrapper<OrderDetailBody>> getOrderDetail(@Body RequestBody body);

    /**
     * 获取订单支付支付签名
     */
    @POST("orderinfo/GetAlipaySign")
    Call<ResponseWrapper<OrderPayAliSignBody>> GetOrderPayAliSign(@Body RequestBody requestBody);


    /**
     * 获取分享内容
     */
    @POST("ClientShare/ShareContent")
    Call<ResponseWrapper<ShareInfoBody>> getShareInfo(@Body RequestBody body);

    /**
     * 获取订单微信支付签名
     */
    @POST("orderInfo/GetWxpaySign")
    Call<ResponseWrapper<OrderPayWXSignBody>> GetOrderPayWXSign(@Body RequestBody requestBody);

    @POST("orderInfo/OrderCancel")
    Call<ResponseWrapper<Void>> cancelOrder(@Body RequestBody requestBody);

    @POST("orderInfo/OrderDelete")
    Call<ResponseWrapper<Void>> deleteOrder(@Body RequestBody requestBody);

    //FIXME!!!改成新的接口,老的接口不变
//    @POST("orderInfo/OrderConfirm")
    @POST("orderInfo/CompleteOrderEx")
    Call<ResponseWrapper<Void>> confirmOrder(@Body RequestBody requestBody);

    @POST("orderinfo/BalancePay")
    Call<ResponseWrapper<Void>> payOrderByBalance(@Body RequestBody requestBody);

    @POST("OrderInfo/CreateDirectPay")
    Call<ResponseWrapper<DirectPayInfo>> getDirectPayId(@Body RequestBody requestBody);


    //直接支付支付宝签名
    @POST("OrderInfo/GetAlipaySignForDirectPay")
    Call<ResponseWrapper<AlipaySignBody>> getDirectPayAliSign(@Body RequestBody requestBody);

    //直接支付微信签名
    @POST("OrderInfo/GetWxpaySignForDirectPay")
    Call<ResponseWrapper<OrderPayWXSignBody>> getDirectPayWXSign(@Body RequestBody requestBody);

    @POST("OrderInfo/BalanceDirectPay")
    Call<ResponseWrapper<Void>> directBalancePay(@Body RequestBody requestBody);

    /**
     * 上传二维码扫描内容
     */
    @POST("ClientInfo/UploadQRCode")
    Call<ResponseWrapper<PushInfo>> uploadUrl(@Body RequestBody body);

    /**
     * 增加分享内容
     */
    @POST("ClientShare/AddShareRecord")
    Call<ResponseWrapper<Void>> addShareRecord(@Body RequestBody requestBody);


    /*第一次打开app时请求此接口*/
    @POST("ClientInfo/FirstOpenApp")
    Call<ResponseWrapper<FirstOpenAppInfo>> getFirstCoupon(@Body RequestBody body);

    @POST("HelperInfo/GetAllDescription")
    Observable<ResponseWrapper<DescriptionInfo>> getDescription(@Body GetDescriptionRequest requestBody);

    /**
     * 获取工人标签
     */
    @POST("worker/GetWorkerTags")
    Call<ResponseWrapper<WorkerTagsBody>> getWorkerTags(@Body RequestBody requestBody);

    /**
     * 获取商户标签
     */
    @POST("business/GetMerchantTags")
    Call<ResponseWrapper<BusinessTagsBody>> getBusinessTags(@Body RequestBody requestBody);

    /**
     * 获取工人/商户头像列表
     *
     * @param requestBody
     * @return
     */
    @POST("Provider/Avatar")
    Call<ResponseWrapper<AvatarsEntity>> getAvatars(@Body RequestBody requestBody);

    /**
     * Provider/GetAds:获取活动信息
     */
    @POST("Provider/GetAds")
    Call<ResponseWrapper<AdsInfo>> getAdsInfo(@Body RequestBody requestBody);

    /**
     * Provider/GetAds:获取活动信息
     */
    @POST("Provider/GetAds")
    Observable<ResponseWrapper<AdsInfo>> getBannerInfo(@Body RequestBody requestBody);

    /**
     * 获取弹窗的值
     */
    @POST("ClientInfo/GetPopupVer")
    Call<ResponseWrapper<PopupVer>> getPopuVer(@Body RequestBody requestBody);

    /**
     * Banner
     */
    @POST("SystemService/GetBanners")
    Call<ResponseWrapper<List<BannerInfo>>> getBannersInfo(@Body RequestBody requestBody);

    /**
     * 获取服务类型列表数据
     */
    @POST("SystemService/InfoListEx")
    Observable<ResponseWrapper<List<ServiceTypeEx>>> getInfoListEx();

    @POST("ClientInfo/GetAddress")
    Call<ResponseWrapper<List<AddressEntity>>> getServiceAddressList(@Body TokenRequest tokenRequest);

    /**
     * 删除我的服务地址
     *
     * @param requestBody
     * @return
     */
    @POST("ClientInfo/DeleteAddress")
    Call<ResponseWrapper<Void>> deleteServiceAddress(@Body RequestBody requestBody);

    @POST("ClientInfo/AddAddress")
    Call<ResponseWrapper<AddressIdEntity>> addServiceAddress(@Body RequestBody requestBody);

    @POST("ClientInfo/EditAddress")
    Call<ResponseWrapper<Void>> updateServiceAddress(@Body RequestBody requestBody);

    @POST("SystemService/ServiceTimeStartAt")
    Call<ResponseWrapper<List<ServiceScheduleEntity>>> getServiceDSchedule(@Body RequestBody requestBody);

    @POST("ClientInfo/GetServicePriceEx")
    Call<ResponseWrapper<PriceEntity>> getServicePrice(@Body RequestBody requestBody);

    @POST("SystemService/GetActivityEx")
    Call<ResponseWrapper<ActivityNgInfoNew>> getAvailableActivity(@Body RequestBody requestBody);

    @POST("OrderInfo/CreateOrderOneKey")
    @Multipart
    Call<ResponseWrapper<CreatedOrderBody>> placeDirectOrder(@PartMap Map<String, RequestBody> requestBodyMap);

    @POST("SystemService/GetConfig")
    Call<ResponseWrapper<SystemConfigEntity>> getSystemConfig(@Body RequestBody requestBody);

    @POST("SystemService/GetServiceQty")
    Observable<ResponseWrapper<List<String>>> getQty(@Body ServiceIdRequest requestBody);

    @POST("ClientInfo/AddRecommendInfo")
    Call<ResponseWrapper<Void>> addRecommendInfo(@Body RequestBody requestBody);

    //账单列表
    @POST("ClientInfo/MySettlementList")
    Observable<ResponseWrapper<AccountListEntity>> getSettlementList(@Body SettlementListRequest requestBody);

    //账户余额
    @POST("ClientInfo/MySettlement")
    Observable<ResponseWrapper<NewAccountInfo>> getAccountInfo(@Body TokenRequest request);

    //账单详情
    @POST("ClientInfo/MySettlementDetail")
    Observable<ResponseWrapper<AccountDetailItemEntry>> getAccountDetails(@Body AccountDetailsRequest request);

    //支付宝充值
    @POST("Recharge/GetAlipaySignEx")
    Observable<ResponseWrapper<AliPaySignEntry>> getAlipaySign(@Body AliPaySignRequest request);

    //微信充值
    @POST("Recharge/GetWxpaySignEx")
    Observable<ResponseWrapper<WXPaySignEntry>> getWXPaySign(@Body WXPaySignRequest request);

    //充值列表
    @POST("Recharge/GetRechargeListEx")
    Observable<ResponseWrapper<RechargeListExEntity>> getRechargeListEx();

    //充值成功后充值属性
    @POST("Recharge/GetRechargeInfoByRechargeCode")
    Observable<ResponseWrapper<RechargeInfoResponse>> getRechargeInfo(@Body RechargeInfoRequest request);

    //服务查询
    @POST("Provider/IndexEx2")
    Observable<ResponseWrapper<ServiceSearchInfo>> getSearchService(@Body SearchServiceRequest request);

    //热门服务
    @POST("Search/GetHotServices")
    Observable<ResponseWrapper<List<HotServiceInfo>>> getHotServiceInfo(@Body SearchHotServiceRequest request);

    //获取商户服务价格列表
    @POST("ClientInfo/GetMerchantServicePriceList")
    Observable<ResponseWrapper<BusinessServicePricesEntity>> getBusinessServicePrice(@Body BusinessSercicePriceRequest request);

    //获取商户服务列表
    @POST("ClientInfo/GetMerchantServiceListEx")
    Observable<ResponseWrapper<BusinessServiceTypeInfo>> getBusinessServiceList(@Body BusinessServiceListRequest request);

    //获取商户服务列表
    @POST("ClientInfo/GetMerchantServicePriceListEx")
    Observable<ResponseWrapper<MerchantServicePriceListEntity>> getMerchantServicePriceList(@Body MerchantServicePriceListRequest request);

    //获取工人服务列表
    @POST("ClientInfo/GetWorkerServicePriceListEx")
    Observable<ResponseWrapper<WorkerServicePriceListEntity>> getWorkerServicePriceList(@Body WorkerServicePriceListRequest request);


    @POST("OrderInfo/CheckFirstOrder")
    Call<ResponseWrapper<IsFirstOrderInfo>> getIsFirstOrderInfo(@Body TokenRequest tokenRequest);

    //到店支付创建订单
    @POST("OrderInfo/CreateDirectPayEx")
    Observable<ResponseWrapper<CreateDirectPayExEntity>> getCreateDirectPayEx(@Body CreateDirectPayExRequest request);

    //到店支付的活动
    @POST("SystemService/GetAtStoreActivityEx")
    Observable<ResponseWrapper<GetAtStoreActivityEntity>> getGetAtStoreActivity(@Body GetAtStoreActivityRequest request);

    //检查是否已经领取了新人红包
    @POST("ClientInfo/CheckIsReceivedRedCoups")
    Observable<ResponseWrapper<CheckIsReceivedRedCoupsEntry>> getCheckIsReceivedRedCoups(@Body TokenRequest tokenRequest);

    /**
     * 首页改变3.3.0
     */
    //分类接口
    @POST("Front/Shortcut")
    Observable<ResponseWrapper<ShortCutEntity>> getShortCut(@Body ShortCutRequest request);

    //查看全部分类
    @POST("Front/AllService")
    Observable<ResponseWrapper<ServiceItemListEntity>> getAllService(@Body ShortCutRequest request);

    //推荐类目
    @POST("Front/Recommend")
    Observable<ResponseWrapper<RecommendServiceEntity>> getRecommendService(@Body ShortCutRequest request);

    //查看更多推荐类目
    @POST("Front/QueryService")
    Observable<ResponseWrapper<ServiceItemListEntity>> getQueryService(@Body QueryServiceRequest request);

    //热门搜索
    @POST("Search/HotService")
    Observable<ResponseWrapper<ServiceItemListEntity>> hotSearchService(@Body ShortCutRequest request);

    //搜索
    @POST("Search/Topic")
    Observable<ResponseWrapper<ServiceItemListEntity>> getSearchTopic(@Body TopicRequest request);

    //3.3.0下单获取服务价格
    @POST("Front/QueryServicePrice")
    Observable<ResponseWrapper<QueryServicePriceEntry>> getQueryServicePrice(@Body QueryServicePriceRequest request);

    //3.3.0下单获取服务时间
    @POST("Front/ServiceTimeStartAt")
    Observable<ResponseWrapper<ServiceTimeStartAtEntry>> getServiceTimeStartAt(@Body QueryServicePriceRequest requestBody);

    //3.3.0提交订单
    @POST("Front/SubmitOrder")
    Observable<ResponseWrapper<PlaceOrderEntry>> getPlaceOrder(@Body PlaceOrderRequest request);

    //3.3.0下单活动
    @POST("Front/GetActivityEx")
    Observable<ResponseWrapper<ActivityNgInfoNew>> getActivityEx(@Body GetActivityExRequest request);

    //红包
    @POST("User/GetCoupons")
    Observable<ResponseWrapper<CommonCouponInfo>> getCommonCouponInfo(@Body GetCouponContentsRequest request);

    //取消订单原因列表
    @POST("Order/GetOrderCancelReasonList")
    Observable<ResponseWrapper<OrderCancelReasonEntity>> getOrderCancelReasonList(@Body TokenRequest request);

    //获取订单投诉原因的列表
    @POST("Order/GetOrderComplaintsReasonList")
    Observable<ResponseWrapper<ComplaintListEntity>> getOrderComplaintsReasonList(@Body TokenRequest request);

    //提交投诉原因信息
    @POST("Order/SubmitApplyComplaints")
    Observable<ResponseWrapper<String>> submitApplyComplaints(@Body ComplaintRequest request);

    //查询（如：超级星期五，秒杀等）这样的活动的价格配置
    @POST("Front/QueryActivityCommonServicePrice")
    Observable<ResponseWrapper<QueryServicePriceEntry>> getQueryActivityServicePrice(@Body QueryActivityServicePriceRequest request);

    //物流信息
    @POST("Front/GetKdTrackInfo")
    Observable<ResponseWrapper<KdTrackInfoResponse>> getKdTrackInfo(@Body GetKdTrackInfoRequest request);

    //跑腿的详情
    @POST("Express/GetExpressDetail")
    Observable<ResponseWrapper<ExpressDetailOutputEntity>> getErrandServiceDetail(@Body ExpressDetailRequest request);

    //申请赔付
    @POST("Order/SubmitApplyClaims")
    Observable<ResponseWrapper<String>> submitApplyClaims(@Body SubmitApplyClaimsRequest request);

    //订单追踪
    @POST("Order/TrackOrderStatus")
    Observable<ResponseWrapper<TrackOrderInfo>> getTrackOrderStatus(@Body TrackOrderStatusRequest request);

    //获取订单列表
    @POST("OrderInfo/GetOrderListEx")
    Observable<ResponseWrapper<OrderInfo>> getOrderList(@Body GetOrderRequest request);

    //获取评价信息
    @POST("Order/GetStarLevel")
    Observable<ResponseWrapper<StarLevelInfo>> getStarLevel(@Body GetStarLevelRequest request);

    @POST("Front/GetSuperDiscount")
    Observable<ResponseWrapper<SuperDiscountEntity>> getSuperDiscount(@Body ShortCutRequest request);

    @POST("Order/GetCityInfo")
    Observable<ResponseWrapper<CityListEntity>> getCityList();

    @POST("Order/SubmitEvaluation")
    Observable<ResponseWrapper<String>> submitEvaluation(@Body SubmitEvaluationRequest request);

    //首页弹框
    @POST("ClientInfo/FirstOpenAppInfo")
    Observable<ResponseWrapper<FirstOpenAppInfoEx>> getFirstOpenAppInfo(@Body TokenRequest request);

    //获取首页浮层弹框信息
    @POST("Order/GetOrderGlobalStatus")
    Observable<ResponseWrapper<HomeOrderStatusEntity>> GetOrderGlobalStatus(@Body TokenRequest request);

    //获取订单列表弹框的类型
    @POST("Order/GetOrderListPop")
    Observable<ResponseWrapper<OrderListPopEntity>> GetOrderListPop(@Body TokenRequest request);

    /**
     * 增加服务相关接口
     */
    //  增项-使用余额支付
    @POST("Order/IodOfBalancePay")
    Observable<ResponseWrapper<String>> iodOfBalancePay(@Body IodOfBalancePayRequest request);

    //    增项-使用支付宝支付（支持混合支付）
    @POST("Order/IodOfAlipay")
    Observable<ResponseWrapper<AddServiceAlipayEntity>> IodOfAlipay(@Body IodOfAlipayRequest request);

    //  增项-使用微信支付（支持混合支付）
    @POST("Order/IodOfWxpay")
    Observable<ResponseWrapper<AddServiceWxpayEntity>> IodOfWxpay(@Body IodOfWxpayRequest request);

    // 下单页--选择工人或者商户
    @POST("Front/GetChooseWorkerOrMerchantList")
    Observable<ResponseWrapper<SelectServiceOrWorkerEntity>> getChooseWorkerOrMerchantList(@Body SelectServiceOrWorkerRequest request);

    /**
     * 获取订单详情
     */
    @POST("OrderInfo/GetOrderInfoEx")
    Observable<ResponseWrapper<OrderDetailBody>> getNewOrderDetail(@Body GetOrderDetailRequest getOrderDetailRequest);


    /**
     * 获取消息列表
     */
    @POST("clientmessage/Count")
    Observable<ResponseWrapper<MessageBody>> getNewsMessageList(@Body MessageListRequest body);

    /**
     * 删除所有消息
     */
    @POST("ClientMessage/Clear")
    Observable<ResponseWrapper<String>> deleteMessageAll(@Body TokenRequest tokenRequest);

    /**
     * 电话未接通
     */
    @POST("callrecord/SetCallRecord")
    Observable<ResponseWrapper<String>> callPhoneBuly(@Body CallLogRequest body);


    /**
     * 获取收藏工人列表
     */
    @POST("clientfavorites/WorkerList")
    Observable<ResponseWrapper<CollectedWorkerBody>> mewGetCollectedWorkers(@Body TokenRequest body);

    /**
     * 获取收藏商户列表
     */
    @POST("clientfavorites/BusinessList")
    Observable<ResponseWrapper<CollectedBusinessBody>> mewGCollectedBusinesses(@Body TokenRequest body);

    /**
     * 收藏工人或取消收藏
     */
    @POST("clientfavorites/Worker")
    Observable<ResponseWrapper<String>> newCollectWorker(@Body CollectWorkerRequest body);

    /**
     * 收藏商户或取消收藏
     */
    @POST("clientfavorites/Business")
    Observable<ResponseWrapper<String>> newCollectBusiness(@Body CollectBusinessRequest body);

    /**
     * 弹框接口
     */
    @POST("Order/FirstOpenAppInfo")
    Observable<ResponseWrapper<NewFirstOpenAppInfo>> firstOpenAppInfo(@Body TokenRequest body);
    /**
     * 非标订单商户相关信息
     */
     //获取商家或者普通工人的信息
    @POST("BusinessEx/GetBusinessInfo")
    Observable<ResponseWrapper<BusinessExInfoOutput>> getBusinessInfo(@Body GetBusinessInfoRequest request);
     //获取二级服务分类
    @POST("BusinessEx/GetBusinessSecService")
    Observable<ResponseWrapper<BusinessSecServiceEntity>> getBusinessSecService(@Body GetBusinessSecServiceRequest request);
    //获取服务列表
    @POST("BusinessEx/GetBusinessServiceList")
    Observable<ResponseWrapper<BusinessServiceListEntity>> getBusinessServiceList(@Body GetBusinessServiceListRequest request);
    //获取商户或者普通工人的评论列表
    @POST("BusinessEx/GetBusinessCommentList")
    Observable<ResponseWrapper<BusinessCommentListOutput>> getBusinessCommentList(@Body GetBusinessCommentRequest
                                                                                          request);
    //通过二级服务id，获取商家下单的服务列表
    @POST("BusinessEx/GetBusinessOrderServiceList")
    Observable<ResponseWrapper<BusinessOrderServiceListEntity>> getBusinessOrderServiceList(@Body BusinessOrderServiceListRequest request);
}

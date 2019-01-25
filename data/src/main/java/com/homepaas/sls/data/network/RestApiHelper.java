package com.homepaas.sls.data.network;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
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
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ModifyPhotoBody;
import com.homepaas.sls.data.network.dataformat.MyRedPacketBody;
import com.homepaas.sls.data.network.dataformat.OrderDetailBody;
import com.homepaas.sls.data.network.dataformat.OrderPayAliSignBody;
import com.homepaas.sls.data.network.dataformat.OrderPayWXSignBody;
import com.homepaas.sls.data.network.dataformat.PayDetailBody;
import com.homepaas.sls.data.network.dataformat.PersonalInfoDataSegment;
import com.homepaas.sls.data.network.dataformat.ProviderDetailBody;
import com.homepaas.sls.data.network.dataformat.RechargeListBody;
import com.homepaas.sls.data.network.dataformat.ResetPasswordBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.SearchServiceBody;
import com.homepaas.sls.data.network.dataformat.ServiceContentBody;
import com.homepaas.sls.data.network.dataformat.ShareInfoBody;
import com.homepaas.sls.data.network.dataformat.UnreadMessageBody;
import com.homepaas.sls.data.network.dataformat.WeChatPayBody;
import com.homepaas.sls.data.network.dataformat.WorkerInfoBody;
import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.data.network.dataformat.request.AddEvaluationRequest;
import com.homepaas.sls.data.network.dataformat.request.AddRecommendInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.AddServiceAddressRequest;
import com.homepaas.sls.data.network.dataformat.request.AddShareRecord;
import com.homepaas.sls.data.network.dataformat.request.AvailableActivityRequest;
import com.homepaas.sls.data.network.dataformat.request.AvatarRequest;
import com.homepaas.sls.data.network.dataformat.request.BalancePayRequest;
import com.homepaas.sls.data.network.dataformat.request.BusinessInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.BusinessServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.CallLogRequest;
import com.homepaas.sls.data.network.dataformat.request.CancelOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.CaptchaRequest;
import com.homepaas.sls.data.network.dataformat.request.CheckCallableRequest;
import com.homepaas.sls.data.network.dataformat.request.CheckTokenRequest;
import com.homepaas.sls.data.network.dataformat.request.CheckUpdateBody;
import com.homepaas.sls.data.network.dataformat.request.CollectBusinessRequest;
import com.homepaas.sls.data.network.dataformat.request.CollectWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.ConfirmOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.CreateOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.DeleteMessageRequest;
import com.homepaas.sls.data.network.dataformat.request.DeleteOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.DeleteServiceAddressRequest;
import com.homepaas.sls.data.network.dataformat.request.DirectBalancePayRequest;
import com.homepaas.sls.data.network.dataformat.request.EvaluateOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.EvaluationListRequest;
import com.homepaas.sls.data.network.dataformat.request.FeedbackRequest;
import com.homepaas.sls.data.network.dataformat.request.GetAllOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessTagsRequest;
import com.homepaas.sls.data.network.dataformat.request.GetCouponContentsRequest;
import com.homepaas.sls.data.network.dataformat.request.GetDirectPayAliSignRequest;
import com.homepaas.sls.data.network.dataformat.request.GetDirectPayRequest;
import com.homepaas.sls.data.network.dataformat.request.GetDirectPayWXSignRequest;
import com.homepaas.sls.data.network.dataformat.request.GetMyEvaluationRequest;
import com.homepaas.sls.data.network.dataformat.request.GetMyRedPacketrRequest;
import com.homepaas.sls.data.network.dataformat.request.GetOrderDetailRequest;
import com.homepaas.sls.data.network.dataformat.request.GetOrderPayAliSignRequest;
import com.homepaas.sls.data.network.dataformat.request.GetOrderPayWxSignRequest;
import com.homepaas.sls.data.network.dataformat.request.GetOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.GetPaySignRequest;
import com.homepaas.sls.data.network.dataformat.request.GetShareInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.GetWorkerTagsRequest;
import com.homepaas.sls.data.network.dataformat.request.LikeBusinessRequest;
import com.homepaas.sls.data.network.dataformat.request.LikeWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.LoginRequest;
import com.homepaas.sls.data.network.dataformat.request.ModifyNicknameRequest;
import com.homepaas.sls.data.network.dataformat.request.ModifyPasswordRequest;
import com.homepaas.sls.data.network.dataformat.request.ProviderDetailRequest;
import com.homepaas.sls.data.network.dataformat.request.PushServiceIdRequest;
import com.homepaas.sls.data.network.dataformat.request.ReadMessageRequest;
import com.homepaas.sls.data.network.dataformat.request.RegisterRequest;
import com.homepaas.sls.data.network.dataformat.request.ReportBusinessRequest;
import com.homepaas.sls.data.network.dataformat.request.ReportWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.ResetPassword1Request;
import com.homepaas.sls.data.network.dataformat.request.ResetPassword2Request;
import com.homepaas.sls.data.network.dataformat.request.ServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.ServiceTypeIdRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.network.dataformat.request.UploadQrCodeRequest;
import com.homepaas.sls.data.network.dataformat.request.WorkerInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.WorkerServiceTypeRequest;
import com.homepaas.sls.data.repository.datasource.DeviceInfoPDataSource;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.AddressIdEntity;
import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.DescriptionInfo;
import com.homepaas.sls.domain.entity.DirectPayInfo;
import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.PopupVer;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.DirectOrderParam;
import com.homepaas.sls.domain.param.GetOrderPayAliSignParams;
import com.homepaas.sls.domain.param.GetOrderpayWxSignParams;
import com.homepaas.sls.domain.param.PriceParam;
import com.homepaas.sls.domain.param.SearchParameter;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2015/12/21.
 * Rest API 帮助类
 */

public class RestApiHelper {

    public static final String TAG = "RestApiHelper";

    public static final String JSON_CONTENT_TYPE = "application/json";

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse(JSON_CONTENT_TYPE);

    public static final String EMPTY_JSON = "{}";


    /**
     * retrofit 创建的 {@link RestApi} 对象,本身也是动态代理实现
     */
    private RestApi restApi;
    /**
     * {@link RestApi} 的动态代理对象,用来做AOP
     */
    private RestApi proxy;

    private Gson gson;

    private String headerChannel;

    private DeviceInfoPDataSource deviceInfo;

    //    private String API_BASE_URL = Url.API_BASE_URL_DEFAULT;
    private OkHttpClient okHttpClient;

    @Inject
    public RestApiHelper(String headerChannel, DeviceInfoPDataSource deviceInfo) {
        this.headerChannel = headerChannel;
        this.deviceInfo = deviceInfo;
        gson = new Gson();
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(BuildConfig.DEFAULT_CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(BuildConfig.DEFAULT_READ_TIMEOUT_SEC, TimeUnit.SECONDS);
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                String deviceId = "";
                try {
                    deviceId = RestApiHelper.this.deviceInfo.getDeviceId();
                    Log.i(TAG, "intercept: "+deviceId);
                } catch (GetPersistenceDataException e) {
                    Log.e(TAG, "Can't get device id", e);
                }
                Request request = chain.request().newBuilder().addHeader("AppSources", RestApiHelper.this.headerChannel)
                        .addHeader("DeviceId", deviceId)
                        .addHeader("zjsh_version",BuildConfig.VERSION_NAME)
                        .build();
                return chain.proceed(request);
            }
        });
        okHttpClient.interceptors().add(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.API_BASE_URL_DEFAULT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
//        proxy = create(RestApi.class, restApi);
    }

    /**
     * 返回创建的动态代理对象
     * @return 返回API对象
     */
    public RestApi restApi() {
        return restApi;
    }

    /**
     * 解析响应数据
     */
    public static class ResponseParseFunc<T> implements Func1<ResponseWrapper<T>, Observable<T>> {

        @Override
        public Observable<T> call(final ResponseWrapper<T> tResponseWrapper) {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(Subscriber<? super T> subscriber) {
                    Meta meta = tResponseWrapper.meta;
                    if (meta.isSuccess()) {
                        subscriber.onNext(tResponseWrapper.data);
                        subscriber.onCompleted();
                    } else if (meta.isAuthFailed()) {
                        try {
                            subscriber.onError(new AuthException(meta.getErrorMsg()));
                        } catch (Exception e) {
                            System.out.print(e.toString());
                        }
                    } else {
                        subscriber.onError(new GetDataException(meta.getErrorMsg(),meta.getErrorCode()));
                    }
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> tClass, Object target) {
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[] {tClass}, new RestApiProxy(target));
    }

    private class RestApiProxy implements InvocationHandler {

        private Object target;

        private RestApiProxy(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }
            return method.invoke(target, args);
        }
    }

    /////////////////////////////

    public void changeRelease() {
        Url.setRelease();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.API_BASE_URL_DEFAULT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public void changeDebug() {
        Url.setDebug();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.API_BASE_URL_DEFAULT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public void changeDeveloper() {
        Url.setDeveloper();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.API_BASE_URL_DEFAULT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
    }


    public Response<ResponseWrapper<LifeServiceDataSegment>> getLifeServiceList() throws IOException {
        final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, EMPTY_JSON);
        Call<ResponseWrapper<LifeServiceDataSegment>> call = restApi.getLifeService(requestBody);
        return call.execute();
    }

    /**
     * 获取热门服务
     */
    public Response<ResponseWrapper<LifeServiceDataSegment>> getHotServiceList() throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, EMPTY_JSON);
        Call<ResponseWrapper<LifeServiceDataSegment>> call = restApi.getHotService(requestBody);
        return call.execute();
    }


    /**
     * 获取个人信息
     *
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<PersonalInfoDataSegment>> getPersonalInfo(String token) throws IOException {
        Call<ResponseWrapper<PersonalInfoDataSegment>> call = restApi.getPersonalInfo(jsonTokenRequest(token));
        return call.execute();
    }

    /**
     * 校验token是否有效
     */
    @Deprecated
    public Response<ResponseWrapper<Void>> checkToken(CheckTokenRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.checkTokenValid(requestBody);
        return call.execute();
    }

    /**
     * 注册
     */
    public Response<ResponseWrapper<RegisterBody>> register(RegisterRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<RegisterBody>> call = restApi.register(requestBody);
        return call.execute();
    }

    /**
     * 登录
     *
     * @param request 请求参数
     * @return 响应结果
     * @throws IOException
     */
    public Response<ResponseWrapper<LoginBody>> login(LoginRequest request) throws IOException {
        Call<ResponseWrapper<LoginBody>> call = restApi.login(request);
        return call.execute();
    }


    /**
     *快捷登录
     */
    public Response<ResponseWrapper<LoginBody>> quickLogin(LoginRequest request) throws IOException {
        Call<ResponseWrapper<LoginBody>> call = restApi.quickLogin(request);
        return call.execute();
    }

    /**
     * 发送验证码
     */
    public Response<ResponseWrapper<CaptchaBody>> sendCaptcha(CaptchaRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<CaptchaBody>> call = restApi.sendCaptcha(requestBody);
        return call.execute();
    }

    /**
     * 请求重置密码
     */
    public Response<ResponseWrapper<Void>> requestResetPassword(ResetPassword1Request request) throws IOException {

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.requestResetPassword(requestBody);
        return call.execute();
    }

    /**
     * 重置密码
     */
    public Response<ResponseWrapper<ResetPasswordBody>> resetPassword(ResetPassword2Request request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<ResetPasswordBody>> call = restApi.resetPassword(requestBody);
        return call.execute();
    }

    /**
     * 修改密码
     */
    public Response<ResponseWrapper<Void>> modifyPassword(ModifyPasswordRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.modifyPassword(requestBody);
        return call.execute();
    }

    /**
     * 修改昵称
     */
    public Response<ResponseWrapper<Void>> modifyNickname(ModifyNicknameRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.modifyNickname(requestBody);
        return call.execute();
    }


    /**
     * 修改头像
     */
    public Response<ResponseWrapper<ModifyPhotoBody>> modifyPhoto(TokenRequest request, String filePath) throws IOException {
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>(2);
        File file = new File(filePath);
        String fileName = file.getName();
        RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        requestBodyMap.put("file\"; filename=\"" + fileName, photo);
        RequestBody json = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        requestBodyMap.put("json_data", json);
        Call<ResponseWrapper<ModifyPhotoBody>> call = restApi.modifyPhoto(requestBodyMap);
        return call.execute();
    }


    /**
     * 登出
     */
    public Response<ResponseWrapper<Void>> logout(TokenRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.logout(requestBody);
        return call.execute();
    }


    /**
     * 意见反馈
     */
    public Response<ResponseWrapper<Void>> feedback(FeedbackRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.feedback(requestBody);
        return call.execute();
    }


    /**
     * 获取工人信息
     *
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<WorkerInfoBody>> getWorkerInfo(WorkerInfoRequest workerInfoRequest) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse(JSON_CONTENT_TYPE), gson.toJson(workerInfoRequest));
        Call<ResponseWrapper<WorkerInfoBody>> call = restApi.getWorkerInfo(requestBody);
        return call.execute();
    }

    /**
     * 获取商户信息
     *
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<BusinessInfoBody>> getBusinessInfo(BusinessInfoRequest businessInfoRequest) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse(JSON_CONTENT_TYPE), gson.toJson(businessInfoRequest));
        Call<ResponseWrapper<BusinessInfoBody>> call = restApi.getBusinessInfo(requestBody);
        return call.execute();
    }

    /**
     * 获取工人/商户详情
     * @param providerDetailRequest
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<ProviderDetailBody>> getProviderDetailInfo(ProviderDetailRequest providerDetailRequest) throws  IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse(JSON_CONTENT_TYPE), gson.toJson(providerDetailRequest));
        Call<ResponseWrapper<ProviderDetailBody>> call = restApi.getProviderDetailInfo(requestBody);
        return  call.execute();
    }

    /**
     * 搜索服务
     */
    public Response<ResponseWrapper<SearchServiceBody>> getSearchServiceList(ServiceRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<SearchServiceBody>> call = restApi.searchServiceInfo(requestBody);
        return call.execute();
    }

    /**
     * 工人点赞
     */
    public Response<ResponseWrapper<Void>> likeWorker(LikeWorkerRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.likeWorker(requestBody);
        return call.execute();
    }

    /**
     * 商户点赞
     */
    public Response<ResponseWrapper<Void>> likeBusiness(LikeBusinessRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.likeBusiness(requestBody);
        return call.execute();
    }

    /**
     * 工人收藏
     */
    public Response<ResponseWrapper<Void>> collectWorker(CollectWorkerRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.collectWorker(requestBody);
        return call.execute();
    }

    /**
     * 商户收藏
     */
    public Response<ResponseWrapper<Void>> collectBusiness(CollectBusinessRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.collectBusiness(requestBody);
        return call.execute();
    }

    /**
     * 商户收藏列表
     */
    public Response<ResponseWrapper<CollectedBusinessBody>> getCollectedBusinessList(String token) throws IOException {
        Call<ResponseWrapper<CollectedBusinessBody>> call = restApi.getCollectedBusinesses(jsonTokenRequest(token));
        return call.execute();
    }

    /**
     * 工人收藏列表
     */
    public Response<ResponseWrapper<CollectedWorkerBody>> getCollectedWorkerList(String token) throws IOException {
        Call<ResponseWrapper<CollectedWorkerBody>> call = restApi.getCollectedWorkers(jsonTokenRequest(token));
        return call.execute();
    }

    /**
     * 检查该商户/工人是否可拨打电话
     */
    public Response<ResponseWrapper<Void>> checkCallable(CheckCallableRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.checkCallable(requestBody);
        return call.execute();
    }

    /**
     * 获取消息列表
     *
     * @param token
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<MessageBody>> getMessageList(String token) throws IOException {
        Call<ResponseWrapper<MessageBody>> call = restApi.getMessageList(jsonTokenRequest(token));
        return call.execute();
    }

    /**
     * 获取未读消息数量
     *
     * @param token
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<UnreadMessageBody>> getUnreadMessageCount(String token) throws IOException {
        Call<ResponseWrapper<UnreadMessageBody>> call = restApi.getUnreadMessageCount(jsonTokenRequest(token));
        return call.execute();
    }

    /**
     * 删除消息
     */
    public Response<ResponseWrapper<Void>> deleteMessages(DeleteMessageRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.deleteMessages(requestBody);
        return call.execute();
    }

    /**
     * 设置已读
     */
    public Response<ResponseWrapper<Void>> readMessages(ReadMessageRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.readMessages(requestBody);
        return call.execute();
    }

    /**
     * 发送通话记录
     */
    public Response<ResponseWrapper<Void>> sendCallLog(CallLogRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.sendCallLog(requestBody);
        return call.execute();
    }


    /**
     * 上传个推Id
     */
    public Response<ResponseWrapper<Void>> uploadPushDeviceId(PushServiceIdRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.uploadPushDeviceId(requestBody);
        return call.execute();
    }

    /**
     * 清除个推Id
     */
    public Response<ResponseWrapper<Void>> clearPushDeviceId(TokenRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.clearPushDeviceId(requestBody);
        return call.execute();
    }

    /**
     * 更新检查
     */
    public Response<ResponseWrapper<CheckUpdateBody>> checkUpdate() throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, EMPTY_JSON);
        Call<ResponseWrapper<CheckUpdateBody>> call = restApi.checkUpdate(requestBody);
        return call.execute();
    }

    /**
     * 获得一个token为请求参数的请求体
     */
    private RequestBody jsonTokenRequest(String token) {
        String request = String.format("{\"Token\":\"%s\"}", token);
        return RequestBody.create(MEDIA_TYPE_JSON, request);
    }

    /**
     * 获取工人服务类型列表
     */
    public Response<ResponseWrapper<WorkerServiceTypeInfo>> getWorkerServiceTypeList(String id) throws IOException {

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new WorkerServiceTypeRequest(id)));
        Call<ResponseWrapper<WorkerServiceTypeInfo>> call = restApi.getWorkerServiceTypeList(requestBody);
        return call.execute();
    }


    /**
     * 下单
     */
    public Response<ResponseWrapper<CreatedOrderBody>> createOrder(CreateOrderRequest request) throws JSONException, IOException {
        RequestBody bodyJson = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request.getJsonParams()));
        Map<String, RequestBody> bodyMap = new ArrayMap<>(1 + request.getFilePath().size());

        bodyMap.put("json_data", bodyJson);

        int size = request.getFilePath().size();
        List<String> filePaths = request.getFilePath();
        for (int i = 0; i < size; i++) {
            File file = new File(filePaths.get(i));
            String fileName = file.getName();
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            bodyMap.put("file\"; filename=\"" + fileName, photo);
        }

        Call<ResponseWrapper<CreatedOrderBody>> call = restApi.createOrder(bodyMap);
        return call.execute();

    }

    /**
     * 获取我的红包
     */
    public Response<ResponseWrapper<MyRedPacketBody>> getMyRedPacket(String token, String status) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetMyRedPacketrRequest(token, status)));
        Call<ResponseWrapper<MyRedPacketBody>> call = restApi.getMyRedPacket(requestBody);
        return call.execute();
    }

    /**
     * 获取订单列表
     *
     * @param token
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<AllOrderBody>> getAllOrder(String token,String pageIndex,String pageSize) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetAllOrderRequest(token,pageIndex,pageSize)));
        Call<ResponseWrapper<AllOrderBody>> call = restApi.getAllOrder(requestBody);
        return call.execute();
    }

    /**
     * 获取订单列表,待分页
     *
     * @param token
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<AllOrderBody>> getOrder(String token, String pageSize, String pageIndex, String type) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetOrderRequest(token, pageSize, pageIndex, type)));
        Call<ResponseWrapper<AllOrderBody>> call = restApi.getAllOrder(requestBody);
        return call.execute();
    }


    /**
     * 获取工人评价
     */
    public Response<ResponseWrapper<EvaluationBody>> getWorkerEvaluations(EvaluationListRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<EvaluationBody>> call = restApi.getWorkerEvaluations(requestBody);
        return call.execute();
    }

    /**
     * 获取商户评价
     */
    public Response<ResponseWrapper<EvaluationBody>> getBusinessEvaluations(EvaluationListRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<EvaluationBody>> call = restApi.getBusinessEvaluations(requestBody);
        return call.execute();
    }


    /**
     * 获取商户服务内容
     */
    public Response<ResponseWrapper<ServiceContentBody>> getBusinessServiceContents(BusinessServiceRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<ServiceContentBody>> call = restApi.getBusinessServiceContents(requestBody);
        return call.execute();
    }

    /**
     * 工人纠错
     */
    public Response<ResponseWrapper<Void>> reportWorker(ReportWorkerRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.reportWorker(requestBody);
        return call.execute();
    }

    /**
     * 商户纠错
     */
    public Response<ResponseWrapper<Void>> reportBusiness(ReportBusinessRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.reportBusiness(requestBody);
        return call.execute();
    }

    /**
     * 获取优惠券列表
     */
    public Response<ResponseWrapper<CouponContentsBody>> getCouponList(GetCouponContentsRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<CouponContentsBody>> call = restApi.getCouponList(requestBody);
        return call.execute();
    }

    /**
     * 我的评价列表
     */
    public Response<ResponseWrapper<EvaluationBody>> getEvaluations(GetMyEvaluationRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<EvaluationBody>> call = restApi.getMyEvaluations(requestBody);
        return call.execute();
    }

    /**
     * 我的账户
     */
    public Response<ResponseWrapper<AccountInfoBody>> getAccount(TokenRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<AccountInfoBody>> call = restApi.getAccountInfo(requestBody);
        return call.execute();
    }

    /**
     * 账户明细
     */
    public Response<ResponseWrapper<AccountDetailBody>> getAccountDetails(TokenRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<AccountDetailBody>> call = restApi.getAccountDetails(requestBody);
        return call.execute();
    }

    /**
     * 支付明细
     */
    public Response<ResponseWrapper<PayDetailBody>> getPayDetails(TokenRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<PayDetailBody>> call = restApi.getPayDetails(requestBody);
        return call.execute();
    }

    /**
     * 充值列表
     */
    public Response<ResponseWrapper<RechargeListBody>> getRecharges() throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, EMPTY_JSON);
        Call<ResponseWrapper<RechargeListBody>> call = restApi.getRecharges(requestBody);
        return call.execute();
    }

    /**
     * 获取支付宝签名
     */
    public Response<ResponseWrapper<AlipaySignBody>> getAlipaySign(GetPaySignRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<AlipaySignBody>> call = restApi.getAlipaySign(requestBody);
        return call.execute();
    }

    /**
     * 获取微信付宝签名
     */
    public Response<ResponseWrapper<WeChatPayBody>> getWeChatPaySign(GetPaySignRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<WeChatPayBody>> call = restApi.getWeChatSign(requestBody);
        return call.execute();
    }

    /**
     * 评价
     */
    public Response<ResponseWrapper<Void>> addEvaluation(AddEvaluationRequest request, List<String> pictures) throws IOException {
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>();
        for (int i = 0; i < pictures.size(); i++) {
            String filePath = pictures.get(i);
            File file = new File(filePath);
            String fileName = file.getName();
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            requestBodyMap.put("file\"; filename=\"" + fileName, photo);
        }
        RequestBody json = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        requestBodyMap.put("json_data", json);
        Call<ResponseWrapper<Void>> call = restApi.evaluate(requestBodyMap);
        return call.execute();
    }

    /**
     * 评价订单
     */
    public Response<ResponseWrapper<Void>> evaluateOrder(EvaluateOrderRequest request, List<String> pictures) throws IOException {
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>();
        for (int i = 0; i < pictures.size(); i++) {
            String filePath = pictures.get(i);
            File file = new File(filePath);
            String fileName = file.getName();
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            requestBodyMap.put("file\"; filename=\"" + fileName, photo);
        }
        RequestBody json = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        requestBodyMap.put("json_data", json);
        Call<ResponseWrapper<Void>> call = restApi.evaluateOrder(requestBodyMap);
        return call.execute();
    }


    /**
     * 获取订单详情
     */
    public Response<ResponseWrapper<OrderDetailBody>> getOrderDetail(String token, String orderId) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetOrderDetailRequest(token, orderId)));
        Call<ResponseWrapper<OrderDetailBody>> call = restApi.getOrderDetail(requestBody);
        return call.execute();
    }


    /**
     * 获取订单支付的支付宝支付签名
     */
    public Response<ResponseWrapper<OrderPayAliSignBody>> getOrderPayAliSign(String token, GetOrderPayAliSignParams params) throws IOException {
        String orderCode = params.orderCode;
        String needPay = params.needPay;
        String balancePay = params.balancePay;
        String redEnvelopIds = params.redEnvelopIds;
        GetOrderPayAliSignRequest request = new GetOrderPayAliSignRequest(token, orderCode, redEnvelopIds, needPay, balancePay);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<OrderPayAliSignBody>> call = restApi.GetOrderPayAliSign(requestBody);

        return call.execute();
    }

    /**
     * 获取订单支付的微信支付签名
     */
    public Response<ResponseWrapper<OrderPayWXSignBody>> getOrderPayWXSign(String token, GetOrderpayWxSignParams params) throws IOException {
        String orderCode = params.orderCode;
        String needPay = params.needPay;
        String balancePay = params.balancePay;
        String redEnvelopIds = params.redEnvelopIds;
        GetOrderPayWxSignRequest request = new GetOrderPayWxSignRequest(token, orderCode, redEnvelopIds, needPay, balancePay);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<OrderPayWXSignBody>> call = restApi.GetOrderPayWXSign(requestBody);
        return call.execute();
    }

    /**
     * 取消订单
     */
    public Response<ResponseWrapper<Void>> cancelOder(String token, String orderId,String cancelReason) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new CancelOrderRequest(token, orderId,cancelReason)));
        Call<ResponseWrapper<Void>> call = restApi.cancelOrder(requestBody);
        return call.execute();
    }

    /**
     * 删除订单
     */
    public Response<ResponseWrapper<Void>> deleteOrder(String token, String orderId) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new DeleteOrderRequest(token, orderId)));
        Call<ResponseWrapper<Void>> call = restApi.deleteOrder(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<Void>> confirmOrder(String token, String orderId) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new ConfirmOrderRequest(token, orderId)));
        Call<ResponseWrapper<Void>> call = restApi.confirmOrder(requestBody);
        return call.execute();
    }

    /**
     * 获取分享内容
     */
    public Response<ResponseWrapper<ShareInfoBody>> getShareInfo(GetShareInfoRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<ShareInfoBody>> call = restApi.getShareInfo(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<Void>> payOrderByBalance(String token, String orderId, String couponId, String balanceMoney) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new BalancePayRequest(token, orderId, couponId, balanceMoney)));
        Call<ResponseWrapper<Void>> call = restApi.payOrderByBalance(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<DirectPayInfo>> getDirectPayId(String token, String receiverId, String receiverType, String paySource, String totalMoney, String serviceTypeID) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetDirectPayRequest(token, receiverId, receiverType, paySource, totalMoney, serviceTypeID)));
        Call<ResponseWrapper<DirectPayInfo>> call = restApi.getDirectPayId(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<OrderPayWXSignBody>> getDirectPayWXSign(String token, String directPayId, String balancePayMoney) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetDirectPayWXSignRequest(token, directPayId, balancePayMoney)));
        Call<ResponseWrapper<OrderPayWXSignBody>> call = restApi.getDirectPayWXSign(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<AlipaySignBody>> getDirectPayAliSign(String token, String directPayId, String balancePayMoney) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetDirectPayAliSignRequest(token, directPayId, balancePayMoney)));
        Call<ResponseWrapper<AlipaySignBody>> call = restApi.getDirectPayAliSign(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<Void>> directBalancePay(String token, String directPayId) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new DirectBalancePayRequest(token, directPayId)));
        Call<ResponseWrapper<Void>> call = restApi.directBalancePay(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<FirstOpenAppInfo>> getFirstCoupon(TokenRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<FirstOpenAppInfo>> call = restApi.getFirstCoupon(requestBody);
        return call.execute();
    }

    /**
     * 上传扫描到的二维码
     */
    public Response<ResponseWrapper<PushInfo>> uploadQrCodeUrl(UploadQrCodeRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<PushInfo>> call = restApi.uploadUrl(requestBody);
        return call.execute();
    }

    /**
     * 增加分享内容
     */
    public Response<ResponseWrapper<Void>> addShareRecord(AddShareRecord request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<Void>> call = restApi.addShareRecord(requestBody);
        return call.execute();
    }


    @Deprecated
    public Response<ResponseWrapper<DescriptionInfo>> getDescription(String code) throws IOException {
       /* RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new GetDescriptionRequest(code)));
        Call<ResponseWrapper<DescriptionInfo>> call = restApi.getDescription(requestBody);*/
        return null;
    }

    public Response<ResponseWrapper<ServiceSearchInfo>> searchServices(SearchParameter searchParameter) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(searchParameter));
        Call<ResponseWrapper<ServiceSearchInfo>> call = restApi.searchService(requestBody);
        return call.execute();
    }




    /**
     * 获取工人标签
     */
    public Response<ResponseWrapper<WorkerTagsBody>> getWorkerTags(GetWorkerTagsRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<WorkerTagsBody>> call = restApi.getWorkerTags(requestBody);
        return call.execute();
    }

    /**
     * 获取商户标签
     */
    public Response<ResponseWrapper<BusinessTagsBody>> getBusinessTags(GetBusinessTagsRequest request) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(request));
        Call<ResponseWrapper<BusinessTagsBody>> call = restApi.getBusinessTags(requestBody);
        return call.execute();
    }
    /**
     * Provider/GetAds:获取活动信息
     */
    public Response<ResponseWrapper<AdsInfo>> getAdsInfo(String token)throws IOException{
        RequestBody requestBody;
        if(TextUtils.isEmpty(token)) {
           requestBody = RequestBody.create(MEDIA_TYPE_JSON, EMPTY_JSON);
        }else{
            TokenRequest tokenRequest = new TokenRequest(token);
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(tokenRequest));
        }
        Call<ResponseWrapper<AdsInfo>> call = restApi.getAdsInfo(requestBody);
        return call.execute();
    }
    /**
     * 获取弹窗的值
     */
    public Response<ResponseWrapper<PopupVer>> getPopuVer()throws IOException{
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON,EMPTY_JSON);
        Call<ResponseWrapper<PopupVer>> call = restApi.getPopuVer(requestBody);
        return call.execute();
    }

    /**
     * Banner
     */
    public Response<ResponseWrapper<List<BannerInfo>>> getBannersInfo()throws IOException{
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON,EMPTY_JSON);
        Call<ResponseWrapper<List<BannerInfo>>> call = restApi.getBannersInfo(requestBody);
        return call.execute();
    }

    /**
     * 获取工人/商户头像列表
     * @return
     * @throws IOException
     */
    public Response<ResponseWrapper<AvatarsEntity>> getAvatar(AvatarRequest request)throws IOException{
        RequestBody requestBody = RequestBody.create(MediaType.parse(JSON_CONTENT_TYPE),gson.toJson(request));
        Call<ResponseWrapper<AvatarsEntity>> call = restApi.getAvatars(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<List<AddressEntity>>> getServiceAddressList(String token) throws IOException {
        TokenRequest tokenRequest = new TokenRequest(token);
        Call<ResponseWrapper<List<AddressEntity>>> call = restApi.getServiceAddressList(tokenRequest);
        return call.execute();

    }

    public Response<ResponseWrapper<IsFirstOrderInfo>> getIsFirstOrderInfo(String token)throws IOException {
        TokenRequest tokenRequest = new TokenRequest(token);
        Call<ResponseWrapper<IsFirstOrderInfo>> call = restApi.getIsFirstOrderInfo(tokenRequest);
        return call.execute();
    }

    public Response<ResponseWrapper<Void>> deleteServiceAddress(String token, String id) throws IOException {
        DeleteServiceAddressRequest deleteRequest = new DeleteServiceAddressRequest(token, id);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(deleteRequest));
        Call<ResponseWrapper<Void>> call = restApi.deleteServiceAddress(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<AddressIdEntity>> addServiceAddress(String token, AddressEntity param) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new AddServiceAddressRequest(token, param)));
        Call<ResponseWrapper<AddressIdEntity>> call = restApi.addServiceAddress(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<Void>> updateServiceAddress(String token, AddressEntity param) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new AddServiceAddressRequest(token, param)));
        Call<ResponseWrapper<Void>> call = restApi.updateServiceAddress(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<List<ServiceScheduleEntity>>> getServiceDSchedule(String serviceTypId) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(new ServiceTypeIdRequest(serviceTypId)));
        Call<ResponseWrapper<List<ServiceScheduleEntity>>> call = restApi.getServiceDSchedule(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<PriceEntity>> getServicePrice(PriceParam param) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(param));
        Call<ResponseWrapper<PriceEntity>> call = restApi.getServicePrice(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<ActivityNgInfoNew>> getAvailableActivity(String token, String serviceTypeId) throws IOException {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON,gson.toJson(new AvailableActivityRequest(token,serviceTypeId)));
        Call<ResponseWrapper<ActivityNgInfoNew>> call = restApi.getAvailableActivity(requestBody);
        return call.execute();
    }

    public Response<ResponseWrapper<CreatedOrderBody>> placeDirectOrder(DirectOrderParam param) throws IOException {
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>();
        for (int i = 0; i < param.getPhotoPaths().size(); i++) {
            String filePath = param.getPhotoPaths().get(i);
            File file = new File(filePath);
            String fileName = file.getName();
            RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            requestBodyMap.put("file\"; filename=\"" + fileName, photo);
        }
        requestBodyMap.put("json_data", RequestBody.create(MEDIA_TYPE_JSON,gson.toJson(param)));
        Call<ResponseWrapper<CreatedOrderBody>> call = restApi.placeDirectOrder(requestBodyMap);
        return call.execute();
    }

    public Response<ResponseWrapper<SystemConfigEntity>> getSystemConfig(String token) throws IOException {
        TokenRequest request = new TokenRequest(token);
        Call<ResponseWrapper<SystemConfigEntity>> call = restApi.getSystemConfig(RequestBody.create(MEDIA_TYPE_JSON,gson.toJson(request)));
        return call.execute();
    }

    public Response<ResponseWrapper<Void>> addRecommendInfo(String token, String code) throws IOException {
        AddRecommendInfoRequest recommendInfoRequest = new AddRecommendInfoRequest(token, code);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(recommendInfoRequest));
        Call<ResponseWrapper<Void>> call = restApi.addRecommendInfo(requestBody);
       return  call.execute();
    }
}

package com.homepaas.sls.di.component;

import com.homepaas.sls.Global;
import com.homepaas.sls.data.entity.mapper.CallLogEntityMapper;
import com.homepaas.sls.data.entity.mapper.OrderMapper;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.module.ApplicationModule;
import com.homepaas.sls.di.module.DataModule;
import com.homepaas.sls.di.module.DomainModule;
import com.homepaas.sls.di.module.PushModule;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.CheckUpdateInteractor;
import com.homepaas.sls.domain.interactor.SearchServiceListInteractor;
import com.homepaas.sls.domain.repository.AccountDetailsRepo;
import com.homepaas.sls.domain.repository.AccountInfoRepo;
import com.homepaas.sls.domain.repository.AddRecommendInfoRepo;
import com.homepaas.sls.domain.repository.AddressRepo;
import com.homepaas.sls.domain.repository.AdsRepo;
import com.homepaas.sls.domain.repository.AlipaySignExRepo;
import com.homepaas.sls.domain.repository.AllServiceRepo;
import com.homepaas.sls.domain.repository.AvatarRepo;
import com.homepaas.sls.domain.repository.BannersInfoRepo;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;
import com.homepaas.sls.domain.repository.BusinessServiceListRepo;
import com.homepaas.sls.domain.repository.BusinessServicePriceRepo;
import com.homepaas.sls.domain.repository.BusinessTagsRepo;
import com.homepaas.sls.domain.repository.CallLogRepo;
import com.homepaas.sls.domain.repository.CheckIsReceivedRedCoupsRepo;
import com.homepaas.sls.domain.repository.CityRepo;
import com.homepaas.sls.domain.repository.CommentRepo;
import com.homepaas.sls.domain.repository.CommonCouponInfoRepo;
import com.homepaas.sls.domain.repository.CouponContentsRepo;
import com.homepaas.sls.domain.repository.CreateDirectPayExRepo;
import com.homepaas.sls.domain.repository.DescriptionInfoRepo;
import com.homepaas.sls.domain.repository.FavourRepo;
import com.homepaas.sls.domain.repository.FirstCouponRepo;
import com.homepaas.sls.domain.repository.FirstOpenAppInfoRepo;
import com.homepaas.sls.domain.repository.FirstOrderInfoRepo;
import com.homepaas.sls.domain.repository.GetActivityExRepo;
import com.homepaas.sls.domain.repository.GetAtStoreActivityRepo;
import com.homepaas.sls.domain.repository.GetErrandServiceDetailRepo;
import com.homepaas.sls.domain.repository.GetKdTrackInfoRepo;
import com.homepaas.sls.domain.repository.GetOrderListRepo;
import com.homepaas.sls.domain.repository.GetStarLevelRepo;
import com.homepaas.sls.domain.repository.HotSearchServiceRepo;
import com.homepaas.sls.domain.repository.HotServiceInfoRepo;
import com.homepaas.sls.domain.repository.LifeServiceRepo;
import com.homepaas.sls.domain.repository.LoginInfoRepo;
import com.homepaas.sls.domain.repository.MerchantServicePriceListRepo;
import com.homepaas.sls.domain.repository.MessageRepo;
import com.homepaas.sls.domain.repository.NewAccountInfoRepo;
import com.homepaas.sls.domain.repository.OrderCancelReasonRepo;
import com.homepaas.sls.domain.repository.OrderInfoRepo;
import com.homepaas.sls.domain.repository.OtherRepo;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;
import com.homepaas.sls.domain.repository.PlaceOrderRepo;
import com.homepaas.sls.domain.repository.PopuverRepo;
import com.homepaas.sls.domain.repository.QueryActivityServicePriceRepo;
import com.homepaas.sls.domain.repository.QueryServicePriceRepo;
import com.homepaas.sls.domain.repository.QueryServiceRepo;
import com.homepaas.sls.domain.repository.RechargeInfoRepo;
import com.homepaas.sls.domain.repository.RechargeListExRepo;
import com.homepaas.sls.domain.repository.RecommendServiceRepo;
import com.homepaas.sls.domain.repository.SearchRepo;
import com.homepaas.sls.domain.repository.SearchServiceRepo;
import com.homepaas.sls.domain.repository.SearchTopicRepo;
import com.homepaas.sls.domain.repository.ServiceRepo;
import com.homepaas.sls.domain.repository.ServiceSearchListRepo;
import com.homepaas.sls.domain.repository.ServiceTimeStartAtRepo;
import com.homepaas.sls.domain.repository.ServiceTypeExRepository;
import com.homepaas.sls.domain.repository.ServiceTypeListRepo;
import com.homepaas.sls.domain.repository.SettlementListRepository;
import com.homepaas.sls.domain.repository.ShareRepo;
import com.homepaas.sls.domain.repository.ShortCutRepo;
import com.homepaas.sls.domain.repository.SubmitApplyClaimsRepo;
import com.homepaas.sls.domain.repository.SubmitEvaluationRepo;
import com.homepaas.sls.domain.repository.SuperDiscountRepo;
import com.homepaas.sls.domain.repository.TrackOrderStatusRepo;
import com.homepaas.sls.domain.repository.VerifyTokenRepo;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;
import com.homepaas.sls.domain.repository.WorkerServicePriceListRepo;
import com.homepaas.sls.domain.repository.WorkerTagsRepo;
import com.homepaas.sls.domain.repository.WxpaySignExRepo;
import com.homepaas.sls.location.LocationHelper;
import com.homepaas.sls.mvp.model.mapper.CallLogModelMapper;
import com.homepaas.sls.mvp.model.mapper.LifeServiceModelMapper;
import com.homepaas.sls.mvp.model.mapper.MessageMapper;
import com.homepaas.sls.mvp.model.mapper.PaymentMapper;
import com.homepaas.sls.mvp.model.mapper.PersonalInfoModelMapper;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.model.mapper.TagsInfoMapper;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.pushservice.PushUtil;
import com.homepaas.sls.ui.FirstOpenAppActivity;
import com.homepaas.sls.ui.SplashActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.util.ToastUtil;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2015/12/22.
 */

@Singleton
@Component(modules = {ApplicationModule.class, DomainModule.class, DataModule.class, PushModule.class})
public interface ApplicationComponent {

    void inject(CommonBaseActivity activity);
    void inject(SplashActivity activity);
    void inject(FirstOpenAppActivity activity);

    //expose to sub
    PersonalInfoModelMapper personalInfoModelMapper();

    Navigator navigator();

    LocationHelper locationHelper();

    PostExecutionThread postExecutionThread();

    @Named("concurrent")
    JobExecutor concurrentJobExecutor();

    @Named("sequence")
    JobExecutor sequenceJobExecutor();

    ServiceSearchListRepo serviceSearchListRepo();

    SearchServiceListInteractor searchServiceListInteractor();

    ServiceInfoModelMapper serviceInfoModelMapper();

    TagsInfoMapper tagsInfoMapper();

    BusinessInfoRepo businessInfoRepo();

    BusinessTagsRepo businessTagsRepo();

    WorkerInfoRepo workerInfoRepo();

    WorkerTagsRepo workerTagsRepo();

    LifeServiceModelMapper lifeServiceModelMapper();

    PersonalInfoRepo personalInfoRepo();

    LifeServiceRepo lifeServiceRepo();

    ServiceTypeListRepo serviceTypeListRepo();

    MessageMapper messageMapper();

    MessageRepo messageRepo();

    CallLogEntityMapper callLogEntityMapper();
    OrderMapper orderMapper();

    CallLogModelMapper callLogModelMapper();

    CallLogRepo callLogRepo();

    OrderInfoRepo orderInfoRepo();

    FavourRepo favourRepo();

//    //红包仓库接口
   CouponContentsRepo couponContentsRepo();

    CheckUpdateInteractor update();

    LoginInfoRepo accountRepo();

    CommentRepo commentRepo();

    AccountInfoRepo accountInfoRepo();

    PaymentMapper paymentMapper();

    ShareRepo shareRepo();

    OtherRepo otherRepo();

    PushUtil push();
    FirstCouponRepo firstAppRepo();
    DescriptionInfoRepo descriptionRepo();
    RestApiHelper restA();
    PersonalInfoPDataSource personalInfoPDDatasource();

    Global global();

    SearchRepo search();


    BannersInfoRepo bannersInfoRepo();
    AdsRepo adsRepo();

    ServiceTypeExRepository serviceTypeExRepository();

    AddressRepo addressRepo();
    ServiceRepo service();
    AvatarRepo avatarRepo();

    AddRecommendInfoRepo ddRecommendInfoRepo();

    SettlementListRepository settlementListRepository();
    AccountDetailsRepo accountDetailsRepo();
    NewAccountInfoRepo newAccountInfoRepo();
    RechargeListExRepo rechargeListExRepo();
    AlipaySignExRepo alipaySignExRepo();
    WxpaySignExRepo wxpaySignExRepo();
    RechargeInfoRepo rechargeInfoRepo();
    SearchServiceRepo searchServiceRepo();
    HotServiceInfoRepo hotServiceInfoRepo();
    PopuverRepo popuverRepo();
    BusinessServicePriceRepo businessServicePriceRepo();
    BusinessServiceListRepo businessServiceListRepo();
    MerchantServicePriceListRepo merchantServicePriceListRepo();
    WorkerServicePriceListRepo workerServicePriceListRepo();
    FirstOrderInfoRepo firstOrderInfoRepo();
    CreateDirectPayExRepo createDirectPayExRepo();
    GetAtStoreActivityRepo getAtStoreActivityRepo();
    CheckIsReceivedRedCoupsRepo checkIsReceivedRedCoupsRepo();
    VerifyTokenRepo verifyTokenRepo();

    /**
     * 首页改版3.3.0
     */
    ShortCutRepo shortCutRepo();
    AllServiceRepo allServiceRepo();
    QueryServiceRepo queryServiceRepo();
    RecommendServiceRepo recommendServiceRepo();
    SearchTopicRepo searchTopicRepo();
    HotSearchServiceRepo hotSearchServiceRepo();
    QueryServicePriceRepo queryServicePriceRepo();
    ServiceTimeStartAtRepo serviceTimeStartAtRepo();
    PlaceOrderRepo placeOrderRepo();
    GetActivityExRepo getActivityExRepo();
    CommonCouponInfoRepo getCommonCouponInfoRepo();
    OrderCancelReasonRepo getOrderCancelReasonRepo();
    QueryActivityServicePriceRepo getQueryActivityServicePriceRepo();
    GetKdTrackInfoRepo getGetKdTrackInfoRepo();
    GetErrandServiceDetailRepo getExpressDetailRepo();
    SubmitApplyClaimsRepo submitApplyClaimsRepo();
    TrackOrderStatusRepo trackOrderStatusRepo();

    /**
     * 3.5.0
     */
    SuperDiscountRepo getSuperDiscountRepo();
    CityRepo getCityRepo();
    GetOrderListRepo GetOrderListRepo();
    GetStarLevelRepo GetStarLevelRepo();
    SubmitEvaluationRepo SubmitEvaluationRepo();
    ToastUtil getToastUtil();
    FirstOpenAppInfoRepo firstOpenAppInfoRepo();
}

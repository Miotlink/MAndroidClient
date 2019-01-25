package com.homepaas.sls.di.module;

import com.homepaas.sls.data.repository.AccountDetailsRepoImpl;
import com.homepaas.sls.data.repository.AccountInfoRepoImpl;
import com.homepaas.sls.data.repository.AddRecommendInfoRepoImpl;
import com.homepaas.sls.data.repository.AddressRepoImpl;
import com.homepaas.sls.data.repository.AdsRepoImpl;
import com.homepaas.sls.data.repository.AlipaySignExRepoImpl;
import com.homepaas.sls.data.repository.AllServiceRepoImpl;
import com.homepaas.sls.data.repository.AvatarRepoImpl;
import com.homepaas.sls.data.repository.BannersInfoRepoImpl;
import com.homepaas.sls.data.repository.BusinessServiceListImpl;
import com.homepaas.sls.data.repository.BusinessServicePriceImpl;
import com.homepaas.sls.data.repository.BusinessTagsRepoImpl;
import com.homepaas.sls.data.repository.CheckIsReceivedRedCoupsImpl;
import com.homepaas.sls.data.repository.CityRepoImpl;
import com.homepaas.sls.data.repository.CommonCouponInfoImpl;
import com.homepaas.sls.data.repository.CreateDirectPayExImpl;
import com.homepaas.sls.data.repository.DescriptionInfoRepoImpl;
import com.homepaas.sls.data.repository.FirstCouponRepoImpl;
import com.homepaas.sls.data.repository.FirstOpenAppInfoImpl;
import com.homepaas.sls.data.repository.FirstOrderInfoRepoImpl;
import com.homepaas.sls.data.repository.GetActivityExImpl;
import com.homepaas.sls.data.repository.GetAtStoreActivityImpl;
import com.homepaas.sls.data.repository.GetErrandServiceDetailImpl;
import com.homepaas.sls.data.repository.GetKdTrackInfoImpl;
import com.homepaas.sls.data.repository.GetOrderListImpl;
import com.homepaas.sls.data.repository.GetStarLevelImpl;
import com.homepaas.sls.data.repository.HotSearchServiceRepoImpl;
import com.homepaas.sls.data.repository.HotServiceInfoRepoImpl;
import com.homepaas.sls.data.repository.LoginInfoRepoImpl;
import com.homepaas.sls.data.repository.BusinessInfoRepoImpl;
import com.homepaas.sls.data.repository.CallLogRepoImpl;
import com.homepaas.sls.data.repository.CommentRepoImpl;
import com.homepaas.sls.data.repository.CouponContentsRepoImpl;
import com.homepaas.sls.data.repository.FavourRepoImpl;
import com.homepaas.sls.data.repository.LifeServiceRepoImpl;
import com.homepaas.sls.data.repository.MerchantServicePriceListImpl;
import com.homepaas.sls.data.repository.MessageRepoImpl;
import com.homepaas.sls.data.repository.NewAccountInfoRepoImpl;
import com.homepaas.sls.data.repository.OrderCancelReasonImpl;
import com.homepaas.sls.data.repository.OrderInfoRepoImpl;
import com.homepaas.sls.data.repository.OtherRepoImpl;
import com.homepaas.sls.data.repository.PersonalInfoRepoImpl;
import com.homepaas.sls.data.repository.PlaceOrderImpl;
import com.homepaas.sls.data.repository.PopuverRepoImpl;
import com.homepaas.sls.data.repository.PushRepoImpl;
import com.homepaas.sls.data.repository.QueryActivityServicePriceImpl;
import com.homepaas.sls.data.repository.QueryServicePriceImpl;
import com.homepaas.sls.data.repository.QueryServiceRepoImpl;
import com.homepaas.sls.data.repository.RechargeInfoRepoImpl;
import com.homepaas.sls.data.repository.RechargeListExRepoImpl;
import com.homepaas.sls.data.repository.RecommendServiceRepoImpl;
import com.homepaas.sls.data.repository.SearchRepoImpl;
import com.homepaas.sls.data.repository.SearchServiceRepoImpl;
import com.homepaas.sls.data.repository.SearchTopicRepoImpl;
import com.homepaas.sls.data.repository.ServiceRepoImpl;
import com.homepaas.sls.data.repository.ServiceSearchListRepoImpl;
import com.homepaas.sls.data.repository.ServiceTimeStartAtImpl;
import com.homepaas.sls.data.repository.ServiceTypeExRepositoryImpl;
import com.homepaas.sls.data.repository.ServiceTypeListRepoImpl;
import com.homepaas.sls.data.repository.SettlementListRepoImpl;
import com.homepaas.sls.data.repository.ShareRepoImpl;
import com.homepaas.sls.data.repository.ShortCutRepoImpl;
import com.homepaas.sls.data.repository.SubmitApplyClaimsImpl;
import com.homepaas.sls.data.repository.SubmitEvaluationImpl;
import com.homepaas.sls.data.repository.TrackOrderStatusImpl;
import com.homepaas.sls.data.repository.SuperDiscountRepoImpl;
import com.homepaas.sls.data.repository.VerifyTokenRepoImpl;
import com.homepaas.sls.data.repository.WorkerInfoRepoImpl;
import com.homepaas.sls.data.repository.WorkerServicePriceListImpl;
import com.homepaas.sls.data.repository.WorkerTagsRepoImpl;
import com.homepaas.sls.data.repository.WxpaySignExRepoImpl;
import com.homepaas.sls.domain.repository.AccountDetailsRepo;
import com.homepaas.sls.domain.repository.AccountInfoRepo;
import com.homepaas.sls.domain.repository.AddRecommendInfoRepo;
import com.homepaas.sls.domain.repository.AddressRepo;
import com.homepaas.sls.domain.repository.AdsRepo;
import com.homepaas.sls.domain.repository.AlipaySignExRepo;
import com.homepaas.sls.domain.repository.AllServiceRepo;
import com.homepaas.sls.domain.repository.AvatarRepo;
import com.homepaas.sls.domain.repository.BannersInfoRepo;
import com.homepaas.sls.domain.repository.BusinessServiceListRepo;
import com.homepaas.sls.domain.repository.BusinessServicePriceRepo;
import com.homepaas.sls.domain.repository.BusinessTagsRepo;
import com.homepaas.sls.domain.repository.CheckIsReceivedRedCoupsRepo;
import com.homepaas.sls.domain.repository.CityRepo;
import com.homepaas.sls.domain.repository.CommonCouponInfoRepo;
import com.homepaas.sls.domain.repository.CreateDirectPayExRepo;
import com.homepaas.sls.domain.repository.DescriptionInfoRepo;
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
import com.homepaas.sls.domain.repository.LoginInfoRepo;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;
import com.homepaas.sls.domain.repository.CallLogRepo;
import com.homepaas.sls.domain.repository.CommentRepo;
import com.homepaas.sls.domain.repository.CouponContentsRepo;
import com.homepaas.sls.domain.repository.FavourRepo;
import com.homepaas.sls.domain.repository.LifeServiceRepo;
import com.homepaas.sls.domain.repository.MerchantServicePriceListRepo;
import com.homepaas.sls.domain.repository.MessageRepo;
import com.homepaas.sls.domain.repository.NewAccountInfoRepo;
import com.homepaas.sls.domain.repository.OrderCancelReasonRepo;
import com.homepaas.sls.domain.repository.OrderInfoRepo;
import com.homepaas.sls.domain.repository.OtherRepo;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;
import com.homepaas.sls.domain.repository.PlaceOrderRepo;
import com.homepaas.sls.domain.repository.PopuverRepo;
import com.homepaas.sls.domain.repository.PushRepo;
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
import com.homepaas.sls.domain.repository.TrackOrderStatusRepo;
import com.homepaas.sls.domain.repository.SuperDiscountRepo;
import com.homepaas.sls.domain.repository.VerifyTokenRepo;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;
import com.homepaas.sls.domain.repository.WorkerServicePriceListRepo;
import com.homepaas.sls.domain.repository.WorkerTagsRepo;
import com.homepaas.sls.domain.repository.WxpaySignExRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2015/12/24.
 *
 */

@Module
public class DomainModule {


    @Provides
    @Singleton
    PersonalInfoRepo providePersonalInfoRepo(PersonalInfoRepoImpl personalInfoRepo) {
        return personalInfoRepo;
    }

    @Provides
    @Singleton
    ServiceSearchListRepo provideServiceListRepo(ServiceSearchListRepoImpl searchListRepo) {
        return searchListRepo;
    }

    @Provides
    @Singleton
    BusinessInfoRepo provideBusinessInfoRepo(BusinessInfoRepoImpl businessInfoRepo) {
        return businessInfoRepo;
    }

    @Provides
    @Singleton
    WorkerInfoRepo provideWorkerInfoRepo(WorkerInfoRepoImpl workerInfoRepo) {
        return workerInfoRepo;
    }

    @Provides
    @Singleton
    LifeServiceRepo provideLifeServiceRepo(LifeServiceRepoImpl lifeServiceRepo) {
        return lifeServiceRepo;
    }

    @Provides
    @Singleton
    MessageRepo provideMessageRepo(MessageRepoImpl messageRepo) {
        return messageRepo;
    }

    @Provides
    @Singleton
    CallLogRepo provideCallLogRepo(CallLogRepoImpl callLogRepo) {
        return callLogRepo;
    }


    @Provides
    @Singleton
    ServiceTypeListRepo provideServiceTypeRepo(ServiceTypeListRepoImpl ServiceTypeListRepoImpl) {
        return ServiceTypeListRepoImpl;
    }


    @Provides
    @Singleton
    OrderInfoRepo provideOrderInfoRepo(OrderInfoRepoImpl orderInfoRepo) {
        return orderInfoRepo;
    }

    @Provides
    @Singleton
    FavourRepo provideFavourRepo(FavourRepoImpl favourRepo) {
        return favourRepo;
    }

    /**
     * 红包仓库接口注入（利用多态，从CouponContentsRepoImpl得到CouponContentsRepo实例）
     * @param couponContentsRepo
     * @return
     */
    @Provides
    @Singleton
    CouponContentsRepo provideCouponContentsRepo(CouponContentsRepoImpl couponContentsRepo) {
        return couponContentsRepo;
    }

    @Provides
    @Singleton
    LoginInfoRepo provideLoginInfoRepo(LoginInfoRepoImpl loginInfoRepo) {
        return loginInfoRepo;
    }

    @Provides
    @Singleton
    CommentRepo provideCommentRepo(CommentRepoImpl commentRepo) {
        return commentRepo;
    }

    @Provides
    @Singleton
    AccountInfoRepo provideAccountInfoRepo(AccountInfoRepoImpl accountInfoRepo) {
        return accountInfoRepo;
    }

    @Provides
    @Singleton
    ShareRepo provideShareRepo(ShareRepoImpl shareRepo) {
        return shareRepo;
    }

    @Provides
    @Singleton
    OtherRepo provideOtherRepo(OtherRepoImpl otherRepo){
        return otherRepo;
    }

    @Provides
    @Singleton
    FirstCouponRepo provideFirstCouponRepo(FirstCouponRepoImpl firstCouponRepo) {
        return firstCouponRepo;
    }

    @Provides
    @Singleton
    DescriptionInfoRepo provideDescriptionInfoRepo(DescriptionInfoRepoImpl descriptionInfoRepo) {
        return descriptionInfoRepo;
    }

    @Provides
    @Singleton
    PushRepo providePushRepo(PushRepoImpl pushRepo) {
        return pushRepo;
    }

    @Provides
    @Singleton
    WorkerTagsRepo provideWorkerTagsRepo(WorkerTagsRepoImpl workerTagsRepo) {
        return workerTagsRepo;
    }

    @Provides
    @Singleton
    BusinessTagsRepo provideBusinessTagsRepo(BusinessTagsRepoImpl businessTagsRepo){
        return businessTagsRepo;
    }

    @Provides
    @Singleton
    SearchRepo provideSearchRepo(SearchRepoImpl searchRepo) {
        return searchRepo;
    }

    @Provides
    @Singleton
    BannersInfoRepo ProvideBannersInfoRepo(BannersInfoRepoImpl bannersInfoRepo) {
        return bannersInfoRepo;
    }
    @Provides
    @Singleton
    AdsRepo providerAdsRepo(AdsRepoImpl adsRepo){
        return adsRepo;
    }

    @Provides
    @Singleton
    PopuverRepo providerPopuverRepo(PopuverRepoImpl popuverRepo){
        return popuverRepo;
    }

    @Provides
    @Singleton
    ServiceTypeExRepository provideServiceTypeExRepository(ServiceTypeExRepositoryImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    AddressRepo provideAddressRepo(AddressRepoImpl addressRepoImpl) {
        return addressRepoImpl;
    }

    @Provides
    @Singleton
    ServiceRepo provideServiceRepo(ServiceRepoImpl serviceRepo) {
        return serviceRepo;
    }

    @Provides
    @Singleton
    AvatarRepo provideAvatarRepo(AvatarRepoImpl avatarRepo){
        return avatarRepo;
    }

    @Provides
    @Singleton
    AddRecommendInfoRepo procideAddRecommendInfoRepo(AddRecommendInfoRepoImpl addRecommendInfoRepo){
        return addRecommendInfoRepo;
    }

    @Provides
    @Singleton
    SettlementListRepository provideSettlementListRepository(SettlementListRepoImpl impl){
        return  impl;
    }

    @Provides
    @Singleton
    AccountDetailsRepo provideAccountDetailsRepo(AccountDetailsRepoImpl impl){
        return impl;
    }


    @Provides
    @Singleton
    NewAccountInfoRepo provideNewAccountInfoRepo(NewAccountInfoRepoImpl impl){
        return impl;    }

    @Provides
    @Singleton
    AlipaySignExRepo provideAlipaySignExRepo(AlipaySignExRepoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    WxpaySignExRepo provideWxpaySignExRepo(WxpaySignExRepoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    RechargeListExRepo provideRechargeListExRepo(RechargeListExRepoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    RechargeInfoRepo provideRechargeInfoRepo(RechargeInfoRepoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    SearchServiceRepo provideSearchServiceRepo(SearchServiceRepoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    HotServiceInfoRepo provideHotServiceInfoRepo(HotServiceInfoRepoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    BusinessServicePriceRepo provideBusinessServicePriceRepo(BusinessServicePriceImpl impl){
        return  impl;
    }

    @Provides
    @Singleton
    BusinessServiceListRepo provideBusinessServiceListRepo(BusinessServiceListImpl impl){
        return  impl;
    }

    @Provides
    @Singleton
    MerchantServicePriceListRepo provideMerchantServicePriceListRepo(MerchantServicePriceListImpl impl){
        return impl;
    }


    @Provides
    @Singleton
    WorkerServicePriceListRepo provideWorkerServicePriceListRepo(WorkerServicePriceListImpl impl){
        return  impl;
    }

    @Provides
    @Singleton
    FirstOrderInfoRepo procideFirstOrderInfoRepo(FirstOrderInfoRepoImpl firstOrderInfoRepo){
        return firstOrderInfoRepo;
    }

    @Provides
    @Singleton
    CreateDirectPayExRepo provideCreateDirectPayExRepo(CreateDirectPayExImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    GetAtStoreActivityRepo provideGetAtStoreActivityRepo(GetAtStoreActivityImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    CheckIsReceivedRedCoupsRepo provideCheckIsReceivedRedCoupsRepo(CheckIsReceivedRedCoupsImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    VerifyTokenRepo provideVerifyTokenRepo(VerifyTokenRepoImpl impl) {
        return impl;
    }

    /**
     * 首页改版3.3.0
     */
    @Provides
    @Singleton
    ShortCutRepo provideShortCutRepo(ShortCutRepoImpl shortCutRepo){
        return shortCutRepo;
    }

    @Provides
    @Singleton
    AllServiceRepo provideAllServiceRepo(AllServiceRepoImpl allServiceRepo){
        return allServiceRepo;
    }

    @Provides
    @Singleton
    RecommendServiceRepo provideRecommendServiceRepo(RecommendServiceRepoImpl recommendServiceRepo){
        return recommendServiceRepo;
    }

    @Provides
    @Singleton
    QueryServiceRepo provideQueryServiceRepo(QueryServiceRepoImpl queryServiceRepo){
        return queryServiceRepo;
    }

    @Provides
    @Singleton
    HotSearchServiceRepo provideHotSearchServiceRepo(HotSearchServiceRepoImpl hotSearchServiceRepo){
        return hotSearchServiceRepo;
    }

    @Provides
    @Singleton
    SearchTopicRepo provideSearchTopicRepo(SearchTopicRepoImpl searchTopicRepo){
        return searchTopicRepo;
    }

    @Provides
    @Singleton
    QueryServicePriceRepo provideQueryServicePriceRepo(QueryServicePriceImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    ServiceTimeStartAtRepo provideServiceTimeStartAtRepo(ServiceTimeStartAtImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    PlaceOrderRepo providePlaceOrderRepo(PlaceOrderImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    GetActivityExRepo privodeGetActivityExRepo(GetActivityExImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    CommonCouponInfoRepo provideCommonCouponInfoRepo(CommonCouponInfoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    OrderCancelReasonRepo provideOrderCancelReasonRepo(OrderCancelReasonImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    QueryActivityServicePriceRepo provideQueryActivityServicePriceRepo(QueryActivityServicePriceImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    GetKdTrackInfoRepo provideGetKdTrackInfoRepo(GetKdTrackInfoImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    GetErrandServiceDetailRepo provideGetExpressDetailRepo(GetErrandServiceDetailImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    SubmitApplyClaimsRepo provideSubmitApplyClaimsRepo(SubmitApplyClaimsImpl impl){
        return impl;
    }

    /**
     * 3.5.0
     */
    @Provides
    @Singleton
    SuperDiscountRepo provideSuperDiscountRepo(SuperDiscountRepoImpl superDiscountRepo){
        return superDiscountRepo;
    }

    @Provides
    @Singleton
    CityRepo provideCityRepo(CityRepoImpl cityRepo){
        return cityRepo;
    }


    @Provides
    @Singleton
    TrackOrderStatusRepo provideTrackOrderStatusRepo(TrackOrderStatusImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    GetOrderListRepo provideGetOrderListRepo(GetOrderListImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    GetStarLevelRepo provideGetStarLevelRepo(GetStarLevelImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    SubmitEvaluationRepo provideSubmitEvaluationRepo(SubmitEvaluationImpl impl){
        return impl;
    }

    @Provides
    @Singleton
    FirstOpenAppInfoRepo provideFirstOpenAppInfoRepo(FirstOpenAppInfoImpl impl){
        return impl;
    }
}

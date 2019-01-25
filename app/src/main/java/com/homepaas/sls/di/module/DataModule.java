package com.homepaas.sls.di.module;

import android.support.annotation.Nullable;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.entity.AccountEntity;
import com.homepaas.sls.data.entity.BusinessEntity;
import com.homepaas.sls.data.entity.CallLogEntity;
import com.homepaas.sls.data.entity.CollectionConfig;
import com.homepaas.sls.data.entity.PersonalInfoEntity;
import com.homepaas.sls.data.entity.WorkerEntity;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.persistence.OrmLiteHelper;
import com.homepaas.sls.data.repository.datasource.AccountNDataSource;
import com.homepaas.sls.data.repository.datasource.AddRecommendInfoDataSource;
import com.homepaas.sls.data.repository.datasource.AddressNDataSource;
import com.homepaas.sls.data.repository.datasource.AdsInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.AvatarsNDataSource;
import com.homepaas.sls.data.repository.datasource.BannersInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.BusinessInfoMockDataSource;
import com.homepaas.sls.data.repository.datasource.BusinessInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.BusinessInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.BusinessTagsNDataSource;
import com.homepaas.sls.data.repository.datasource.CallLogNDataSource;
import com.homepaas.sls.data.repository.datasource.CallLogPDataSource;
import com.homepaas.sls.data.repository.datasource.CommentNDataSource;
import com.homepaas.sls.data.repository.datasource.CouponContentNDataSource;
import com.homepaas.sls.data.repository.datasource.DescriptionNDataSource;
import com.homepaas.sls.data.repository.datasource.DeviceInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.FavourNDataSource;
import com.homepaas.sls.data.repository.datasource.FirstCouponNDataSource;
import com.homepaas.sls.data.repository.datasource.FirstOrderInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.LifeServiceLDataSource;
import com.homepaas.sls.data.repository.datasource.LifeServiceMDataSource;
import com.homepaas.sls.data.repository.datasource.LifeServiceNDataSource;
import com.homepaas.sls.data.repository.datasource.MessageMDataSource;
import com.homepaas.sls.data.repository.datasource.MessageNDataSource;
import com.homepaas.sls.data.repository.datasource.OrderInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.OtherInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.PopuVerNDataSource;
import com.homepaas.sls.data.repository.datasource.PushNDataSource;
import com.homepaas.sls.data.repository.datasource.SearchNDataSource;
import com.homepaas.sls.data.repository.datasource.SearchServiceListNDataSource;
import com.homepaas.sls.data.repository.datasource.ServiceNDataSource;
import com.homepaas.sls.data.repository.datasource.ServiceSearchListMockDataSource;
import com.homepaas.sls.data.repository.datasource.ServiceTypeListNDataSource;
import com.homepaas.sls.data.repository.datasource.ShareNDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoMockDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerTagsNDataSource;
import com.homepaas.sls.data.repository.datasource.impl.AccountNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.AddRecommendInfoDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.AddressNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.AdsInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.AvatarsNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.BannersInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.BusinessInfoMockDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.BusinessInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.BusinessInfoPDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.BusinessTagsNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.CallLogNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.CallLogPDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.CommentNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.CouponContentNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.DescriptionNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.DeviceInfoPDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.FavourNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.FirstCouponNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.FirstOrderInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.LifeServiceLDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.LifeServiceMDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.LifeServiceNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.MessageMDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.MessageNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.OrderInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.OtherInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.PersonalInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.PersonalInfoPDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.PopuverNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.PushNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.SearchNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.SearchServiceListNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.ServiceNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.ServiceSearchListMockDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.ServiceTypeListNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.ShareNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.WorkerInfoMockDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.WorkerInfoNDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.WorkerInfoPDataSourceImpl;
import com.homepaas.sls.data.repository.datasource.impl.WorkerTagsNDataSourceImpl;
import com.homepaas.sls.data.validator.Validator;
import com.homepaas.sls.data.validator.ttl.TtlValidator;
import com.homepaas.sls.util.PlatformUtils;
import com.j256.ormlite.dao.Dao;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2015/12/24.
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    PersonalInfoNDataSource providePersonalInfoNDataSource(PersonalInfoNDataSourceImpl personalInfoNDataSource) {
        return personalInfoNDataSource;
    }

    @Provides
    @Singleton
    PersonalInfoPDataSource providePersonalInfoPDataSource(PersonalInfoPDataSourceImpl personalInfoPDataSource) {
        return personalInfoPDataSource;
    }

    @Provides
    @Singleton
    ServiceSearchListMockDataSource provideServiceSearchListMockDataSource(ServiceSearchListMockDataSourceImpl serviceSearchListMockDataSource) {
        return serviceSearchListMockDataSource;
    }

    @Provides
    @Singleton
    SearchServiceListNDataSource provideSearchServiceListNDataSource(SearchServiceListNDataSourceImpl searchServiceListNDataSource) {
        return searchServiceListNDataSource;
    }

    @Provides
    @Singleton
    BusinessInfoMockDataSource provideBusinessInfoMockDataSource(BusinessInfoMockDataSourceImpl businessInfoMockDataSource) {
        return businessInfoMockDataSource;
    }

    @Provides
    @Singleton
    BusinessInfoNDataSource provideBusinessInfoNDataSource(BusinessInfoNDataSourceImpl businessInfoNDataSource) {
        return businessInfoNDataSource;
    }

    @Provides
    @Singleton
    WorkerInfoMockDataSource provideWorkerInfoMockDataSource(WorkerInfoMockDataSourceImpl workerInfoMockDataSource) {
        return workerInfoMockDataSource;
    }

    @Provides
    @Singleton
    WorkerInfoNDataSource provideWorkerInfoNDataSource(WorkerInfoNDataSourceImpl workerInfoNDataSource) {
        return workerInfoNDataSource;
    }

    @Provides
    @Singleton
    LifeServiceMDataSource provideLifeServiceMDataSource(LifeServiceMDataSourceImpl lifeServiceMDataSource) {
        return lifeServiceMDataSource;
    }

    @Provides
    @Singleton
    LifeServiceNDataSource provideLifeServiceNDataSource(LifeServiceNDataSourceImpl lifeServiceNDataSource) {
        return lifeServiceNDataSource;
    }

    @Provides
    @Singleton
    LifeServiceLDataSource provideLifeServiceLDataSource(LifeServiceLDataSourceImpl lifeServiceLDataSource) {
        return lifeServiceLDataSource;
    }

    @Provides
    @Singleton
    MessageMDataSource provideMessageMDataSource(MessageMDataSourceImpl messageMDataSource) {
        return messageMDataSource;
    }

    @Provides
    @Singleton
    MessageNDataSource provideNDataSource(MessageNDataSourceImpl messageNDataSource) {
        return messageNDataSource;
    }

    @Provides
    @Singleton
    CallLogPDataSource provideCallLogPDataSource(CallLogPDataSourceImpl callLogPDataSource) {
        return callLogPDataSource;
    }

    @Provides
    @Singleton
    CallLogNDataSource provideCallLogNDataSource(CallLogNDataSourceImpl callLogNDataSource) {
        return callLogNDataSource;
    }

    @Provides
    @Singleton
    DeviceInfoPDataSource provideDeviceInfoPDataSource(DeviceInfoPDataSourceImpl deviceInfoPDataSource) {
        return deviceInfoPDataSource;
    }


    @Provides
    @Singleton
    WorkerInfoPDataSource provideWorkerInfoPDataSource(WorkerInfoPDataSourceImpl workerInfoPDataSource) {
        return workerInfoPDataSource;
    }

    @Provides
    @Singleton
    BusinessInfoPDataSource provideBusinessInfoPDataSource(BusinessInfoPDataSourceImpl businessInfoPDataSource) {
        return businessInfoPDataSource;
    }


    @Provides
    @Singleton
    CommentNDataSource provideCommentNDataSource(CommentNDataSourceImpl commentNDataSource){
        return commentNDataSource;
    }

    @Provides
    @Singleton
    AccountNDataSource provideAccountNDataSource(AccountNDataSourceImpl accountNDataSource){
        return accountNDataSource;
    }

    @Provides
    @Singleton
    ShareNDataSource provideShareNDataSource(ShareNDataSourceImpl shareNDataSource){
        return shareNDataSource;
    }

    @Provides
    @Singleton
    DescriptionNDataSource provideDescriptionNDataSource(DescriptionNDataSourceImpl descriptionndatasource){
        return descriptionndatasource;
    }

    /**
     * 服务类别列表
     *
     * @param serviceTypeListNDataSource
     * @return
     */
    @Provides
    @Singleton
    ServiceTypeListNDataSource provideServiceTypeListNDataSource(ServiceTypeListNDataSourceImpl serviceTypeListNDataSource) {
        return serviceTypeListNDataSource;
    }


    /**
     * 订单
     *
     * @param orderInfoNDataSource
     * @return
     */
    @Provides
    @Singleton
    OrderInfoNDataSource provideOrderInfoNDataSource(OrderInfoNDataSourceImpl orderInfoNDataSource) {
        return orderInfoNDataSource;
    }

    /**
     * 优惠活动（红包，优惠券等）
     *
     * @param favourNDataSource
     * @return
     */
    @Provides
    @Singleton
    FavourNDataSource provideFavourNDataSource(FavourNDataSourceImpl favourNDataSource) {
        return favourNDataSource;
    }

    /**
     * 红包数据持久化注入（利用多态，从CouponContentNDataSourceImpl得到CouponContentNDataSource实例）
     * @param couponContentNDataSource
     * @return
     */
    @Provides
    @Singleton
    CouponContentNDataSource provideCouponContentNDataSource(CouponContentNDataSourceImpl couponContentNDataSource) {
        return couponContentNDataSource;
    }

    @Provides
    @Singleton
    FirstCouponNDataSource provideFirstCouponNDataSource(FirstCouponNDataSourceImpl firstCouponNDataSource){return firstCouponNDataSource;}


    @Provides
    @Singleton
    OtherInfoNDataSource provideOtherInfoNDataSource(OtherInfoNDataSourceImpl otherInfoNDataSource){
        return otherInfoNDataSource;
    }


    @Provides
    @Singleton
    PushNDataSource providePushNDataSource(PushNDataSourceImpl pushNDataSource)
    {
        return pushNDataSource;
    }

    @Provides
    @Singleton
    SearchNDataSource provideSearchNDataSource(SearchNDataSourceImpl searchNDataSourceImpl){
        return searchNDataSourceImpl;
    }


    @Provides
    @Singleton
    Dao<PersonalInfoEntity, String> providePersonalInfoDao(OrmLiteHelper helper) {
        return helper.getPersonalInfoDao();
    }

    @Provides
    @Singleton
    Dao<CallLogEntity, Integer> provideCallLogDao(OrmLiteHelper helper) {
        return helper.getCallLogDao();
    }

    @Provides
    @Singleton
    Dao<BusinessEntity, String> provideBusinessDao(OrmLiteHelper helper) {
        return helper.getBusinessDao();
    }

    @Provides
    @Singleton
    Dao<WorkerEntity, String> provideWorkerDao(OrmLiteHelper helper) {
        return helper.getWorkerDao();
    }

    @Provides
    @Singleton
    Dao<CollectionConfig, Integer> provideCollectionConfigDao(OrmLiteHelper helper) {
        return helper.getCollectionConfigDao();
    }

    @Provides
    @Singleton
    Dao<BusinessEntity.SystemCertification, String> provideBusinessSystemCertificationsDao(OrmLiteHelper helper) {
        return helper.getBusinessSystemCertificationDao();
    }

    @Nullable
    @Provides
    @Singleton
    Dao<BusinessEntity.Photo, String> provideBusinessPhotoDao(OrmLiteHelper helper) {
        return helper.getBusinessPhotoDao();
    }

    @Provides
    @Singleton
    Dao<WorkerEntity.SystemCertification, String> provideWorkerSystemCertificationsDao(OrmLiteHelper helper) {
        return helper.getWorkerSystemCertificationDao();
    }

    @Provides
    @Singleton
    Dao<WorkerEntity.Photo, String> provideWorkerPhotoDao(OrmLiteHelper helper) {
        return helper.getWorkerPhotoDao();
    }

    @Provides
    @Singleton
    Dao<AccountEntity, String> provideAccountDao(OrmLiteHelper helper) {
        return helper.getAccountDao();
    }

    @Provides
    @Singleton
    Validator<PersonalInfoEntity> providePersonalInfoValidator() {
        if (!BuildConfig.DEBUG) {
            return new TtlValidator<>(7, TimeUnit.DAYS);
        } else {
            return new TtlValidator<>(1, TimeUnit.MINUTES);
        }
    }

    @Provides
    @Singleton
    Validator<WorkerEntity> provideWorkerEntityValidator() {
        return new TtlValidator<>(5, TimeUnit.MINUTES);
    }

    @Provides
    @Singleton
    Validator<BusinessEntity> provideBusinessEntityValidator() {
        return new TtlValidator<>(5, TimeUnit.MINUTES);
    }

    @Provides
    @Singleton
    Validator<AccountEntity> provideAccountEntityValidator() {
        return new TtlValidator<>(15, TimeUnit.DAYS);
    }

    @Provides
    @Singleton
    RestApiHelper provideResetApi(DeviceInfoPDataSource deviceInfo) {
        return new RestApiHelper(PlatformUtils.getChannelMarketId(SimpleTinkerInApplicationLike.getMainApplication()), deviceInfo);
    }

    @Provides
    @Singleton
    WorkerTagsNDataSource provideWorkerTagsNDataSource(WorkerTagsNDataSourceImpl workerTagsNDataSource){
        return workerTagsNDataSource;
    }

    @Provides
    @Singleton
    BusinessTagsNDataSource provideBusinessTagsNDataSource(BusinessTagsNDataSourceImpl businessTagsNDataSource){
        return businessTagsNDataSource;
    }

    @Provides
    @Singleton
    BannersInfoNDataSource provideBannersInfoNDataSource(BannersInfoNDataSourceImpl bannersInfoNDataSource){
        return bannersInfoNDataSource;
    }

    @Provides
    @Singleton
    AdsInfoNDataSource provideAdsInfoNDataSource(AdsInfoNDataSourceImpl adsInfoNDataSource){
        return adsInfoNDataSource;
    }
    @Provides
    @Singleton
    PopuVerNDataSource providerPopuVerNDataSource(PopuverNDataSourceImpl popuverNDataSource){
        return popuverNDataSource;
    }


    @Provides
    @Singleton
    AddressNDataSource provideAddressNDataSource(AddressNDataSourceImpl addressNDataSource){
        return addressNDataSource;
    }

    @Provides
    @Singleton
    ServiceNDataSource provideServiceNDataSource(ServiceNDataSourceImpl serviceNDataSource)
    {
        return serviceNDataSource;
    }

    @Provides
    @Singleton
    AvatarsNDataSource provideAvatarsNDataSource(AvatarsNDataSourceImpl avatarsNDataSource){
        return avatarsNDataSource;
    }

    @Provides
    @Singleton
    AddRecommendInfoDataSource provideAddRecommendInfoDataSource(AddRecommendInfoDataSourceImpl addRecommendInfoDataSource){
        return addRecommendInfoDataSource;
    }
    @Provides
    @Singleton
    FirstOrderInfoNDataSource provideFirstOrderInfoNDataSource(FirstOrderInfoNDataSourceImpl firstOrderInfoNDataSource){
        return firstOrderInfoNDataSource;
    }

}

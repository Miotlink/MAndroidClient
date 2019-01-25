package com.homepaas.sls.mvp.model.mapper;

import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.mvp.model.PersonalInfoModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

@Singleton
public class PersonalInfoModelMapper {

    @Inject
    public PersonalInfoModelMapper() {}

    public PersonalInfo transform(PersonalInfoModel model) {
        PersonalInfo personalInfo = null;
        if (model != null) {
            personalInfo = new PersonalInfo();
            personalInfo.setUserId(model.getUserId());
            personalInfo.setSmallPic(model.getSmallPic());
            personalInfo.setHqPic(model.getHqPic());
            personalInfo.setAccount(model.getAccount());
            personalInfo.setNickName(model.getNickName());
            personalInfo.setPhoneNumber(model.getPhoneNumber());
            personalInfo.setQrCode(model.getQrCode());
            personalInfo.setRecommendationUrl_Mine(model.getRecommendationUrl_Mine());
            personalInfo.setRecommendationUrl_Add(model.getRecommendationUrl_Add());
            personalInfo.setRecommendationUrl_Share(model.getRecommendationUrl_Share());
            personalInfo.setRecommendUserName(model.getRecommendUserName());
            personalInfo.setRecommendationCode(model.getRecommendationCode());
        }
        return personalInfo;
    }

    public PersonalInfoModel transformToModel(PersonalInfo personalInfo) {
        PersonalInfoModel model = null;
        if (personalInfo != null) {
            model = new PersonalInfoModel();
            model.setUserId(personalInfo.getUserId());
            model.setSmallPic(personalInfo.getSmallPic());
            model.setHqPic(personalInfo.getHqPic());
            model.setAccount(personalInfo.getAccount());
            model.setNickName(personalInfo.getNickName());
            model.setPhoneNumber(personalInfo.getPhoneNumber());
            model.setQrCode(personalInfo.getQrCode());
            model.setRecommendationUrl_Mine(personalInfo.getRecommendationUrl_Mine());
            model.setRecommendationUrl_Add(personalInfo.getRecommendationUrl_Add());
            model.setRecommendationUrl_Share(personalInfo.getRecommendationUrl_Share());
            model.setRecommendationCode(personalInfo.getRecommendationCode());
            model.setRecommendUserName(personalInfo.getRecommendUserName());
        }
        return model;
    }
}

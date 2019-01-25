package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.entity.PersonalInfoEntity;
import com.homepaas.sls.domain.entity.PersonalInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

@Singleton
public class PersonalInfoEntityMapper {

    @Inject
    public PersonalInfoEntityMapper() {}

    public PersonalInfo transform(PersonalInfoEntity entity) {
        PersonalInfo personalInfo = null;
        if (entity != null) {
            personalInfo = new PersonalInfo();
            personalInfo.setUserId(entity.getUserId());
            personalInfo.setUserId(entity.getUserId());
            personalInfo.setSmallPic(entity.getSmallPic());
            personalInfo.setHqPic(entity.getHqPic());
            personalInfo.setAccount(entity.getAccount());
            personalInfo.setNickName(entity.getNickName());
            personalInfo.setPhoneNumber(entity.getPhoneNumber());
            personalInfo.setQrCode(entity.getQrCode());
            personalInfo.setRecommendationUrl_Mine(entity.getRecommendationUrl_Mine());
            personalInfo.setRecommendationUrl_Add(entity.getRecommendationUrl_Add());
            personalInfo.setRecommendationUrl_Share(entity.getRecommendationUrl_Share());
            personalInfo.setRecommendUserName(entity.getRecommendUserName());
            personalInfo.setRecommendationCode(entity.getRecommendationCode());

        }
        return personalInfo;
    }

    public PersonalInfoEntity transformToEntity(PersonalInfo personalInfo) {
        PersonalInfoEntity entity = null;
        if (personalInfo != null) {
            entity = new PersonalInfoEntity();
            entity.setUserId(personalInfo.getUserId());
            entity.setUserId(personalInfo.getUserId());
            entity.setSmallPic(personalInfo.getSmallPic());
            entity.setHqPic(personalInfo.getHqPic());
            entity.setAccount(personalInfo.getAccount());
            entity.setNickName(personalInfo.getNickName());
            entity.setPhoneNumber(personalInfo.getPhoneNumber());
            entity.setQrCode(personalInfo.getQrCode());
            entity.setRecommendationUrl_Mine(personalInfo.getRecommendationUrl_Mine());
            entity.setRecommendationUrl_Add(personalInfo.getRecommendationUrl_Add());
            entity.setRecommendationUrl_Share(personalInfo.getRecommendationUrl_Share());
            entity.setRecommendUserName(personalInfo.getRecommendUserName());
            entity.setRecommendationCode(personalInfo.getRecommendationCode());
        }
        return entity;
    }
}

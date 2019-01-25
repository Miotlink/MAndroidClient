package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by CJJ on 2016/9/8.
 */
public interface IService {

    boolean isWorker();

    boolean isBusiness();


    double getLng();

    double getLat();
    //全城商户或者是全城工人均使用该方法判断
    boolean isWholeCityProvider();
//    String getDefaultService();//新版首頁改版已經去除defaultService

    //是不是商户工人
    boolean isCommonBusinessWorker();

    String getId();

    /**
     * 獲取正常情況下的圖標
     * @return
     */
    String getIcon();

    /**
     * 获取选中状态下的图标
     * @return
     */
    String getIconChecked();

    ServiceType getDefService();
    String getPhotoUrl();
    int  getServiceType();
    List<String> getServiceList();


}

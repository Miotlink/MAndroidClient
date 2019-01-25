package com.homepaas.sls.mvp.model;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.ui.order.adapter.Converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.homepaas.sls.ui.order.adapter.Converter.TYPE_INVALID;

/**
 * Created by CJJ on 2016/9/17.
 */
@ActivityScope
public class PlaceOrderParamModel {

    int mode = TYPE_INVALID;
    public AddressEntity address;
    public String avatar;
    public String providerName;
    public String providerType;
    public String serviceName;
    public String providerId;
    public String serviceTypeId;
    public String serviceContent;
    public String startingPrice;
    public int serviceNumber = 1;
    private String orderSource = Constant.ANDROID_TYPE;
    public String serviceTimeStart;
    public String serviceAddress;
    public List<String> photoPath;
    public String getProviderPic;
    private String price;
    public ActivityNgInfoNew currentAction;
    public double sum;
    public double minSum;
    public double maxSum;
    public float serviceCount = 0.0f;//针对服务数量不是整数的情况
    private String unit;
    private String note;
    private String phoneNumber;
    private String providerGender;
    private String serviceNumberString;//保存原始的服务数量

    @Inject
    public PlaceOrderParamModel() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getServiceNumber() {
        return String.valueOf(serviceNumber);
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = Integer.valueOf(serviceNumber);
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getServiceTimeStart() {
        return serviceTimeStart;
    }

    public void setServiceTimeStart(String serviceTimeStart) {
        this.serviceTimeStart = serviceTimeStart;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public List<String> getPhotoPath() {
        if (photoPath == null)
            photoPath = new ArrayList<>();
        return photoPath;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public String getServiceNumberString() {
        return serviceNumberString;
    }

    public void setServiceNumberString(String serviceNumberString) {
        this.serviceNumberString = serviceNumberString;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public void setPhotoPath(List<String> photoPath) {
        this.photoPath = photoPath;
    }

    public String getGetProviderPic() {
        return getProviderPic;
    }

    public void setGetProviderPic(String getProviderPic) {
        this.getProviderPic = getProviderPic;
    }

    public int plusServiceNumber() {
        serviceNumber++;
        setServiceNumberString(String.valueOf(serviceNumber));
        return serviceNumber;
    }

    public int minusServiceNumber() {

        int r = serviceNumber > 1 ? --serviceNumber : serviceNumber;
        setServiceNumberString(String.valueOf(r));
        return r;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCurrentAction(ActivityNgInfoNew currentAction) {
        this.currentAction = currentAction;
    }

    @Converter.PriceType
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setServiceNumber(int serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public ActivityNgInfoNew getCurrentAction() {
        return currentAction;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getMinSum() {
        return minSum;
    }

    public void setMinSum(double minSum) {
        this.minSum = minSum;
    }

    public double getMaxSum() {
        return maxSum;
    }

    public void setMaxSum(double maxSum) {
        this.maxSum = maxSum;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProviderGender() {
        return providerGender;
    }

    public void setProviderGender(String providerGender) {
        this.providerGender = providerGender;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }
}

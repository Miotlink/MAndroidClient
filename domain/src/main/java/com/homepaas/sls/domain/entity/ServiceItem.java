package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sherily on 2017/3/22.
 */

public class ServiceItem implements Serializable {

    private static final long serialVersionUID = -3673727146544087171L;
    @SerializedName("Description")
    private String subtitle;
    @SerializedName("IconUrl")
    private String iconUrl;
    @SerializedName("LogoUrl")
    private List<String> logoUrl;
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("ServiceName")
    private String serviceName;
    @SerializedName("Price")
    private String price;
    @SerializedName("Unit")
    private String unit;
    //是否是特殊类型（保姆，月嫂，看护类等等），0 不是，1是;default:0
    @SerializedName("SpecialType")
    private String specialType;
    //详情页地址
    @SerializedName("DetailUrl")
    private String detailUrl;
    /**
     * SellType String	是出售类型字段,
     * SERVICE_PRICE_SPECIFIC:定价类型(包含定价面议混合类型) 1;
     * SERVICE_PRICE_UNDEFINED:面议类型 0;
     * SERVICE_SIMPLY:简单类型
     * default: SERVICE_PRICE_SPECIFIC 1
     */
    @SerializedName("SellType")
    private String sellType;
    /**
     * StructureType String是结构类型字段,
     * SERVICE_GROUP:组类型,对应二级;0
     * SERVICE_PRODUCT:商品类型,对应三级;1
     * SERVICE_KIND:种类类型,对应四级2
     * default: SERVICE_GROUP;0
     */
    @SerializedName("StructureType")
    private String structureType;

    @SerializedName("MaxCount")
    private String maxCount;
    @SerializedName("MinCount")
    private String minCount;
    /**
     * 每次递增最小单位, 默认1, 注:小时工从以前的提供列表方式改成这种形式(配合最小最大限制)
     */
    @SerializedName("CountStep")
    private String countStep;
    /**
     * 是否可以人工输入,默认否, 注:有些类型比如录入面积可能需要提供这种操作方式,1可输，0不能
     */
    @SerializedName("MaunalInput")
    private String maunlInput;
    @SerializedName("SubItems")
    private List<ServiceItem> subItems;
    //是否有保险选项  0：没有 1：有
    @SerializedName("IsEnableClaims")
    private String isEnableClaims;
    //保险须知
    @SerializedName("ClaimsNote")
    private ClaimsNote claimsNote;
    //最高充值金额
    @SerializedName("MaxRechargeAmount")
    private String maxRechargeAmount;
    //最高充值的优惠金额
    @SerializedName("MaxRechargeOffAmount")
    private String maxRechargeOffAmount;
    //面议类，订金金额 只有SellType=0并且StructureType=2时，才不为空,
    @SerializedName("DepositAmount")
    private String depositAmount;
    //订金单位
    @SerializedName("DepositUnit")
    private String depositUnit;
    //是否显示订金金额,0：不显示，1：显示，默认不显示,
    @SerializedName("IsShowDeposit")
    private String isShowDeposit;
    // 订单类型，表示需要提交的是超级星期五的订单还是正常的订单 0：正常，1：超级星期五；默认是正常
    @SerializedName("OrderType")
    private String orderType;
    @SerializedName("SuperDiscountItem")
    private SuperDiscountItem superDiscountItem;
    //默认数量
    @SerializedName("DefaultCount")
    private String defaultCount;

    //特殊服务主要需求url
    @SerializedName("SpecialTypeUrl")
    private String specialTypeUrl;
    @SerializedName("IsFlagMerchantService")
    private  String isFlagMerchantService;//标记详情页是否出现商家的tab页面 0：不出现 1:出现

    /// <summary>
    /// 多个区间单价。
    /// 如果集合数量为0或者集合为空，则表示没有区间单价，直接使用默认的服务单价
    /// </summary>
    public List<RangePrice> RangePrices;

    /// <summary>
    /// 区间单价，是指：
    /// 用户选择的服务数量，在[1,3]之间，所对应的服务单价。
    /// ***注意：在下单页面计算价格时，优先使用区间单价。***
    /// </summary>
    public class RangePrice {
        /// <summary>
        /// 服务数量区间的最小值
        /// </summary>
        public double MinCount;
        /// <summary>
        /// 服务数量区间的最大值
        /// </summary>
        public double MaxCount;
        /// <summary>
        /// 区间所对应的服务单价
        /// </summary>
        public String Price;

        public double getMinCount() {
            return MinCount;
        }

        public void setMinCount(double minCount) {
            MinCount = minCount;
        }

        public double getMaxCount() {
            return MaxCount;
        }

        public void setMaxCount(double maxCount) {
            MaxCount = maxCount;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }
    }

    public List<RangePrice> getRangePrices() {
        return RangePrices;
    }

    public void setRangePrices(List<RangePrice> rangePrices) {
        RangePrices = rangePrices;
    }

    public String getSpecialTypeUrl() {
        return specialTypeUrl;
    }

    public void setSpecialTypeUrl(String specialTypeUrl) {
        this.specialTypeUrl = specialTypeUrl;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public String getIsFlagMerchantService() {
        return isFlagMerchantService;
    }

    public void setIsFlagMerchantService(String isFlagMerchantService) {
        this.isFlagMerchantService = isFlagMerchantService;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDepositUnit() {
        return depositUnit;
    }

    public void setDepositUnit(String depositUnit) {
        this.depositUnit = depositUnit;
    }

    public String getIsShowDeposit() {
        return isShowDeposit;
    }

    public void setIsShowDeposit(String isShowDeposit) {
        this.isShowDeposit = isShowDeposit;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public SuperDiscountItem getSuperDiscountItem() {
        return superDiscountItem;
    }

    public void setSuperDiscountItem(SuperDiscountItem superDiscountItem) {
        this.superDiscountItem = superDiscountItem;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(List<String> logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public String getMinCount() {
        return minCount;
    }

    public void setMinCount(String minCount) {
        this.minCount = minCount;
    }

    public String getCountStep() {
        return countStep;
    }

    public void setCountStep(String countStep) {
        this.countStep = countStep;
    }

    public String getMaunlInput() {
        return maunlInput;
    }

    public void setMaunlInput(String maunlInput) {
        this.maunlInput = maunlInput;
    }

    public List<ServiceItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<ServiceItem> subItems) {
        this.subItems = subItems;
    }

    public String getIsEnableClaims() {
        return isEnableClaims;
    }

    public void setIsEnableClaims(String isEnableClaims) {
        this.isEnableClaims = isEnableClaims;
    }

    public ClaimsNote getClaimsNote() {
        return claimsNote;
    }

    public void setClaimsNote(ClaimsNote claimsNote) {
        this.claimsNote = claimsNote;
    }

    public String getMaxRechargeAmount() {
        return maxRechargeAmount;
    }

    public void setMaxRechargeAmount(String maxRechargeAmount) {
        this.maxRechargeAmount = maxRechargeAmount;
    }

    public String getMaxRechargeOffAmount() {
        return maxRechargeOffAmount;
    }

    public void setMaxRechargeOffAmount(String maxRechargeOffAmount) {
        this.maxRechargeOffAmount = maxRechargeOffAmount;
    }

    public String getDefaultCount() {
        return defaultCount;
    }

    public void setDefaultCount(String defaultCount) {
        this.defaultCount = defaultCount;
    }

    public class ClaimsNote implements Serializable {
        //标题
        @SerializedName("Title")
        private String title;
        //内容
        @SerializedName("Content")
        private String content;
        //保险协议URL地址
        @SerializedName("Url")
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class SuperDiscountItem implements Serializable {
        @SerializedName("Amount")
        private String amount;
        @SerializedName("DiscountContent")
        private List<String> discountContent;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public List<String> getDiscountContent() {
            return discountContent;
        }

        public void setDiscountContent(List<String> discountContent) {
            this.discountContent = discountContent;
        }
    }
}

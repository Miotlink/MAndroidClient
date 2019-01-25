package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2017/7/5.
 * 保险赔付信息
 */

public class ClaimsInfo implements Serializable{
    //一元保险金额
    @SerializedName("ClaimsAmount")
    private String claimsAmount;
    //是否显示申请保险赔付的按钮 0：不显示 1：显示
    @SerializedName("IsDisplayApplyClaimsBtn")
    private String isDisplayApplyClaimsBtn;
    //是否显示查看保险赔付的按钮 0：不显示 1：显示
    @SerializedName("IsDisplayShowClaimsBtn")
    private String isDisplayShowClaimsBtn;
    //是否显示查看保险赔付信息不通过原因的按钮 0：不显示 1：显示
    @SerializedName("IsDisplayBackClaimsReasonBtn")
    private String isDisplayBackClaimsReasonBtn;
    //保险赔付信息
    @SerializedName("ClaimsManagementInfo")
    private ClaimsManagementInfo claimsManagementInfo;

    public String getClaimsAmount() {
        return claimsAmount;
    }

    public void setClaimsAmount(String claimsAmount) {
        this.claimsAmount = claimsAmount;
    }

    public String getIsDisplayApplyClaimsBtn() {
        return isDisplayApplyClaimsBtn;
    }

    public void setIsDisplayApplyClaimsBtn(String isDisplayApplyClaimsBtn) {
        this.isDisplayApplyClaimsBtn = isDisplayApplyClaimsBtn;
    }

    public String getIsDisplayShowClaimsBtn() {
        return isDisplayShowClaimsBtn;
    }

    public void setIsDisplayShowClaimsBtn(String isDisplayShowClaimsBtn) {
        this.isDisplayShowClaimsBtn = isDisplayShowClaimsBtn;
    }

    public String getIsDisplayBackClaimsReasonBtn() {
        return isDisplayBackClaimsReasonBtn;
    }

    public void setIsDisplayBackClaimsReasonBtn(String isDisplayBackClaimsReasonBtn) {
        this.isDisplayBackClaimsReasonBtn = isDisplayBackClaimsReasonBtn;
    }

    public ClaimsManagementInfo getClaimsManagementInfo() {
        return claimsManagementInfo;
    }

    public void setClaimsManagementInfo(ClaimsManagementInfo claimsManagementInfo) {
        this.claimsManagementInfo = claimsManagementInfo;
    }

    public class ClaimsManagementInfo implements Serializable{
        //计算明细Id
        @SerializedName("SettlementId")
        private String settlementId;
        //状态 审核中 已赔付 审核不通过
        @SerializedName("AuditStatus")
        private String auditStatus;
        //驳回原因
        @SerializedName("BackReason")
        private String backReason;
        //赔付原因的类型
        @SerializedName("ClaimsReasons")
        private List<ClaimsManagementTypeInfo> claimsManagementTypeInfo;

        public String getSettlementId() {
            return settlementId;
        }

        public void setSettlementId(String settlementId) {
            this.settlementId = settlementId;
        }

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getBackReason() {
            return backReason;
        }

        public void setBackReason(String backReason) {
            this.backReason = backReason;
        }

        public List<ClaimsManagementTypeInfo> getClaimsManagementTypeInfo() {
            return claimsManagementTypeInfo;
        }

        public void setClaimsManagementTypeInfo(List<ClaimsManagementTypeInfo> claimsManagementTypeInfo) {
            this.claimsManagementTypeInfo = claimsManagementTypeInfo;
        }

        public class ClaimsManagementTypeInfo implements Serializable{
            @SerializedName("Id")
            private String id;
            @SerializedName("Name")
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}

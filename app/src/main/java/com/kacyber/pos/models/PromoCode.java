package com.kacyber.pos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCode {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("discountCouponSingle")
    @Expose
    public DiscountCouponSingle discountCouponSingle;
    @SerializedName("hasCouponForSingle")
    @Expose
    public String hasCouponForSingle;

    public class DiscountCouponSingle {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("createdTime")
        @Expose
        public String createdTime;
        @SerializedName("validUpto")
        @Expose
        public String validUpto;
        @SerializedName("noOfUsers")
        @Expose
        public Integer noOfUsers;
        @SerializedName("amount")
        @Expose
        public Integer amount;
        @SerializedName("status")
        @Expose
        public Integer status;

    }
}

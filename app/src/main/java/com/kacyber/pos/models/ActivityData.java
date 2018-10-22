package com.kacyber.pos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityData {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("totalCount")
    @Expose
    public Integer totalCount;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> dataList = null;

    public class Datum {

        @SerializedName("pin")
        @Expose
        public String pin;
        @SerializedName("boardingPoint_id")
        @Expose
        public Integer boardingPointId;
        @SerializedName("paymentFrom")
        @Expose
        public Integer paymentFrom;
        @SerializedName("totalCommissionCharges")
        @Expose
        public Float totalCommissionCharges;
        @SerializedName("totalTicketsCharges")
        @Expose
        public Float totalTicketsCharges;
        @SerializedName("sourceCity")
        @Expose
        public String sourceCity;
        @SerializedName("issuedReceiptNumber")
        @Expose
        public String issuedReceiptNumber;
        @SerializedName("bookingStatus_id")
        @Expose
        public Integer bookingStatusId;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("bookedByName")
        @Expose
        public String bookedByName;
        @SerializedName("transactionReferenceId")
        @Expose
        public String transactionReferenceId;
        @SerializedName("qrCode")
        @Expose
        public String qrCode;
        @SerializedName("transactionReferenceForTransfer")
        @Expose
        public String transactionReferenceForTransfer;
        @SerializedName("issuedReceiptNumberForTransfer")
        @Expose
        public String issuedReceiptNumberForTransfer;
        @SerializedName("bookingOfDate")
        @Expose
        public String bookingOfDate;
        @SerializedName("busFleet_id")
        @Expose
        public Integer busFleetId;
        @SerializedName("lastUpdated")
        @Expose
        public String lastUpdated;
        @SerializedName("printingStatus")
        @Expose
        public Integer printingStatus;
        @SerializedName("startTime")
        @Expose
        public String startTime;
        @SerializedName("paypalTransactionId")
        @Expose
        public String paypalTransactionId;
        @SerializedName("totalFair")
        @Expose
        public Float totalFair;
        @SerializedName("commissionPercent")
        @Expose
        public Float commissionPercent;
        @SerializedName("africellTransactionId")
        @Expose
        public Object africellTransactionId;
        @SerializedName("bookingTime")
        @Expose
        public String bookingTime;
        @SerializedName("bookedBy_id")
        @Expose
        public Integer bookedById;
        @SerializedName("eTicket")
        @Expose
        public String eTicket;
        @SerializedName("transactionReference")
        @Expose
        public String transactionReference;
        @SerializedName("firstPassangerName")
        @Expose
        public String firstPassangerName;
        @SerializedName("destinationCity")
        @Expose
        public String destinationCity;
        @SerializedName("busCompanyName")
        @Expose
        public String busCompanyName;
        @SerializedName("busRoute")
        @Expose
        public String busRoute;
        @SerializedName("bookingFrom")
        @Expose
        public String bookingFrom;
        @SerializedName("droppingPoint_id")
        @Expose
        public Integer droppingPointId;
        @SerializedName("timeZone")
        @Expose
        public String timeZone;
        @SerializedName("transactionReferenceIdForTransfer")
        @Expose
        public String transactionReferenceIdForTransfer;
        @SerializedName("passangerPhone")
        @Expose
        public String passangerPhone;

    }
}

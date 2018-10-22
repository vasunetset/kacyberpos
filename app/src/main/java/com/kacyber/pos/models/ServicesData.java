package com.kacyber.pos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServicesData implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("list")
    @Expose
    public List<ListData> list = null;

    public class ListData implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("endTime")
        @Expose
        public String endTime;
        @SerializedName("destinationLocation")
        @Expose
        public String destinationLocation;
        @SerializedName("destinationLocationLatitude")
        @Expose
        public String destinationLocationLatitude;
        @SerializedName("destinationLocationLongitude")
        @Expose
        public String destinationLocationLongitude;
        @SerializedName("sourceLocation")
        @Expose
        public String sourceLocation;
        @SerializedName("sourceLocationLatitude")
        @Expose
        public String sourceLocationLatitude;
        @SerializedName("sourceLocationLongitude")
        @Expose
        public String sourceLocationLongitude;
        @SerializedName("startTime")
        @Expose
        public String startTime;
        @SerializedName("ticketPricePerBusinessSeat")
        @Expose
        public Float ticketPricePerBusinessSeat;
        @SerializedName("ticketPricePerEconomySeat")
        @Expose
        public Float ticketPricePerEconomySeat;
        @SerializedName("ticketPricePerBusinessSeatForAgent")
        @Expose
        public Float ticketPricePerBusinessSeatForAgent;
        @SerializedName("ticketPricePerEconomySeatForAgent")
        @Expose
        public Float ticketPricePerEconomySeatForAgent;
        @SerializedName("totalBusinessSeats")
        @Expose
        public Integer totalBusinessSeats;
        @SerializedName("totalEconomySeats")
        @Expose
        public Integer totalEconomySeats;
        @SerializedName("busCompany")
        @Expose
        public Integer busCompany;
        @SerializedName("busType")
        @Expose
        public Integer busType;
        @SerializedName("sourceCity")
        @Expose
        public String sourceCity;
        @SerializedName("destinationCity")
        @Expose
        public String destinationCity;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("busNumber")
        @Expose
        public String busNumber;
        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("timeZone")
        @Expose
        public String timeZone;
        @SerializedName("busRoute")
        @Expose
        public String busRoute;
        @SerializedName("lastUpdatedDate")
        @Expose
        public String lastUpdatedDate;
        @SerializedName("lastUpdatedBy")
        @Expose
        public Integer lastUpdatedBy;

    }
}

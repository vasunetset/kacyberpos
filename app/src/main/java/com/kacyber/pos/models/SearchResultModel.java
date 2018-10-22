package com.kacyber.pos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by netset on 27/3/18.
 */


public class SearchResultModel implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("list")
    @Expose
    public List<SearchResultBusList> list = new ArrayList<>();


    public class SearchResultBusList implements Serializable {

        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("sourceLocationLongitude")
        @Expose
        public String sourceLocationLongitude;
        @SerializedName("destinationLocationLongitude")
        @Expose
        public String destinationLocationLongitude;
        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("busCompanyLogo")
        @Expose
        public String busCompanyLogo;
        @SerializedName("duration")
        @Expose
        public String duration;
        @SerializedName("sourceCity")
        @Expose
        public String sourceCity;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("totalSeats")
        @Expose
        public Integer totalSeats;
        @SerializedName("destinationLocationLatitude")
        @Expose
        public String destinationLocationLatitude;
        @SerializedName("ticketPricePerEconomySeat")
        @Expose
        public Float ticketPricePerEconomySeat;
        @SerializedName("busType")
        @Expose
        public Integer busType;
        @SerializedName("totalBusinessSeats")
        @Expose
        public Integer totalBusinessSeats;
        @SerializedName("busOperator")
        @Expose
        public Integer busOperator;
        @SerializedName("busNumber")
        @Expose
        public String busNumber;
        @SerializedName("duration_in_milli_sec")
        @Expose
        public Integer durationInMilliSec;
        @SerializedName("price")
        @Expose
        public Float price;
        @SerializedName("startTime")
        @Expose
        public String startTime;
        @SerializedName("totalEconomySeats")
        @Expose
        public Integer totalEconomySeats;
        @SerializedName("sourceLocation")
        @Expose
        public String sourceLocation;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("destinationCity")
        @Expose
        public String destinationCity;
        @SerializedName("destinationLocation")
        @Expose
        public String destinationLocation;
        @SerializedName("sourceLocationLatitude")
        @Expose
        public String sourceLocationLatitude;
        @SerializedName("timeZone")
        @Expose
        public String timeZone;
        @SerializedName("endTime")
        @Expose
        public String endTime;
        @SerializedName("ticketPricePerBusinessSeat")
        @Expose
        public Float ticketPricePerBusinessSeat;
        @SerializedName("started")
        @Expose
        public String started;
        @SerializedName("busRoute")
        @Expose
        public String busRoute;
        @SerializedName("freeSeats")
        @Expose
        public String freeSeats;
    }
}



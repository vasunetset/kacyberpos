package com.kacyber.pos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by netset on 27/3/18.
 */
public class BusDetails {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("boardingPoints")
    @Expose
    public List<BoardingPoint> boardingPoints = null;
    @SerializedName("amenities")
    @Expose
    public List<Amenity> amenities = null;
    @SerializedName("dropingPoints")
    @Expose
    public List<DropingPoint> dropingPoints = null;
    @SerializedName("Terms and Policies")
    @Expose
    public List<TermsAndPolicy> termsAndPolicies = null;
    @SerializedName("Bus Details")
    @Expose
    public BusDetails_ busDetails;
    @SerializedName("message")
    @Expose
    public String message;

    public class BoardingPoint {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("location")
        @Expose
        public String location;

    }

    public class Amenity {

        @SerializedName("amenity")
        @Expose
        public String amenity;
        @SerializedName("id")
        @Expose
        public Integer id;

    }




    public class BusDetails_ {

        @SerializedName("busNumber")
        @Expose
        public String busNumber;
        @SerializedName("totalBusinessSeats")
        @Expose
        public Integer totalBusinessSeats;
        @SerializedName("destination Location Longitude")
        @Expose
        public String destinationLocationLongitude;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("source Location")
        @Expose
        public String sourceLocation;
        @SerializedName("Destination Location")
        @Expose
        public String destinationLocation;
        @SerializedName("destinationCity")
        @Expose
        public String destinationCity;
        @SerializedName("Destination Location Latitude")
        @Expose
        public String destinationLocationLatitude;
        @SerializedName("start time")
        @Expose
        public String startTime;
        @SerializedName("busType")
        @Expose
        public Integer busType;
        @SerializedName("source Location Latitude")
        @Expose
        public String sourceLocationLatitude;
        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("end Time")
        @Expose
        public String endTime;
        @SerializedName("ticketPricePerEconomySeat")
        @Expose
        public Float ticketPricePerEconomySeat;
        @SerializedName("source Location Longitude")
        @Expose
        public String sourceLocationLongitude;
        @SerializedName("duration")
        @Expose
        public String duration;
        @SerializedName("totalEconomySeats")
        @Expose
        public Integer totalEconomySeats;
        @SerializedName("sourceCity")
        @Expose
        public String sourceCity;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("busOperator")
        @Expose
        public Integer busOperator;
        @SerializedName("ticketPricePerBusinessSeat")
        @Expose
        public Float ticketPricePerBusinessSeat;
        @SerializedName("busRoute")
        @Expose
        public String busRoute;

    }
    public class DropingPoint {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("location")
        @Expose
        public String location;

    }

    public class TermsAndPolicy {

        @SerializedName("Description")
        @Expose
        public String description;
        @SerializedName("Key")
        @Expose
        public String key;

    }
}
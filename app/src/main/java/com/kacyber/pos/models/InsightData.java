package com.kacyber.pos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsightData {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Datum data;

    public class Datum {
        @SerializedName("cancelledSeats")
        @Expose
        public Integer cancelledSeats;
        @SerializedName("cancellationAmount")
        @Expose
        public String cancellationAmount;
        @SerializedName("totalTicketsIssued")
        @Expose
        public Integer totalTicketsIssued;
        @SerializedName("seatsBooked")
        @Expose
        public Integer seatsBooked;
        @SerializedName("ticketsVerified")
        @Expose
        public Integer ticketsVerified;
        @SerializedName("amountCollected")
        @Expose
        public String amountCollected;
        @SerializedName("ticketsPrinted")
        @Expose
        public Integer ticketsPrinted;


    }
}

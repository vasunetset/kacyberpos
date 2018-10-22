package com.kacyber.pos.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 车票信息
 * Created by mzy on 2017/9/29.
 */
public class Ticket {
    @SerializedName("fromto")
    public String fromTo;
    @SerializedName("price")
    public String price;
    @SerializedName("name")
    public String name;
    @SerializedName("time")
    public String time;
    @SerializedName("passengername")
    public String passengerName;
    @SerializedName("documentId")
    public String documentId;
}

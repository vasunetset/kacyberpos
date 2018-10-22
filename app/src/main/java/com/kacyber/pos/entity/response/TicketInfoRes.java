package com.kacyber.pos.entity.response;

import com.google.gson.annotations.SerializedName;

import com.kacyber.pos.entity.Ticket;

/**
 * Created by mzy on 2018/3/19.
 */

public class TicketInfoRes extends BaseRes {
    @SerializedName("data")
    public Ticket ticket;
}

package com.kacyber.pos.entity.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mzy on 2018/3/17.
 */

public class BaseRes {
    @SerializedName(value = "success", alternate = "status")
    public int success;
}

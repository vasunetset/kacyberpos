package com.kacyber.pos.entity.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mzy on 2017/6/9.
 */

public class LoginInfoRes extends BaseRes {
    @SerializedName("token")
    public String token;
}

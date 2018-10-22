package com.kacyber.pos.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mzy on 2018/3/17.
 */

public class LoginReq extends BaseReq {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;

    public LoginReq(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

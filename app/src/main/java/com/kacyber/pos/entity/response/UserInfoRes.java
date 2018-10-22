package com.kacyber.pos.entity.response;

import com.google.gson.annotations.SerializedName;

import com.kacyber.pos.entity.User;

/**
 * 网络返回的用户信息
 * Created by mzy on 2018/3/17.
 */

public class UserInfoRes extends BaseRes {
    @SerializedName("data")
    public User user;
}

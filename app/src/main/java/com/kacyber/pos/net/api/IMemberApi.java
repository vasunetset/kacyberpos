package com.kacyber.pos.net.api;

import com.kacyber.pos.entity.response.UserInfoRes;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by mzy on 2018/3/17.
 */

public interface IMemberApi {

    /**
     * 用户信息，需要token
     * @return
     */
    @GET("members")
    Observable<UserInfoRes> userInfo();
}

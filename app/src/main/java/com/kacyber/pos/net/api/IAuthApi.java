package com.kacyber.pos.net.api;

import com.kacyber.pos.entity.request.LoginReq;
import com.kacyber.pos.entity.response.LoginInfoRes;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by mzy on 2018/3/17.
 */

public interface IAuthApi {

    /**
     * 登录，不需要token
     * @param req
     * @return
     */
    @POST("loginAdminUser")
    Observable<LoginInfoRes> login(@Body LoginReq req);
}

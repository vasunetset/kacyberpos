package com.kacyber.pos.net.api;

import com.kacyber.pos.entity.response.TicketInfoRes;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by mzy on 2018/3/19.
 */

public interface ITicketApi {

    /**
     * 用户信息，需要token
     * @return
     */
    @GET("ticket")
    Observable<TicketInfoRes> ticket();
}

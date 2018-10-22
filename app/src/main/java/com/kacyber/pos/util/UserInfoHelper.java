package com.kacyber.pos.util;

import com.kacyber.pos.App;
import com.kacyber.pos.entity.response.UserInfoRes;
import com.kacyber.pos.net.RetrofitService;
import com.kacyber.pos.net.SimpleSubscriber;

/**
 * 用户信息修改帮助类
 * Created by mzy on 2017/6/20.
 */

public class UserInfoHelper {

    public interface OnGetUserInfoListener {
        void onSuccess();
        void onFailed();
    }

    /**
     * 获取用户信息
     * @param listener
     */
    public static void getUserInfo(final OnGetUserInfoListener listener) {
        RetrofitService.userInfo().subscribe(new SimpleSubscriber<UserInfoRes>() {
            @Override
            public void onSuccess(UserInfoRes userInfoRes) {
                GlobalStore.saveUser(App.getInstance(), userInfoRes.user);
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailed(Throwable e) {
                if (listener != null) {
                    listener.onFailed();
                }
            }
        });
    }
}

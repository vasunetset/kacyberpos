package com.kacyber.pos.util;

import android.content.Context;
import android.support.annotation.Nullable;

import com.kacyber.pos.entity.User;
import com.kacyber.pos.entity.response.LoginInfoRes;
import com.kacyber.pos.util.common.GsonUtils;
import com.kacyber.pos.util.common.LogUtils;
import com.kacyber.pos.util.common.PreferencesUtils;

/**
 * Created by mzy on 2017/6/16.
 */

public class GlobalStore {

    private static final String TAG = GlobalStore.class.getSimpleName();

    private class Constant {
        public static final String KEY_INTRO_VIEW_ALREADY_STARTED_ONCE = "intro_view_already_started_once";
        public static final String KEY_ACCOUNT = "account";
        public static final String KEY_USER_ROOL = "role";
        public static final String USER_TOKEN = "user_token";
        public static final String LOGIN_BOOL = "login_bool";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_LOGIN_INFO = "login_info";
        public static final String KEY_USER = "user";
    }

    /**
     * 启动过一次后调用保存
     *
     * @param context
     */
    public static void saveAlreadyStartedOnce(Context context) {
        PreferencesUtils.putBoolean(context, Constant.KEY_INTRO_VIEW_ALREADY_STARTED_ONCE, true);
    }

    /**
     * 是否已经启动过一次
     *
     * @param context
     * @return
     */
    public static boolean isAlreadyStartedOnce(Context context) {
        boolean isAlreadyStartedOnce = PreferencesUtils.getBoolean(context, Constant.KEY_INTRO_VIEW_ALREADY_STARTED_ONCE, false);
        LogUtils.d(TAG, isAlreadyStartedOnce ? "不是第一次启动" : "第一次启动");
        return isAlreadyStartedOnce;
    }

    /**
     * 保存登录账号数据
     *
     * @param context
     * @param account
     */
    public static void saveAccount(Context context, String account) {
        PreferencesUtils.putString(context, Constant.KEY_ACCOUNT, account);
    }

    /**
     * 读取登录账号数据
     *
     * @param context
     * @return
     */
    public static @Nullable
    int getUserRoll(Context context) {
        int account = PreferencesUtils.getInt(context, Constant.KEY_USER_ROOL);
        return account;
    }

    public static void saveUserRoll(Context context, int roleID) {
        PreferencesUtils.putInt(context, Constant.KEY_USER_ROOL, roleID);
    }

    /**
     * 读取登录账号数据
     *
     * @param context
     * @return
     */
    public static @Nullable
    String getAccount(Context context) {
        String account = PreferencesUtils.getString(context, Constant.KEY_ACCOUNT);
        return account;
    }


    /**
     * 保存登录账号数据
     *
     * @param context
     * @param token
     */
    public static void saveToken(Context context, String token) {
        PreferencesUtils.putString(context, Constant.USER_TOKEN, token);
    }

    /**
     * 读取登录账号数据
     *
     * @param context
     * @return
     */
    public static @Nullable
    String getToken(Context context) {
        String account = PreferencesUtils.getString(context, Constant.USER_TOKEN);
        return account;
    }

    /**
     * 保存登录账号数据
     *
     * @param context
     * @param loginBool
     */
    public static void saveLoginBool(Context context, Boolean loginBool) {
        PreferencesUtils.putBoolean(context, Constant.LOGIN_BOOL, loginBool);
    }

    /**
     * 读取登录账号数据
     *
     * @param context
     * @return
     */
    public static @Nullable
    Boolean getLoginBool(Context context) {
        Boolean loginBool = PreferencesUtils.getBoolean(context, Constant.LOGIN_BOOL);
        return loginBool;
    }


    /**
     * 保存登录密码数据
     *
     * @param context
     * @param password
     */
    public static void savePassword(Context context, String password) {
        PreferencesUtils.putString(context, Constant.KEY_PASSWORD, password);
    }

    /**
     * 读取登录密码数据
     *
     * @param context
     * @return
     */
    public static @Nullable
    String getPassword(Context context) {
        String password = PreferencesUtils.getString(context, Constant.KEY_PASSWORD);
        return password;
    }

    /**
     * 保存登录数据
     *
     * @param context
     * @param loginInfo
     */
    public static void saveLoginInfo(Context context, LoginInfoRes loginInfo) {
        PreferencesUtils.putString(context, Constant.KEY_LOGIN_INFO, GsonUtils.toJson(loginInfo));
    }

    /**
     * 读取登录数据
     *
     * @param context
     * @return
     */
    public static @Nullable
    LoginInfoRes getLoginInfo(Context context) {
        String json = PreferencesUtils.getString(context, Constant.KEY_LOGIN_INFO);
        LoginInfoRes loginInfo = GsonUtils.fromJson(json, LoginInfoRes.class);
        return loginInfo;
    }

    /**
     * 保存用户数据
     *
     * @param context
     * @param user
     */
    public static void saveUser(Context context, User user) {
        PreferencesUtils.putString(context, Constant.KEY_USER, GsonUtils.toJson(user));
    }

    /**
     * 读取用户数据
     *
     * @param context
     * @return
     */
    public static @Nullable
    User getUser(Context context) {
        String json = PreferencesUtils.getString(context, Constant.KEY_USER);
        User user = GsonUtils.fromJson(json, User.class);
        return user;
    }

}

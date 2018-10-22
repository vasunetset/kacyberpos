package com.kacyber.pos.util;

import android.content.Context;
import android.text.TextUtils;

import com.kacyber.pos.App;
import com.kacyber.pos.R;
import com.kacyber.pos.entity.response.LoginInfoRes;
import com.kacyber.pos.net.RetrofitService;
import com.kacyber.pos.net.SimpleSubscriber;
import com.kacyber.pos.ui.MainActivity;
import com.kacyber.pos.util.common.ToastUtils;

/**
 * 鉴权
 * Created by mzy on 2017/7/4.
 */

public class LoginAccountHelper {

    /**
     * 登录状态，默认根据是否保存用户信息判断是否已登录
     */
    private static boolean hasLogin = GlobalStore.getUser(App.getInstance()) != null;

    /**
     * 获取登录状态
     * @return
     */
    public static boolean isHasLogin() {
        return hasLogin;
    }

    public interface Callback {
        void onSuccess();
        void onFailed();
    }

    /**
     * 是否需要自动登录
     * @param context
     * @return
     */
    public static boolean shouldAutoLogin(Context context) {
        boolean autoLogin = false;
        String account = GlobalStore.getAccount(context);
        String password = GlobalStore.getPassword(context);
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
            autoLogin = true;
        }
        return autoLogin;
    }

    /**
     * 手动普通登录
     * @param context
     * @param account
     * @param password
     * @param callback
     */
    public static void manualNormalLogin(Context context, String account, String password, Callback callback) {
        normalLogin(context, account, password, true, callback);
    }

    /**
     * 自动登录，静默登录
     * @param context
     * @param callback
     */
    public static void autoLogin(Context context, Callback callback) {
        autoNormalLogin(context, callback);
    }

    /**
     * 自动普通登录，静默登录
     * @param context
     * @param callback
     */
    private static void autoNormalLogin(Context context, Callback callback) {
        String account = GlobalStore.getAccount(context);
        String password = GlobalStore.getPassword(context);
        normalLogin(context, account, password, false, callback);
    }

    /**
     * 普通登录
     * @param context
     * @param account
     * @param password
     * @param manualLogin
     * @param callback
     */
    private static void normalLogin(final Context context, final String account, final String password, final boolean manualLogin, final Callback callback) {
        if (manualLogin) {
            DialogHelper.showLoadingDialog(context, context.getString(R.string.dialog_login));
        }
        RetrofitService.login(account, password).subscribe(new SimpleSubscriber<LoginInfoRes>() {
            @Override
            public void onSuccess(LoginInfoRes loginInfoRes) {
                GlobalStore.saveAccount(context, account);
                GlobalStore.savePassword(context, password);
                GlobalStore.saveLoginInfo(context, loginInfoRes);

                UserInfoHelper.getUserInfo(new UserInfoHelper.OnGetUserInfoListener() {
                    @Override
                    public void onSuccess() {
                        hasLogin = true;
                        loginSuccess();

                        if (manualLogin) {
                            ToastUtils.show(R.string.toast_login_suc);
                            DialogHelper.dismissLoadingDialog();
                            launchToMainActivity(context);
                        }
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    }

                    @Override
                    public void onFailed() {
                        if (manualLogin) {
                            DialogHelper.dismissLoadingDialog();
                        }
                        if (callback != null) {
                            callback.onFailed();
                        }
                    }
                });
            }

            @Override
            public void onFailed(Throwable e) {

            }
        });
    }

    /**
     * 登录成功
     */
    private static void loginSuccess() {
        // nothing
    }

    /**
     * 注销账户，hasLogin置为NO，同时会清除保存的password和token
     * @param context
     */
    public static void logout(Context context) {
        hasLogin = false;
        clearAll(context);
        launchToMainActivity(context);
    }

    /**
     * 登录失效，hasLogin置为NO
     * @param context
     */
    public static void invalid(Context context) {
        hasLogin = false;
        clearLoginInfo(context);
        launchToMainActivity(context);
    }

    /**
     * 清空保存的数据
     * @param context
     */
    private static void clearAll(Context context) {
        clearLoginInfo(context);
        //GlobalStore.saveAccount(context, null);
        GlobalStore.savePassword(context, null);
    }

    /**
     * 只清空登录的数据
     * @param context
     */
    private static void clearLoginInfo(Context context) {
        GlobalStore.saveLoginInfo(context, null);
        GlobalStore.saveUser(context, null);
    }

    /**
     * 跳转到主页
     * @param context
     */
    public static void launchToMainActivity(Context context) {
        MainActivity.start(context);
    }
}

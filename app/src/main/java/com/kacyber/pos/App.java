package com.kacyber.pos;

import android.app.Application;

import com.kacyber.pos.net.RetrofitService;
import com.kacyber.pos.util.ConnectivityReceiver;
import com.kacyber.pos.util.common.LoggerUtils;
import com.kacyber.pos.util.common.ToastUtils;

/**
 * App
 * Created by mzy on 2017/12/12.
 */
public class App extends Application {
    private static App instance;
    public static App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RetrofitService.init();
        ToastUtils.init(this);
        LoggerUtils.init(BuildConfig.DEBUG);
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}

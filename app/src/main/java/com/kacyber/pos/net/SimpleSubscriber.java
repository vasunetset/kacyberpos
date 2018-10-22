package com.kacyber.pos.net;

import com.kacyber.pos.entity.response.BaseRes;
import rx.Subscriber;

/**
 * 所有网络请求的订阅器，这里统一进行数据分流
 * Created by mzy on 2017/6/21.
 */

public abstract class SimpleSubscriber<T extends BaseRes> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
        /*if (!NetUtil.isNetworkAvailable(App.getInstance())) {
            ToastUtils.show(R.string.toast_network_not_available);
            onCompleted();
        }*/
    }

    @Override
    public void onCompleted() {
        // nothing
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
    public abstract void onFailed(Throwable e);
}

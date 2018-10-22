package com.kacyber.pos.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kacyber.pos.App;
import com.kacyber.pos.BuildConfig;
import com.kacyber.pos.entity.request.LoginReq;
import com.kacyber.pos.entity.response.LoginInfoRes;
import com.kacyber.pos.entity.response.TicketInfoRes;
import com.kacyber.pos.entity.response.UserInfoRes;
import com.kacyber.pos.net.api.IAuthApi;
import com.kacyber.pos.net.api.IMemberApi;
import com.kacyber.pos.net.api.ITicketApi;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.LoggerUtils;
import com.kacyber.pos.util.common.NetUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mzy on 2017/6/8.
 */

public class RetrofitService {

    private static IAuthApi sAuthService;
    private static IMemberApi sMemberService;
    private static ITicketApi sTicketService;

    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化网络通信服务，在Application中注册
     */
    public static void init() {
        // 指定缓存路径,缓存大小50Mb
        Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 50);
        sHttpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(sHttpLoggingInterceptor)
                .addInterceptor(sHeaderAuthorizationInterceptor)
                .addInterceptor(sCacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        sAuthService = retrofit.create(IAuthApi.class);
        sMemberService = retrofit.create(IMemberApi.class);
        sTicketService = retrofit.create(ITicketApi.class);
    }

    /**
     * 请求添加Header拦截器
     */
    private static final Interceptor sHeaderAuthorizationInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LoginInfoRes loginInfo = GlobalStore.getLoginInfo(App.getInstance());
            String token = loginInfo == null ? "" : loginInfo.token;
            Headers headers = request.headers().newBuilder()
                    .add("token", token)
                    .build();
            request = request.newBuilder().headers(headers).build();
            return chain.proceed(request);
        }
    };

    /**
     * 打印请求url和数据的拦截器
     */
    @Deprecated
    private static final Interceptor sRequestLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                LoggerUtils.d("request.body() == null");
            }
            // 打印url信息
            LoggerUtils.d(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final HttpLoggingInterceptor sHttpLoggingInterceptor = new HttpLoggingInterceptor();

    /**
     * 解析参数
     *
     * @param body
     * @param requestBuffer
     * @return
     * @throws UnsupportedEncodingException
     */
    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    private static final Interceptor sCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // 网络不可用
            if (!NetUtil.isNetworkAvailable(App.getInstance())) {
                // 在请求头中加入：强制使用缓存，不访问网络
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            // 网络可用
            if (NetUtil.isNetworkAvailable(App.getInstance())) {
                int maxAge = 0;
                // 有网络时 在响应头中加入：设置缓存超时时间0个小时
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // 无网络时，在响应头中加入：设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    /************************************ API *******************************************/

    public static Observable<LoginInfoRes> login(String account, String password) {
        LoginReq req = new LoginReq(account, password);
        return sAuthService.login(req)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<UserInfoRes> userInfo() {
        return sMemberService.userInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<TicketInfoRes> ticket() {
        return sTicketService.ticket()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Deprecated
    public static class FlatMapTransformer<T> implements Observable.Transformer<Map<String, List<T>>, T> {
        private String mMapKey;

        public FlatMapTransformer<T> setMapKey(String mapKey) {
            mMapKey = mapKey;
            return this;
        }

        @Override
        public Observable<T> call(Observable<Map<String, List<T>>> mapObservable) {
            return mapObservable.flatMap(new Func1<Map<String, List<T>>, Observable<T>>() {
                @Override
                public Observable<T> call(Map<String, List<T>> stringListMap) {
                    if (TextUtils.isEmpty(mMapKey)) {
                        return Observable.error(new Throwable("Map Key is empty"));
                    }
                    return Observable.from(stringListMap.get(mMapKey));
                }
            }).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}

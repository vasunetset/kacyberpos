package com.kacyber.pos.net;

import com.kacyber.pos.BuildConfig;

/**
 * Created by mzy on 2017/12/9.
 */
public class API {

    public static final String HOST_DEVELOP = "http://34.241.247.127:8001/";

    public static final String HOST_PRODUCT = "http://34.241.247.127:8001/";

    public static final String HOST = BuildConfig.DEBUG ? HOST_DEVELOP : HOST_PRODUCT;

}

package com.kacyber.pos.util.common;

import com.google.gson.Gson;

/**
 * Created by mzy on 2017/6/16.
 */

public class GsonUtils {

    public static String toJson(Object src) {
        return new Gson().toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }
}

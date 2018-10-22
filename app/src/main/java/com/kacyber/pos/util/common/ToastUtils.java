package com.kacyber.pos.util.common;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.kacyber.pos.BuildConfig;

/**
 * Created by mzy on 2018/3/17.
 */

public class ToastUtils {

    private static Context mContext;

    private static Toast toast = null;

    private static String oldMsg;

    private static long oneTime = 0;
    private static long twoTime = 0;

    /**
     * 在Application中注册
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 显示toast
     * @param text
     */
    public static void show(String text) {
        showToast(text);
    }

    /**
     * 显示toast
     * @param resId
     */
    public static void show(@StringRes int resId) {
        showToast(mContext.getString(resId));
    }

    /**
     * 在debug模式下打印信息
     * @param text
     */
    public static void debugShow(String text) {
        if (BuildConfig.DEBUG) {
            showToast(text);
        }
    }

    /**
     * 在debug模式下打印信息
     * @param resId
     */
    public static void debugShow(@StringRes int resId) {
        if (BuildConfig.DEBUG) {
            showToast(mContext.getString(resId));
        }
    }

    /**
     * 显示toast
     * @param text
     */
    private static void showToast(String text) {
        if (toast == null) {
            oldMsg = text;
            toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (text.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = text;
                //toast.setText(text);
                toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
                toast.show();

            }
            oneTime = twoTime;
        }
    }

}

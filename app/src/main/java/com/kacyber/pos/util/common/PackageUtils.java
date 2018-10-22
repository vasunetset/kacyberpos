package com.kacyber.pos.util.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by SuShuai on 16/7/27.
 */
public class PackageUtils {

    public PackageUtils() {
    }

    /**
     * 获取包名，如com.mzy.weijiaxing
     * @param context
     * @return 包名
     */
    public static String getPackageName(Context context) {
        String packageName = getPackageInfo(context).packageName;
        return packageName;
    }

    /**
     * 获取版本Code，如1
     * @param context
     * @return 版本Code
     */
    public static int getPackageVersionCode(Context context) {
        int versionCode = getPackageInfo(context).versionCode;
        return versionCode;
    }

    /**
     * 获取版本name，如v1.0.0
     * @param context
     * @return 版本名
     */
    public static String getPackageVersionName(Context context) {
        String versionName = getPackageInfo(context).versionName;
        return versionName;
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;

        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace(System.err);
        }

        if(info == null) {
            info = new PackageInfo();
        }

        return info;
    }
}

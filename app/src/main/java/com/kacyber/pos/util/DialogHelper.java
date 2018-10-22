package com.kacyber.pos.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;

import com.kacyber.pos.util.common.DialogUtils;

/**
 * Created by mzy on 2017/6/19.
 */

public class DialogHelper {

    private static ProgressDialog dialog;

    private DialogHelper() {
        throw new AssertionError();
    }

    /**
     * 显示进度框
     * @param context
     * @param resId
     */
    public static void showLoadingDialog(Context context, @StringRes int resId) {
        showLoadingDialog(context, context.getString(resId));
    }

    /**
     * 显示进度框
     * @param context
     * @param msg
     */
    public static void showLoadingDialog(Context context, String msg) {
        try{
            dialog = new ProgressDialog(context);
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage(msg);
            dialog.show();
            // special, change message text color.
            DialogUtils.changeProgressDialogMessageTextColor(context, dialog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 隐藏进度框
     */
    public static void dismissLoadingDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.kacyber.pos.util.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;

import com.kacyber.pos.R;

import java.lang.reflect.Field;

/**
 * Created by mzy on 2017/9/6.
 */

public class DialogUtils {

    /**
     * 改变ProgressDialog显示文字的颜色
     * @param context
     * @param dialog
     */
    public static void changeProgressDialogMessageTextColor(Context context, ProgressDialog dialog) {
        try {
            Field mMessage = ProgressDialog.class.getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(dialog);
            mMessageView.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变AlertDialog的Button显示文字的颜色为红色
     * @param context
     * @param dialog
     */
    public static void changeAlertDialogButtonTextColorRed(Context context, AlertDialog dialog) {
        final Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setTextColor(context.getResources().getColor(R.color.textColorRed));
    }

}

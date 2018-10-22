package com.kacyber.pos.util.common;

import android.graphics.Bitmap;
import android.util.Log;

import com.kacyber.pos.util.DialogHelper;

import hardware.print.printer;

/**
 * 打印机帮助类
 * Created by mzy on 2018/3/27.
 */

public class PrintHelper {

    private printer mPrinter;

    private static PrintHelper mInstance;

    public PrintHelper() {
        super();
        mPrinter = new printer();
    }

    public static PrintHelper getInstance() {
        if (mInstance == null) {
            mInstance = new PrintHelper();
        }
        return mInstance;
    }

    public void startPrint(Bitmap bitmap) {
        int result = mPrinter.Open();
        //  Bitmap binarizationBitmap = BitmapUtils.binarization(bitmap);
//        Bitmap resizeBzationBitmap = BitmapUtils.binarization(bitmap);
        Bitmap resizeBitmap = bitmap;
        mPrinter.SetGrayLevel((byte) 7);
        if (result == 0) {
            if (!mPrinter.IsOutOfPaper() && !mPrinter.IsOverTemperature()) {
                if (resizeBitmap != null) {
                    mPrinter.PrintBitmap(resizeBitmap);
                    mPrinter.PrintLineInit(40);
                    mPrinter.Step((byte) 0x5f);
                    mPrinter.PrintLineEnd();
                    DialogHelper.dismissLoadingDialog();
                }
            } else {
                Log.e("Printer", "NoConnected");
                DialogHelper.dismissLoadingDialog();
            }
            mPrinter.Close();
        }
    }

    /**
     * 调整bitmap的大小到打印机适合的大小
     *
     * @param bitmap
     * @return
     */
    public static Bitmap resize(Bitmap bitmap) {
        int width = 388;
        int height = bitmap.getHeight();
          // return BitmapUtils.scale(bitmap, width, height * 11 / 19);
           return BitmapUtils.scale(bitmap, width, (int)((height * 11) / 19)+20 );

        /*Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        float originalWidth = bitmap.getWidth();
        float originalHeight = bitmap.getHeight();
        Canvas canvas = new Canvas(background);

        float scaleX = (float) 1280 / originalWidth;
        float scaleY = (float) 720 / originalHeight;

        float xTranslation = 0.0f;
        float yTranslation = 0.0f;
        float scale = 1;

        if (scaleX < scaleY) { // Scale on X, translate on Y
            scale = scaleX;
            yTranslation = (height - originalHeight * scale) / 2.0f;
        } else { // Scale on Y, translate on X
            scale = scaleY;
            xTranslation = 0.0f;;
        }

        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, transformation, paint);
        return background;*/
    }
}

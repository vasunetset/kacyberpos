package com.kacyber.pos.devices.cilico;

import android.graphics.Bitmap;
import android.util.Log;

import com.kacyber.pos.devices.IPrinter;
import com.kacyber.pos.util.DialogHelper;
import com.kacyber.pos.util.common.BitmapUtils;

import hardware.print.printer;

/**
 * 富立叶打印机
 * Created by mzy on 2018/3/27.
 */
public class CILICOPrinter implements IPrinter{

    private printer mPrinter;

    private static CILICOPrinter mInstance;

    public CILICOPrinter() {
        super();
        mPrinter = new printer();
    }

    public static CILICOPrinter getInstance() {
        if (mInstance == null) {
            mInstance = new CILICOPrinter();
        }
        return mInstance;
    }

    @Override
    public void init() {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isReady() {
        return true;
    }

    public void printImage(Bitmap bitmap) {
        int result = mPrinter.Open();
        Bitmap resizeBitmap = resize(bitmap);;
        Bitmap bmp = BitmapUtils.binarization(resizeBitmap);
        mPrinter.SetGrayLevel((byte) 7);
        if (result == 0) {
            if (!mPrinter.IsOutOfPaper() && !mPrinter.IsOverTemperature()) {
                if (resizeBitmap != null) {
                    mPrinter.PrintBitmap(bmp);
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
     */
    private Bitmap resize(Bitmap bitmap) {
        int width = 388;
        int height = bitmap.getHeight();
        return BitmapUtils.scale(bitmap, width, (int) ((height * 11) / 19) + 20);

    }
}

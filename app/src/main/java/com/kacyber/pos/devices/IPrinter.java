package com.kacyber.pos.devices;

import android.graphics.Bitmap;

/**
 * Created by zmtianch on 2018/9/30.
 */

public interface IPrinter {

    void init();

    void close();

    boolean isReady();

    void printImage(Bitmap bitmap);

}

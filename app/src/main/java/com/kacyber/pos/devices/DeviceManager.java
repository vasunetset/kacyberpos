package com.kacyber.pos.devices;

import android.os.Build;

import com.kacyber.pos.devices.cilico.CILICOPrinter;
import com.kacyber.pos.devices.iprt.IPRTPrinter;
import com.kacyber.pos.devices.sunmi.SUNMIPrinter;

/**
 * Created by zmtianch on 2018/9/30.
 */

public class DeviceManager {

    private static DeviceManager sInstance;

    private boolean mHasPrinter;
    private boolean mHasScanner;


    private DeviceManager() {
        if("SUNMI".equals(Build.BRAND)){
            mHasPrinter = true;
        } else if("alps".equalsIgnoreCase(Build.BRAND)) {
            mHasPrinter = true;
            mHasScanner = true;
        }
    }

    public static DeviceManager getInstance() {
        if (sInstance == null) {
            sInstance = new DeviceManager();
        }
        return sInstance;
    }

    public IPrinter getPrinter() {
        if("SUNMI".equals(Build.BRAND)){
            return SUNMIPrinter.getInstance();
        } else if("alps".equalsIgnoreCase(Build.BRAND)) {
            return CILICOPrinter.getInstance();
        }

        return IPRTPrinter.getInstance();
    }

    public boolean hasPrinter(){
        return mHasPrinter;
    }

    public boolean hasScanner(){
        return mHasScanner;
    }

}

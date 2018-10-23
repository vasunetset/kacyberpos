package com.kacyber.pos.devices.cilico.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by zmtianch on 2018/10/1.
 */

public class Scanner {

    String m_Broadcastname = "com.barcode.sendBroadcast";// com.barcode.sendBroadcastScan

    private static Scanner sInstance = new Scanner();

    private Scanner() {


    }

    public static Scanner getInstance(){
        return sInstance;
    }

    public void init(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(m_Broadcastname);
        context.registerReceiver(receiver, intentFilter);
    }


    public void scan(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.barcode.sendBroadcastScan");
        context.sendBroadcast(intent);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {

            if (arg1.getAction().equals(m_Broadcastname)) {
                String str = arg1.getStringExtra("BARCODE");
                Log.e("aaaaa", "aaaaaa " + str);
            }
        }
    };


}

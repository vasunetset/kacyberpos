package com.kacyber.pos.devices.iprt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.iprt.android_print_sdk.BluetoothPrinter;
import com.iprt.android_print_sdk.PrinterType;
import com.kacyber.pos.App;
import com.kacyber.pos.R;
import com.kacyber.pos.devices.IPrinter;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.BitmapUtils;

/**
 * Created by zmtianch on 2018/9/30.
 */

public class IPRTPrinter implements IPrinter {
    private static final String TAG = "IPRTPrinter";
    public BluetoothPrinter bPrinter;
    private boolean isConnected;

    private boolean mShowToast = true;

    private static IPRTPrinter sInstance;

    public static IPRTPrinter getInstance() {
        if (sInstance == null) {
            sInstance = new IPRTPrinter();
        }
        return sInstance;
    }

    private IPRTPrinter() {

    }

    @Override
    public void init() {
        if (isConnected) {
            return;
        }

        String address = GlobalStore.getBluetoothAddress(App.getInstance());
        if (TextUtils.isEmpty(address)) {
            return;
        }
        mShowToast = false;
        BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
        String mConnectedDeviceName = device.getName();
        Log.i(TAG, "connected device name is : " + mConnectedDeviceName + ", address is : "
                + address);
        Log.i(TAG, "device.getBondState() is : " + device.getBondState());

        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            Log.i(TAG, "device.getBondState() is BluetoothDevice.BOND_BONDED");
            initPrinter(device);
        }
    }

    @Override
    public void close() {
        if (isConnected) {
            bPrinter.closeConnection();
        }
        isConnected = false;
    }

    @Override
    public boolean isReady() {
        return isConnected;
    }


    public void initPrinter(BluetoothDevice device) {
        bPrinter = new BluetoothPrinter(device);
        bPrinter.setCurrentPrintType(PrinterType.M21);
        //set handler for receive message of connect state from sdk.
        bPrinter.setHandler(bHandler);
        bPrinter.openConnection();
        bPrinter.setEncoding("GBK");
    }


    private final Handler bHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "msg.what is : " + msg.what);
            switch (msg.what) {
                case BluetoothPrinter.Handler_Connect_Connecting:
                    showToast(R.string.bt_connecting);
                    break;
                case BluetoothPrinter.Handler_Connect_Success:
                    isConnected = true;
                    showToast(R.string.bt_connect_success);
                    break;
                case BluetoothPrinter.Handler_Connect_Failed:
                    isConnected = false;
                    showToast(R.string.bt_connect_failed);
                    break;
                case BluetoothPrinter.Handler_Connect_Closed:
                    isConnected = false;
                    showToast(R.string.bt_connect_closed);
                    break;
            }
        }
    };

    private void showToast(int resId) {
        if (!mShowToast) {
            return;
        }
        Toast.makeText(App.getInstance(), resId,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void printImage(Bitmap bitmap) {
        if (!isConnected) {
            return;
        }
        Bitmap resizeBitmap = resize(bitmap);
        Bitmap bmp = BitmapUtils.binarizationWhiteBg(resizeBitmap);
        bPrinter.setLeftMargin(0, 0);
        //单色图片
        bPrinter.printImage(bmp);
        bPrinter.setPrinter(BluetoothPrinter.COMM_PRINT_AND_WAKE_PAPER_BY_LINE, 1);

    }

    /**
     * 调整bitmap的大小到打印机适合的大小
     */
    private Bitmap resize(Bitmap bitmap) {
        int width = 380;
        int height = bitmap.getHeight();
        return BitmapUtils.scale(bitmap, width, (int) ((height * 11) / 19) + 20);

    }
}

package com.kacyber.pos.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kacyber.pos.R;
import com.kacyber.pos.devices.iprt.IPRTPrinter;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.DialogHelper;
import com.kacyber.pos.util.GlobalStore;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by xzc-pc on 2016/8/8.
 */

/**
 * This Activity appears as a dialog. It lists any paired devices and
 * devices detected in the area after discovery. When a device is chosen
 * by the user, the MAC address of the device is sent back to the parent
 * Activity in the result Intent.
 */
public class BluetoothDeviceListActivity extends BaseActivity {
    private static final String TAG = "DeviceListActivity";
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String EXTRA_RE_PAIR = "mRePair";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ListView pairedListView;
    private Button scanButton;
    private boolean hasRegisteredBoundReceiver;
    private BluetoothDevice currentDevice;
    private boolean mRePair = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the window
        setTitle(R.string.select_device);

        // Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);

        // Initialize the button to perform device discovery
        scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
                v.setEnabled(false);
            }
        });

        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // Find and set up the ListView for paired devices
        pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);
        pairedListView.setOnItemLongClickListener(mDeviceLongClickListener);
//        pairedListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            public void onCreateContextMenu(ContextMenu menu, View view,
//                    ContextMenu.ContextMenuInfo arg2) {
//                menu.setHeaderTitle(R.string.select_options);
//
//                String info =
//                        ((TextView) (((AdapterView.AdapterContextMenuInfo) arg2).targetView))
//                                .getText().toString();
//                if (info.contains(" ( " + getResources().getText(R.string.has_paired) + " )")) {
//                    menu.add(0, 0, 0, R.string.rePaire_connect).setOnMenuItemClickListener(
//                            mOnMenuItemClickListener);
//                    menu.add(0, 1, 1, R.string.connect_paired).setOnMenuItemClickListener(
//                            mOnMenuItemClickListener);
//                } else {
//                    menu.add(0, 2, 2, R.string.paire_connect).setOnMenuItemClickListener(
//                            mOnMenuItemClickListener);
//                }
//            }
//        });

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBtAdapter.isEnabled()) {
            String noDevices = getResources().getText(R.string.bluetooth_disabled).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
            pairedListView.setEnabled(false);
            scanButton.setEnabled(false);
        }

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String status;
                if(device.getAddress().equals(GlobalStore.getBluetoothAddress(this))){
                    status = getResources().getString(R.string.selected);
                } else {
                    status = getResources().getString(R.string.has_paired);
                }
                mPairedDevicesArrayAdapter.add(device.getName()
                        + " ( " + status + " )"
                        + "\n" + device.getAddress());
            }
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_device_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null && mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register for broadcasts when a device is discovered and discovery has finished
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        Log.d(TAG, "doDiscovery()");
        DialogHelper.showLoadingDialog(this, R.string.scanning);
        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        mPairedDevicesArrayAdapter.clear();
        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    private void returnToPreviousActivity(String address, boolean re_pair) {
        // Cancel discovery because it's costly and we're about to connect
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
        String mConnectedDeviceName = device.getName();
        Log.i(TAG, "connected device name is : " + mConnectedDeviceName + ", address is : "
                + address);
        Log.i(TAG, "device.getBondState() is : " + device.getBondState());
        GlobalStore.saveBluetoothAddress(this, address);
        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            Log.i(TAG, "device.getBondState() is BluetoothDevice.BOND_BONDED");
            if (re_pair) {
                pairDevice(true, device);
            } else {
                IPRTPrinter.getInstance().initPrinter(device);
                finish();
            }
        } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            pairDevice(false, device);
        }
    }

    // The on-click listener for all devices in the ListViews
    private AdapterView.OnItemClickListener mDeviceClickListener =
            new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
                    // Get the device MAC address, which is the last 17 chars in the View
                    String info = ((TextView) v).getText().toString();
                    String address = info.substring(info.length() - 17);
                    returnToPreviousActivity(address, false);
                }
            };

    private AdapterView.OnItemLongClickListener mDeviceLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3) {
                    return false;
                }
            };

    private final MenuItem.OnMenuItemClickListener mOnMenuItemClickListener =
            new MenuItem.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    String info =
                            ((TextView) ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo())
                                    .targetView).getText().toString();
                    String address = info.substring(info.length() - 17);
                    switch (item.getItemId()) {
                        case 0:
                            returnToPreviousActivity(address, true);
                            break;
                        case 1:
                        case 2:
                            returnToPreviousActivity(address, false);
                            break;
                    }
                    return false;
                }
            };

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                String itemName = device.getName()
                        + " ( " + getResources().getText(
                        device.getBondState() == BluetoothDevice.BOND_BONDED ? R.string.has_paired
                                : R.string.not_paired) + " )"
                        + "\n" + device.getAddress();
                mPairedDevicesArrayAdapter.remove(itemName);
                mPairedDevicesArrayAdapter.add(itemName);
                pairedListView.setEnabled(true);
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                DialogHelper.dismissLoadingDialog();
                if (mPairedDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mPairedDevicesArrayAdapter.add(noDevices);
                    pairedListView.setEnabled(false);
                }
                scanButton.setEnabled(true);
            }
        }
    };

    private BroadcastReceiver boundDeviceReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!currentDevice.equals(device)) {

                    return;
                }
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
                        Log.i(TAG, "bounding......");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Log.i(TAG, "bound success");
                        // if bound success, auto init BluetoothPrinter. open connect.
                        if (hasRegisteredBoundReceiver) {
                            unregisterReceiver(boundDeviceReceiver);
                            hasRegisteredBoundReceiver = false;
                        }
                        IPRTPrinter.getInstance().initPrinter(device);
                        break;
                    case BluetoothDevice.BOND_NONE:
                        if (mRePair) {
                            mRePair = false;
                            Log.i(TAG, "removeBond success, wait create bound.");
                            pairDevice(false, device);
                        } else if (hasRegisteredBoundReceiver) {
                            unregisterReceiver(boundDeviceReceiver);
                            hasRegisteredBoundReceiver = false;
                            Log.i(TAG, "bound cancel");
                        }
                    default:
                        break;
                }
            }
        }
    };

    private boolean pairDevice(boolean rePair, BluetoothDevice device) {
        boolean success = false;
        try {
            if (!hasRegisteredBoundReceiver) {
                currentDevice = device;
                registerReceiver(boundDeviceReceiver,
                        new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
                hasRegisteredBoundReceiver = true;
            }

            if (rePair) {
                Method removeBondMethod = BluetoothDevice.class.getMethod("removeBond");
                success = (Boolean) removeBondMethod.invoke(device);
                Log.i(TAG, "removeBond is success? : " + success);
            } else {
                //Method setPinMethod = BluetoothDevice.class.getMethod("setPin");
                //setPinMethod.invoke(device, 1234);
                Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                success = (Boolean) createBondMethod.invoke(device);
                Log.i(TAG, "createBond is success? : " + success);
            }
        } catch (Exception e) {
            Log.i(TAG, "removeBond or createBond failed.");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

}


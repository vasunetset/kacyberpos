package com.kacyber.pos.util.common;

/**
 *
 * Created by mzy on 2018/3/27.
 */

public class ScanUtils {
    
    // ******** Please put this code in your Activity. ********
    
   /* // send scan broadcast
    private void sendScanIntent() {
        Intent intent = new Intent();
        intent.setAction("com.barcode.sendBroadcastScan");
        sendBroadcast(intent);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }
    
    private static final String BROADCAST_ACTION_NAME = "com.barcode.sendBroadcast";
    
    private void registerReceiver() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION_NAME);
        registerReceiver(receiver, intentFilter);
    }
    
    private void unregisterReceiver() {
        unregisterReceiver(receiver);
    }
    
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST_ACTION_NAME)) {
                String barCode = intent.getStringExtra("BARCODE");
                if (!TextUtils.isEmpty(barCode)) {
                    TicketActivity.start(MainActivity.this, barCode, null);
                }
            }
        }
    };*/
}

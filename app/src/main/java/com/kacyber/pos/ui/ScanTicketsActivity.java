package com.kacyber.pos.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.google.zxing.Result;
import com.kacyber.pos.R;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.GlobalStore;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;

public class ScanTicketsActivity extends BaseActivity implements ApiResponse, ZXingScannerView.ResultHandler, BaseActivity.PermCallback {
    private ZXingScannerView mScannerView;
    ;
    Handler handler;
    Call<JsonObject> getTicketDetail;

    public static final int PERMISSION_REQUEST_CAMERA = 1;
    private String deviceID_Name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkPermissions(new String[]{Manifest.permission.CAMERA}, 123, this)) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_scan_tickets;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_scan_tickets);
        FrameLayout container = findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        container.addView(mScannerView);
        deviceID_Name = getDeviceId();


    }

    private void getBookinDetailsByETicket(String code) {
        // RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        getTicketDetail = apiInterface.getTicketDetailsByETicket(GlobalStore.getToken(getApplicationContext()), code, deviceID_Name);
        apiHitAndHandle.makeApiCall(getTicketDetail, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*mScannerView.setResultHandler(this);
        mScannerView.startCamera();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // This is because the dialog was cancelled when we recreated the activity.
        if (permissions.length == 0 || grantResults.length == 0)
            return;

        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mScannerView.setResultHandler(this);
                    mScannerView.startCamera();
                } else {
                    finish();
                }
            }
            break;
        }
    }


    @Override
    public void handleResult(Result result) {
        String code = result.getText();
        Log.v("result", result.getText()); // Prints scan results
        Log.v("result", result.getBarcodeFormat().toString());
        if (code.contains("kacyber")) {
            getBookinDetailsByETicket(result.getText());
        } else {
            showDialog("Unrecognized QR code!");
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("KaCyber");
        builder.setCancelable(false);
        builder.setMessage(message);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.resumeCameraPreview(ScanTicketsActivity.this);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onSuccess(Call call, Object object) {
        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                finish();
                JSONObject bookingDetailObject = jsonObject.getJSONObject("BookingDetails");
                //TicketActivity.startForBookingDetails(this, bookingDetailObject, null);
                Intent intent = new Intent(ScanTicketsActivity.this, TicketActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("bookingDetails", bookingDetailObject.toString());
                intent.putExtra("from", "manual");
                startActivity(intent);
            }else {
                showDialog(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        showToast("Error :" + errorMessage);
        finish();
    }

    @Override
    public void permGranted(int resultCode) {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void permDenied(int resultCode) {

    }
}

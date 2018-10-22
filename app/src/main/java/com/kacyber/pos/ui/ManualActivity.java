package com.kacyber.pos.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import retrofit2.Call;

/**
 * 手动验票
 */
public class ManualActivity extends BaseActivity implements ApiResponse {

    Call<JsonObject> getTicketDetail;
    @BindView(R.id.et_code)
    EditText mCodeEditText;
    String deviceID_Name="";

    @Override
    public int getLayoutResID() {
        return R.layout.activity_manual;
    }

    @Override
    public boolean hideNavigationIcon() {
        return false;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mCodeEditText.requestFocus();
        if (checkPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 123, new PermCallback() {
            @Override
            public void permGranted(int resultCode) {
                deviceID_Name = getDeviceId();
            }

            @Override
            public void permDenied(int resultCode) {

            }
        })) {
            deviceID_Name = getDeviceId();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manual, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.done).setVisible(true);
        menu.findItem(R.id.clear).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if (checkValid()) {

                    String code = mCodeEditText.getText().toString().trim();
                    getBookinDetailsByETicket(code);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getBookinDetailsByETicket(String code) {
        /*JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", GlobalStore.getToken(ManualActivity.this));
            jsonObject.put("ticketNumber", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());*/
        getTicketDetail = apiInterface.getTicketDetailsByETicket(GlobalStore.getToken(getApplicationContext()), code,deviceID_Name);
        apiHitAndHandle.makeApiCall(getTicketDetail, this);
    }

    private boolean checkValid() {
        String code = mCodeEditText.getText().toString().trim();
        boolean isCodeEmpty = TextUtils.isEmpty(code);
        if (isCodeEmpty) {
            ToastUtils.show(getString(R.string.toast_input_code_please));
        }
        return !isCodeEmpty;
    }

    @Override
    public void onSuccess(Call call, Object object) {
        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if (status.equalsIgnoreCase("1")) {
                Log.e("result>>", object.toString());
                JSONObject bookingDetailObject = jsonObject.getJSONObject("BookingDetails");
                //TicketActivity.startForBookingDetails(this, bookingDetailObject, null);
                Intent intent = new Intent(getApplicationContext(), TicketActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("bookingDetails", bookingDetailObject.toString());
                intent.putExtra("from", "manual");
                startActivity(intent);
            } else {
                ToastUtils.show(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}

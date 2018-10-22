package com.kacyber.pos.retrofitManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.kacyber.pos.BuildConfig;
import com.kacyber.pos.ui.LoginActivity;
import com.kacyber.pos.util.ConnectivityReceiver;
import com.kacyber.pos.util.DialogHelper;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class ApiHitAndHandle implements Callback {

    static final String TAG = ApiHitAndHandle.class.getSimpleName();
    private static ApiHitAndHandle apiHitAndHandle;
    private static Context mContext;
    private HashMap<Call, ApiResponse> apiResponseHashMap = new HashMap<>();

    public static ApiHitAndHandle getInstance(Context context) {
        if (apiHitAndHandle == null) {
            apiHitAndHandle = new ApiHitAndHandle();
        }
        mContext = context;
        return apiHitAndHandle;
    }

    public void makeApiCall(Call call, ApiResponse apiResponse) {
        makeApiCall(call, true, apiResponse);
    }

    public void makeApiCall(Call call, boolean showProgress, ApiResponse apiResponse) {
        boolean isNetworkAvailable = checkConnection();
        if (isNetworkAvailable) {
            try {
                apiResponseHashMap.put(call, apiResponse);
                call.enqueue(this);
                if (showProgress) {
                    DialogHelper.showLoadingDialog(mContext, "Loading..");
                }
                //Logs post URL
                log(call.request().url() + "");
                log("HEADER==" + call.request().headers().toString());
                log("Post Params >>>> \n" + bodyToString(call.request().body())
                        .replace("\r", "")
                        .replaceAll("--+[a-zA-Z0-9-\\/:=;\\ ]+\\n", "")
                        .replaceAll("Content+[a-zA-Z0-9-\\/:=;\\ ]+;\\s", "")
                        .replaceAll("Content+[a-zA-Z0-9-\\/:=;\\ ]+\\n", "")
                        .replaceAll("charset+[a-zA-Z0-9-\\/:=;\\ ]+\\n", "")
                        .replace("name=", "")
                        .replace("\n\n", "--> " + call.request().headers())
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.show("No Internet Available");
        }
    }

    private void log(String s) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, s);
        }
    }

    private String bodyToString(final RequestBody request) {
        try {
            Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        //((BaseActivity) mContext).stopProgressDialog();
        DialogHelper.dismissLoadingDialog();
        ApiResponse apiResponse = apiResponseHashMap.get(call);
        try {
            log("HEADER==" + "" + call.request().header("timeZone"));
            log("response>>" + response.body() + "");
            log("code>>" + response.code() + "");
            int responseCode = response.code();
            if (responseCode == 401 || responseCode == 400) {
                try {
                    try {
                        if (response.errorBody() == null) {
                            GlobalStore.saveLoginBool(mContext, false);
                            LoginActivity.start(mContext);
                            ((Activity) mContext).finish();
                        } else {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            final String message = jsonObject.optString("message");
                            oneButtonDialogone(mContext, message, "ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (mContext instanceof LoginActivity) {
                                        // ToastUtils.show(message);
                                    } else {
                                        GlobalStore.saveLoginBool(mContext, false);
                                        LoginActivity.start(mContext);
                                        ((Activity) mContext).finish();
                                    }
                                }
                            }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (responseCode == 200 || responseCode == 201) {
                apiResponse.onSuccess(call, response.body().toString());
            } else {
                try {
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.optString("message");
                        oneButtonDialogone(mContext, message, "ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        apiResponse.onError(call, "Server Not Respond", apiResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            apiResponseHashMap.remove(call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

        ToastUtils.show("Server not responding.");
        DialogHelper.dismissLoadingDialog();
        ApiResponse apiResponse = apiResponseHashMap.get(call);
        apiResponse.onError(call, t.getMessage(), apiResponse);
        apiResponseHashMap.remove(call);
    }


    public Dialog oneButtonDialogone(Context c, String msg, String buttonTxt, DialogInterface.OnClickListener clickListener) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(c);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setNeutralButton(buttonTxt, clickListener);
        return dialog.create();
    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
    }
}

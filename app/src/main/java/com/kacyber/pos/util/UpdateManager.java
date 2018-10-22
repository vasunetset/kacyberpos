package com.kacyber.pos.util;

import android.accounts.NetworkErrorException;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kacyber.pos.R;
import com.kacyber.pos.util.common.LogUtils;
import com.kacyber.pos.util.common.NetUtil;
import com.kacyber.pos.util.common.PackageUtils;
import com.kacyber.pos.util.common.ToastUtils;
import com.kacyber.pos.util.common.ValidationUtils;

/**
 * 新版本更新提示
 * Created by caojing on 16/8/3.
 */
public class UpdateManager {

    private static final String TAG = UpdateManager.class.getSimpleName();

    public static final int WHAT = 1;

    private Context mContext;

    private boolean isHome;

    private static UpdateManager mInstance;
    private String updateMessage;

    public static UpdateManager getInstance() {
        if (mInstance == null) {
            mInstance = new UpdateManager();
        }
        return mInstance;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UpdateManager.WHAT:
                    String response = msg.getData().getString("response");
                    LogUtils.i(TAG, response);
                    try {
                        int localVersionCode = PackageUtils.getPackageVersionCode(mContext);
                        JSONObject responseJsonObject = new JSONObject(response);
                        Map<String, Object> responseMap = jsonToMap(responseJsonObject);
                        int versionCode = (int) responseMap.get("version_code");
                        if (versionCode > localVersionCode) {
                            createDialog(responseMap);
                        } else {
                            if (!isHome) {
                                ToastUtils.show(R.string.toast_is_lastest_version);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void createDialog(Map<String, Object> responseMap) {
        final String versionName = (String) responseMap.get("version_name");
        Object Message = responseMap.get("update_message");

        String s = Message.toString();
        if (s.equals("null")) {
            updateMessage = "修复若干bug";
        } else {
            updateMessage = s;
        }

        final String downURL = (String) responseMap.get("down_url");


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.UpdateDialog);
        View hintView = View.inflate(mContext, R.layout.dialog_update_hint, null);
        builder.setCancelable(false);
        builder.setView(hintView);
        final Dialog dialog = builder.create();
        dialog.show();
        //设置对话的宽高
        /*WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.height = App.screenHeight * 3 / 5;
        params.width = App.screenWidth * 4 / 5;
        dialog.getWindow().setAttributes(params);*/

        final TextView update_content = (TextView) hintView.findViewById(R.id.update_content);
        final TextView cancel = (TextView) hintView.findViewById(R.id.cancel);
        final TextView confirm = (TextView) hintView.findViewById(R.id.confirm);
        //设置textview可上下滑动
        update_content.setMovementMethod(new ScrollingMovementMethod());

        update_content.setText(updateMessage);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidationUtils.isUrl(downURL)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downURL));
                    mContext.startActivity(intent);
                } else {
                    ToastUtils.show(R.string.toast_download_not_correct);
                }
            }
        });
    }

    /**
     * 检测服务端APP的版本信息
     */
    public void checkServerAppVersionInfo(Context context, boolean isHome) {
        if (!NetUtil.isNetworkAvailable(context)) {
            ToastUtils.show(R.string.toast_network_not_avaliable);
            return;
        }
        this.mContext = context;
        this.isHome = isHome;

        new CheckUpdateThread().start();
    }

    private class CheckUpdateThread extends Thread {

        public CheckUpdateThread() {
            super();
        }

        @Override
        public void run() {
            super.run();

            /*Map<String, Object> Map = new HashMap<>();
            Map.put("type", 1);
            String s = new Gson().toJson(Map);
*//******************************修改后台地址**************************************//*
            String response = post(App.HOST + "rapiv2/Common/checkUpdate", s);

            if (response != null) {
                Message msg = new Message();
                msg.what = WHAT;
                Bundle bundle = new Bundle();
                bundle.putString("response", response);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }*/
        }
    }

    private static String post(String url, String content) {
        HttpURLConnection conn = null;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);

            String data = content;
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                String response = getStringFromInputStream(is);
                return response;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                    throw new NetworkErrorException("response status is " + responseCode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}

package com.kacyber.pos.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.MyFirebaseInstanceIDService;
import com.kacyber.pos.MyFirebaseMessagingService;
import com.kacyber.pos.R;
import com.kacyber.pos.entity.User;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.LoginAccountHelper;
import com.kacyber.pos.util.common.ToastUtils;
import com.kacyber.pos.util.common.ValidationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by MZY on 2017/9/30.
 */

public class LoginActivity extends BaseActivity implements ApiResponse {

    Call<JsonObject> loginData;
    @BindView(R.id.et_account)
    EditText mAccountEditText;
    @BindView(R.id.et_password)
    EditText mPasswordEditText;
    @BindView(R.id.eyepswd)
    LinearLayout eyepswd;
    private boolean isShow;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_login, R.id.eyepswd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (checkValid()) {
                    String account = mAccountEditText.getText().toString().trim();
                    String password = mPasswordEditText.getText().toString().trim();
                    loginApi(account, password, true);
                }
                break;
            case R.id.eyepswd:
                showAddhide();
                break;
        }
    }

    private void showAddhide() {
        if (isShow) {
            isShow = false;
            mPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            isShow = true;
            mPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    private void loginApi(String name, String password, boolean isLoader) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", name);
            jsonObject.put("password", password);
            jsonObject.put("deviceToken", FirebaseInstanceId.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        loginData = apiInterface.loginApi(requestBody);
        apiHitAndHandle.makeApiCall(loginData, true, this);
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean hideNavigationIcon() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
       /* mAccountEditText.setText(account);
        mPasswordEditText.setText(password);*/
    }

    private boolean checkValid() {

        String account = mAccountEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        boolean isAccountEmpty = TextUtils.isEmpty(account);
        boolean isPasswordEmpty = TextUtils.isEmpty(password);
        boolean isName = ValidationUtils.isName(account);
        boolean isEmail = ValidationUtils.isEmail(account);
        boolean isPassword = ValidationUtils.isPassword(password);
        if (isAccountEmpty) {
            ToastUtils.show(R.string.toast_account_is_empty);
        } else if (isPasswordEmpty) {
            ToastUtils.show(R.string.toast_password_is_empty);
        } else if (!isName && !isEmail) {
            ToastUtils.show(R.string.toast_account_error);
        } else if (!isPassword) {
            ToastUtils.show(R.string.toast_password_error);
        }
        return !isAccountEmpty && !isPasswordEmpty && (isName || isEmail) && isPassword;
    }

    @Override
    public void onSuccess(Call call, Object object) {
        try {
            Log.e("response>>", object.toString());
            JSONObject jsonObject = new JSONObject(object + "");
            String status = jsonObject.getString("status");
            String message = jsonObject.optString("message");
            if (status.equalsIgnoreCase("1")) {
                String token = jsonObject.getString("token");
                int userRole = jsonObject.optInt("role");
                String userData = jsonObject.optJSONObject("userDetail").toString();
                User obj = new Gson().fromJson(userData, User.class);
                GlobalStore.saveUserRoll(getApplicationContext(), userRole);
                GlobalStore.saveUser(LoginActivity.this, obj);
                GlobalStore.saveToken(LoginActivity.this, token);
                GlobalStore.saveLoginBool(LoginActivity.this, true);
                LoginAccountHelper.launchToMainActivity(LoginActivity.this);
                finish();
            } else {
                oneButtonDialogone(LoginActivity.this, message, getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        showToast(errorMessage);
    }
}

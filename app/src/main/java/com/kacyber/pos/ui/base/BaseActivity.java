package com.kacyber.pos.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.kacyber.pos.R;
import com.kacyber.pos.retrofitManager.ApiClient;
import com.kacyber.pos.retrofitManager.ApiHitAndHandle;
import com.kacyber.pos.retrofitManager.ApiInterface;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.util.ConnectivityReceiver;
import com.kacyber.pos.util.PrefStore;
import com.kacyber.pos.util.common.LogUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;

/**
 * 基类
 * Created by mzy on 2017/6/1.
 */

public abstract class BaseActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    protected final String TAG = this.getClass().getSimpleName();
    public static ApiHitAndHandle apiHitAndHandle;
    public static ApiInterface apiInterface;

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @Nullable
    @BindView(R.id.toolbar_title)
    public TextView mTitleView;

    public Unbinder unbinder;
    private PermCallback permCallback;
    private int reqCode;
    private int testing = 0;
    public PrefStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "BaseActivity-->onCreate()");
        Fabric.with(this, new Crashlytics());
        setContentView(getLayoutResID());

        unbinder = ButterKnife.bind(this);
        store = new PrefStore(getApplicationContext());
        apiHitAndHandle = ApiHitAndHandle.getInstance(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        steepStatusBar(useSteepStatusBar());
        setRequestedOrientation(forbidScreenRotate() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : getRequestedOrientation());
        if (mToolbar != null && mTitleView != null) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
            }
            setHideNavigationIcon(hideNavigationIcon());
            setHideToolbar(hideToolbar());
        }
        initView(savedInstanceState);
        checkConnection();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        isInternetConnected(isConnected);
    }


    public void isInternetConnected(boolean isConnected) {
        // Change status according to boolean value
        if (!isConnected)
            internetAlertDialog();
    }

    public String getDeviceId() {
        final String deviceId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null) {
            return android.os.Build.MODEL + "_" + deviceId;
        } else {
            return android.os.Build.SERIAL + "_" + deviceId;
        }
    }

    public String formatDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String pattern = "EEE d";
        // pattern += "'" + getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH)) + "'";
        pattern += " MMM ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    private void internetAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setMessage("No internet connection,Do you want to connect?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        builder.show();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            //  Toast.makeText(this, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected abstract   @LayoutRes   int getLayoutResID();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    // ============== 抽象方法，子类实现 ==============

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar(boolean steep) {
        if (steep) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 设置透明状态栏,这样才能让 ContentView 向上
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * [是否使用沉浸状态栏，默认false]
     */
    protected boolean useSteepStatusBar() {
        return false;
    }

    /**
     * [是否禁止屏幕旋转，默认true]
     */
    protected boolean forbidScreenRotate() {
        return true;
    }

    /**
     * [隐藏Toolbar的NavigationIcon，默认false]
     */
    protected boolean hideNavigationIcon() {
        return false;
    }

    /**
     * [设置隐藏NavigationIcon]
     *
     * @param hide
     */
    protected void setHideNavigationIcon(boolean hide) {
        if (hide) {
            if (mToolbar != null) {
                mToolbar.setNavigationIcon(null);
            }
        }
    }

    /**
     * [是否隐藏Toolbar，默认false]
     */
    protected boolean hideToolbar() {
        return false;
    }

    /**
     * [设置隐藏ToolBar]
     *
     * @param hide
     */
    protected void setHideToolbar(boolean hide) {
        if (mToolbar != null) {
            mToolbar.setVisibility(hide ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        apiHitAndHandle = ApiHitAndHandle.getInstance(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        LogUtils.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy()");
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        isInternetConnected(isConnected);
    }

    public void imageLoader(Context context, String imagePath, ImageView imageView) {
        if (!imagePath.isEmpty()) {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
        }
    }

    public Dialog oneButtonDialogone(Context c, String msg, String buttonTxt, DialogInterface.OnClickListener clickListener) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(c);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setNeutralButton(buttonTxt, clickListener);
        return dialog.create();
    }

    public static String parseDateToddMMyyyy(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        String str = null;
        str = outputFormat.format(calendar.getTime());
        return str;
    }


    public boolean checkPermissions(String[] perms, int requestCode, PermCallback permCallback) {
        this.permCallback = permCallback;
        this.reqCode = requestCode;
        ArrayList<String> permsArray = new ArrayList<>();
        boolean hasPerms = true;
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                permsArray.add(perm);
                hasPerms = false;
            }
        }
        if (!hasPerms) {
            String[] permsString = new String[permsArray.size()];
            for (int i = 0; i < permsArray.size(); i++) {
                permsString[i] = permsArray.get(i);
            }
            ActivityCompat.requestPermissions(BaseActivity.this, permsString, 99);
            return false;
        } else
            return true;
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permGrantedBool = false;
        switch (requestCode) {
            case 99:
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        showToast(getString(R.string.not_sufficient_permissions, getString(R.string.app_name)));
                        permGrantedBool = false;
                        break;
                    } else {
                        permGrantedBool = true;
                    }
                }
                if (permCallback != null) {
                    if (permGrantedBool) {
                        permCallback.permGranted(reqCode);
                    } else {
                        permCallback.permDenied(reqCode);
                    }
                }
                break;
        }
    }

    public interface PermCallback {
        void permGranted(int resultCode);

        void permDenied(int resultCode);
    }
}

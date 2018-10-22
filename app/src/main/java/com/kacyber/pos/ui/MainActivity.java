package com.kacyber.pos.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.entity.User;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.ui.base.BaseActivity.PermCallback;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.CommonUtils;
import com.kacyber.pos.util.common.ToastUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 *
 */
public class MainActivity extends BaseActivity implements PermCallback {

    @BindView(R.id.iv_setting)
    ImageView settingImageView;
    @BindView(R.id.iv_notifications)
    ImageView notificationsImageView;
    @BindView(R.id.ll_scan)
    LinearLayout scanLinearLayout;
    @BindView(R.id.ll_manual)
    LinearLayout manualLinearLayout;
    @BindView(R.id.ll_quick_sale)
    LinearLayout quickSaleLinearLayout;
    @BindView(R.id.tv_version_name)
    TextView versionNameTextView;
    @BindView(R.id.tv_version_code)
    TextView versionCodeTextView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private int memberId = 10;
    private TextView txtName, txtWebsite;
    private ImageView imgProfile;
    private ImageView imgNavHeaderBg;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_setting, R.id.iv_notifications, R.id.ll_scan, R.id.ll_manual, R.id.ll_quick_sale, R.id.ll_history})
    public void onClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_setting:
                drawer.openDrawer(Gravity.LEFT);
                setUserData();
                break;
            case R.id.iv_notifications:
                ToastUtils.show("Not activated for this user!");
                break;
            case R.id.ll_scan:
                if (checkPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        123, new PermCallback() {
                            @Override
                            public void permGranted(int resultCode) {
                                gotoScan();
                            }

                            @Override
                            public void permDenied(int resultCode) {

                            }
                        })) {
                    gotoScan();
                }

                break;
            case R.id.ll_manual:
                intent = new Intent(this, ManualActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
            case R.id.ll_quick_sale:
                intent = new Intent(this, QuickSaleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
            case R.id.ll_history:
                intent = new Intent(this, ReportsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }

    private void gotoScan() {
        Intent intent;
        intent = new Intent(MainActivity.this, ScanTicketsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        scanLinearLayout.setClickable(false);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawer.closeDrawers();
                switch (menuItem.getItemId()) {
                    /*case R.id.notification:
                        showToast("Coming soon !!");
                        //   startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        break;*/
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case R.id.logout:
                        logoutOut();
                        break;
                    default:
                }
              /*  if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);*/
                return false;
            }
        });
    }

    private void setUserData() {
        try {
            User user = GlobalStore.getUser(getApplicationContext());
            if (user != null) {
                txtName.setText("" + user.fullName);
                txtWebsite.setText(user.phone + "");
                if (user.profilePic != null && !user.profilePic.isEmpty()) {
                    Picasso.with(this).load(user.profilePic).placeholder(R.drawable.icon_personal).resize(80, 80)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.drawable.ic_logo).into(imgProfile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logoutOut() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        logoutApi();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void logoutApi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", GlobalStore.getToken(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> logoutData = apiInterface.logoutApi(requestBody);
        apiHitAndHandle.makeApiCall(logoutData, new ApiResponse() {
            @Override
            public void onSuccess(Call call, Object object) {
                if (object != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(object.toString());
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("1")) {
                            GlobalStore.saveLoginBool(getApplicationContext(), false);
                            LoginActivity.start(getApplicationContext());
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
            }
        });
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean hideNavigationIcon() {
        return false;
    }

    @Override
    protected boolean useSteepStatusBar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        txtWebsite = navHeader.findViewById(R.id.website);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        imgProfile = navHeader.findViewById(R.id.img_profile);
        setUpNavigationView();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (checkPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 456, this)) {
            final String deviceVersionName = "";
            final String appVersionName = "V" + CommonUtils.getAppVersion(this);
            versionNameTextView.setText(String.format("%s(%s)", deviceVersionName, appVersionName));
            versionCodeTextView.setText(getDeviceId());
       }
    }

    private void callIntent() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + versionCodeTextView.getText().toString()));
        PackageManager packageManager = getPackageManager();
        if (callIntent.resolveActivity(packageManager) != null) {
            startActivity(callIntent);
        } else {
            Log.d("Call=", "Cannot handle this intent");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        unbinder = ButterKnife.bind(this);
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    // ----------------------- 处理灯灭发出的广播 ---------------------------
    private static final String BROADCAST_ACTION_NAME = "com.barcode.sendBroadcast";

    private void registerReceiver() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION_NAME);
        registerReceiver(receiver, intentFilter);
    }

    // send scan broadcast
    private void sendScanIntent() {
        Intent intent = new Intent();
        intent.setAction("com.barcode.sendBroadcastScan");
        sendBroadcast(intent);
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
    };

    @Override
    public void permGranted(int resultCode) {
        if (resultCode == 456) {
            versionCodeTextView.setText(getDeviceId());
        } else {
            callIntent();
        }
    }

    @Override
    public void permDenied(int resultCode) {

    }
}

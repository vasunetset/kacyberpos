package com.kacyber.pos.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.Const;
import com.kacyber.pos.util.GetVersionCode;
import com.kacyber.pos.util.common.NetUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kacyber.pos.util.common.CommonUtils.getAppVersion;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.versionCode)
    TextView mVersionCode;
    private Intent intent;
    private int version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView(R.layout.activity_setting);*/
        ButterKnife.bind(this);
        //setupActionBar();
        mVersionCode.setText(String.format("Version %s", getAppVersion(getApplicationContext())));
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    private void setupActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Settings");
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @OnClick({R.id.NotificationRL, R.id.cleaCacheRL, R.id.privacyRL, R.id.privacyPolicyRL, R.id.termsOfsericeRL, R.id.helpRL, R.id.checkUpdateRL, R.id.signOutBT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.NotificationRL:
                break;
            case R.id.cleaCacheRL:
                clearApplicationData();
                showToast("Clear cache successfully");
                break;
            case R.id.privacyRL:

                break;
            case R.id.privacyPolicyRL:
                intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", Const.PRIVACY_POLICY_URL);
                intent.putExtra("urltype", "pdf");
                intent.putExtra("title", "Privacy policy");
                startActivity(intent);
                break;
            case R.id.termsOfsericeRL:
                intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", Const.TERM_OF_SERVICE_URL);
                intent.putExtra("urltype", "pdf");
                intent.putExtra("title", "Terms of service");
                startActivity(intent);
                break;
            case R.id.helpRL:
                intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", Const.HELP_FAQ_URL);
                intent.putExtra("urltype", "webUrl");
                intent.putExtra("title", "Help & FAQs");
                startActivity(intent);
                break;
            case R.id.checkUpdateRL:
                if (NetUtil.isNetworkAvailable(getApplicationContext())) {
                    String currentVersion = null;
                    try {
                        currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    new GetVersionCode(this, currentVersion).execute();
                }
                break;
            case R.id.signOutBT:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (deleteDir(dir)) {
                showToast("Cache clear successfully");
            } else {
                showToast("No cache found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }*/


    public void clearApplicationData() {

        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }


        return dir.delete();
    }
}

package com.kacyber.pos.ui;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import butterknife.BindView;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.GlobalStore;

/**
 * Created by mzy on 2018/2/28.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash)
    LinearLayout mSplash;
    Boolean loginBool;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loginBool= GlobalStore.getLoginBool(SplashActivity.this);
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(500);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (loginBool){
                    MainActivity.start(SplashActivity.this);
                }else {
                    LoginActivity.start(SplashActivity.this);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplash.startAnimation(aa);
    }
}

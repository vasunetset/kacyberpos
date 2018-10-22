// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SplashActivity target;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mSplash = Utils.findRequiredViewAsType(source, R.id.splash, "field 'mSplash'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSplash = null;

    super.unbind();
  }
}

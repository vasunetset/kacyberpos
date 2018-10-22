// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseActivity_ViewBinding implements Unbinder {
  private BaseActivity target;

  @UiThread
  public BaseActivity_ViewBinding(BaseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseActivity_ViewBinding(BaseActivity target, View source) {
    this.target = target;

    target.mToolbar = Utils.findOptionalViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mTitleView = Utils.findOptionalViewAsType(source, R.id.toolbar_title, "field 'mTitleView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mTitleView = null;
  }
}

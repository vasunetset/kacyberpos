// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CityListActivity_ViewBinding extends BaseActivity_ViewBinding {
  private CityListActivity target;

  @UiThread
  public CityListActivity_ViewBinding(CityListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CityListActivity_ViewBinding(CityListActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTitleView = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'mTitleView'", TextView.class);
  }

  @Override
  public void unbind() {
    CityListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitleView = null;

    super.unbind();
  }
}

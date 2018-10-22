// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ManualActivity_ViewBinding extends BaseActivity_ViewBinding {
  private ManualActivity target;

  @UiThread
  public ManualActivity_ViewBinding(ManualActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ManualActivity_ViewBinding(ManualActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mCodeEditText = Utils.findRequiredViewAsType(source, R.id.et_code, "field 'mCodeEditText'", EditText.class);
  }

  @Override
  public void unbind() {
    ManualActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mCodeEditText = null;

    super.unbind();
  }
}

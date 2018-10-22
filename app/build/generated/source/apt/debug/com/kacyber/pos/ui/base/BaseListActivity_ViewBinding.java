// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui.base;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseListActivity_ViewBinding extends BaseActivity_ViewBinding {
  private BaseListActivity target;

  @UiThread
  public BaseListActivity_ViewBinding(BaseListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseListActivity_ViewBinding(BaseListActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    BaseListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;

    super.unbind();
  }
}

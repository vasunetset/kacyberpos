// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseListFragment_ViewBinding implements Unbinder {
  private BaseListFragment target;

  @UiThread
  public BaseListFragment_ViewBinding(BaseListFragment target, View source) {
    this.target = target;

    target.mRefreshLayout = Utils.findRequiredViewAsType(source, R.id.refresh_layout, "field 'mRefreshLayout'", SwipeRefreshLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRefreshLayout = null;
    target.mRecyclerView = null;
  }
}

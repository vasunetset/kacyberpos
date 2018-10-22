// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllServicesFragment_ViewBinding implements Unbinder {
  private AllServicesFragment target;

  private View view2131296351;

  private View view2131296691;

  @UiThread
  public AllServicesFragment_ViewBinding(final AllServicesFragment target, View source) {
    this.target = target;

    View view;
    target.servicesRV = Utils.findRequiredViewAsType(source, R.id.servicesRV, "field 'servicesRV'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.cancel, "field 'cancel' and method 'onViewClicked'");
    target.cancel = Utils.castView(view, R.id.cancel, "field 'cancel'", ImageView.class);
    view2131296351 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.selectAllTV, "field 'selectAllTV' and method 'onViewClicked'");
    target.selectAllTV = Utils.castView(view, R.id.selectAllTV, "field 'selectAllTV'", TextView.class);
    view2131296691 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AllServicesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.servicesRV = null;
    target.cancel = null;
    target.header = null;
    target.selectAllTV = null;

    view2131296351.setOnClickListener(null);
    view2131296351 = null;
    view2131296691.setOnClickListener(null);
    view2131296691 = null;
  }
}

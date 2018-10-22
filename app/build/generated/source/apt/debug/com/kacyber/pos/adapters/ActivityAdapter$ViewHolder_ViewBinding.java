// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivityAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ActivityAdapter.ViewHolder target;

  @UiThread
  public ActivityAdapter$ViewHolder_ViewBinding(ActivityAdapter.ViewHolder target, View source) {
    this.target = target;

    target.roteTV = Utils.findRequiredViewAsType(source, R.id.roteTV, "field 'roteTV'", TextView.class);
    target.bookedByNameTV = Utils.findRequiredViewAsType(source, R.id.bookedByNameTV, "field 'bookedByNameTV'", TextView.class);
    target.bookedFromTV = Utils.findRequiredViewAsType(source, R.id.bookedFromTV, "field 'bookedFromTV'", TextView.class);
    target.priceTV = Utils.findRequiredViewAsType(source, R.id.priceTV, "field 'priceTV'", TextView.class);
    target.busIcon = Utils.findRequiredViewAsType(source, R.id.busIcon, "field 'busIcon'", ImageView.class);
    target.busName = Utils.findRequiredViewAsType(source, R.id.busName, "field 'busName'", TextView.class);
    target.statusTV = Utils.findRequiredViewAsType(source, R.id.statusTV, "field 'statusTV'", TextView.class);
    target.parentLL = Utils.findRequiredViewAsType(source, R.id.parentLL, "field 'parentLL'", LinearLayout.class);
    target.dateLayoutRL = Utils.findRequiredViewAsType(source, R.id.dateLayoutRL, "field 'dateLayoutRL'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivityAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.roteTV = null;
    target.bookedByNameTV = null;
    target.bookedFromTV = null;
    target.priceTV = null;
    target.busIcon = null;
    target.busName = null;
    target.statusTV = null;
    target.parentLL = null;
    target.dateLayoutRL = null;
  }
}

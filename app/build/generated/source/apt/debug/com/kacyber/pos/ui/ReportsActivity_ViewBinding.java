// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReportsActivity_ViewBinding extends BaseActivity_ViewBinding {
  private ReportsActivity target;

  private View view2131296505;

  private View view2131296292;

  private View view2131296762;

  private View view2131296695;

  private View view2131296726;

  private View view2131296446;

  private View view2131296313;

  private View view2131296672;

  @UiThread
  public ReportsActivity_ViewBinding(ReportsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReportsActivity_ViewBinding(final ReportsActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.isightTV, "field 'isightTV' and method 'onViewClicked'");
    target.isightTV = Utils.castView(view, R.id.isightTV, "field 'isightTV'", TextView.class);
    view2131296505 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.activityTV, "field 'activityTV' and method 'onViewClicked'");
    target.activityTV = Utils.castView(view, R.id.activityTV, "field 'activityTV'", TextView.class);
    view2131296292 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.todayTV, "field 'todayTV' and method 'onViewClicked'");
    target.todayTV = Utils.castView(view, R.id.todayTV, "field 'todayTV'", TextView.class);
    view2131296762 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.serviceTV, "field 'serviceTV' and method 'onViewClicked'");
    target.serviceTV = Utils.castView(view, R.id.serviceTV, "field 'serviceTV'", TextView.class);
    view2131296695 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.reportRL = Utils.findRequiredViewAsType(source, R.id.reportRL, "field 'reportRL'", RelativeLayout.class);
    target.searchRL = Utils.findRequiredViewAsType(source, R.id.searchRL, "field 'searchRL'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.startDateTV, "field 'startDateTV' and method 'onViewClicked'");
    target.startDateTV = Utils.castView(view, R.id.startDateTV, "field 'startDateTV'", TextView.class);
    view2131296726 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.endDateTV, "field 'endDateTV' and method 'onViewClicked'");
    target.endDateTV = Utils.castView(view, R.id.endDateTV, "field 'endDateTV'", TextView.class);
    view2131296446 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.activityRC = Utils.findRequiredViewAsType(source, R.id.activityRC, "field 'activityRC'", RecyclerView.class);
    target.totalTicketIssueTV = Utils.findRequiredViewAsType(source, R.id.totalTicketIssueTV, "field 'totalTicketIssueTV'", TextView.class);
    target.bookedSeatTV = Utils.findRequiredViewAsType(source, R.id.bookedSeatTV, "field 'bookedSeatTV'", TextView.class);
    target.totalCancelTicketTV = Utils.findRequiredViewAsType(source, R.id.totalCancelTicketTV, "field 'totalCancelTicketTV'", TextView.class);
    target.priceCancelAmtTV = Utils.findRequiredViewAsType(source, R.id.priceCancelAmtTV, "field 'priceCancelAmtTV'", TextView.class);
    target.totalAmountTV = Utils.findRequiredViewAsType(source, R.id.totalAmountTV, "field 'totalAmountTV'", TextView.class);
    target.printedTotalTV = Utils.findRequiredViewAsType(source, R.id.printedTotalTV, "field 'printedTotalTV'", TextView.class);
    target.verifiedTotalTV = Utils.findRequiredViewAsType(source, R.id.verifiedTotalTV, "field 'verifiedTotalTV'", TextView.class);
    view = Utils.findRequiredView(source, R.id.backIV, "field 'backIV' and method 'onViewClicked'");
    target.backIV = Utils.castView(view, R.id.backIV, "field 'backIV'", ImageView.class);
    view2131296313 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.searchBT, "method 'onViewClicked'");
    view2131296672 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    ReportsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.isightTV = null;
    target.activityTV = null;
    target.todayTV = null;
    target.serviceTV = null;
    target.reportRL = null;
    target.searchRL = null;
    target.startDateTV = null;
    target.endDateTV = null;
    target.activityRC = null;
    target.totalTicketIssueTV = null;
    target.bookedSeatTV = null;
    target.totalCancelTicketTV = null;
    target.priceCancelAmtTV = null;
    target.totalAmountTV = null;
    target.printedTotalTV = null;
    target.verifiedTotalTV = null;
    target.backIV = null;
    target.header = null;

    view2131296505.setOnClickListener(null);
    view2131296505 = null;
    view2131296292.setOnClickListener(null);
    view2131296292 = null;
    view2131296762.setOnClickListener(null);
    view2131296762 = null;
    view2131296695.setOnClickListener(null);
    view2131296695 = null;
    view2131296726.setOnClickListener(null);
    view2131296726 = null;
    view2131296446.setOnClickListener(null);
    view2131296446 = null;
    view2131296313.setOnClickListener(null);
    view2131296313 = null;
    view2131296672.setOnClickListener(null);
    view2131296672 = null;

    super.unbind();
  }
}

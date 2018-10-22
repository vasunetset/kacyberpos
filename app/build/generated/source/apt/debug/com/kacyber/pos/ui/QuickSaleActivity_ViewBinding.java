// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuickSaleActivity_ViewBinding extends BaseActivity_ViewBinding {
  private QuickSaleActivity target;

  private View view2131296620;

  private View view2131296621;

  private View view2131296401;

  private View view2131296332;

  private View view2131296341;

  private View view2131296645;

  @UiThread
  public QuickSaleActivity_ViewBinding(QuickSaleActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public QuickSaleActivity_ViewBinding(final QuickSaleActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.place_from, "field 'placeFrom' and method 'onViewClicked'");
    target.placeFrom = Utils.castView(view, R.id.place_from, "field 'placeFrom'", EditText.class);
    view2131296620 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.place_to, "field 'placeTo' and method 'onViewClicked'");
    target.placeTo = Utils.castView(view, R.id.place_to, "field 'placeTo'", EditText.class);
    view2131296621 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.date, "field 'date' and method 'onViewClicked'");
    target.date = Utils.castView(view, R.id.date, "field 'date'", TextView.class);
    view2131296401 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_change, "method 'onViewClicked'");
    view2131296332 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_search, "method 'onViewClicked'");
    view2131296341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.recent, "method 'onViewClicked'");
    view2131296645 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    QuickSaleActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.placeFrom = null;
    target.placeTo = null;
    target.date = null;

    view2131296620.setOnClickListener(null);
    view2131296620 = null;
    view2131296621.setOnClickListener(null);
    view2131296621 = null;
    view2131296401.setOnClickListener(null);
    view2131296401 = null;
    view2131296332.setOnClickListener(null);
    view2131296332 = null;
    view2131296341.setOnClickListener(null);
    view2131296341 = null;
    view2131296645.setOnClickListener(null);
    view2131296645 = null;

    super.unbind();
  }
}

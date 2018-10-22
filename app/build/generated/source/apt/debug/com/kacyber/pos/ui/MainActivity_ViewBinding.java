// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding extends BaseActivity_ViewBinding {
  private MainActivity target;

  private View view2131296515;

  private View view2131296512;

  private View view2131296532;

  private View view2131296530;

  private View view2131296531;

  private View view2131296529;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_setting, "field 'settingImageView' and method 'onClicked'");
    target.settingImageView = Utils.castView(view, R.id.iv_setting, "field 'settingImageView'", ImageView.class);
    view2131296515 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_notifications, "field 'notificationsImageView' and method 'onClicked'");
    target.notificationsImageView = Utils.castView(view, R.id.iv_notifications, "field 'notificationsImageView'", ImageView.class);
    view2131296512 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_scan, "field 'scanLinearLayout' and method 'onClicked'");
    target.scanLinearLayout = Utils.castView(view, R.id.ll_scan, "field 'scanLinearLayout'", LinearLayout.class);
    view2131296532 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_manual, "field 'manualLinearLayout' and method 'onClicked'");
    target.manualLinearLayout = Utils.castView(view, R.id.ll_manual, "field 'manualLinearLayout'", LinearLayout.class);
    view2131296530 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_quick_sale, "field 'quickSaleLinearLayout' and method 'onClicked'");
    target.quickSaleLinearLayout = Utils.castView(view, R.id.ll_quick_sale, "field 'quickSaleLinearLayout'", LinearLayout.class);
    view2131296531 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked(p0);
      }
    });
    target.versionNameTextView = Utils.findRequiredViewAsType(source, R.id.tv_version_name, "field 'versionNameTextView'", TextView.class);
    target.versionCodeTextView = Utils.findRequiredViewAsType(source, R.id.tv_version_code, "field 'versionCodeTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ll_history, "method 'onClicked'");
    view2131296529 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.settingImageView = null;
    target.notificationsImageView = null;
    target.scanLinearLayout = null;
    target.manualLinearLayout = null;
    target.quickSaleLinearLayout = null;
    target.versionNameTextView = null;
    target.versionCodeTextView = null;

    view2131296515.setOnClickListener(null);
    view2131296515 = null;
    view2131296512.setOnClickListener(null);
    view2131296512 = null;
    view2131296532.setOnClickListener(null);
    view2131296532 = null;
    view2131296530.setOnClickListener(null);
    view2131296530 = null;
    view2131296531.setOnClickListener(null);
    view2131296531 = null;
    view2131296529.setOnClickListener(null);
    view2131296529 = null;

    super.unbind();
  }
}

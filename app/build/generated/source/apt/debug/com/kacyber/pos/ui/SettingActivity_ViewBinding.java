// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SettingActivity target;

  private View view2131296264;

  private View view2131296369;

  private View view2131296634;

  private View view2131296633;

  private View view2131296738;

  private View view2131296482;

  private View view2131296364;

  private View view2131296702;

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingActivity_ViewBinding(final SettingActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mVersionCode = Utils.findRequiredViewAsType(source, R.id.versionCode, "field 'mVersionCode'", TextView.class);
    view = Utils.findRequiredView(source, R.id.NotificationRL, "method 'onClick'");
    view2131296264 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.cleaCacheRL, "method 'onClick'");
    view2131296369 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.privacyRL, "method 'onClick'");
    view2131296634 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.privacyPolicyRL, "method 'onClick'");
    view2131296633 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.termsOfsericeRL, "method 'onClick'");
    view2131296738 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.helpRL, "method 'onClick'");
    view2131296482 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkUpdateRL, "method 'onClick'");
    view2131296364 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.signOutBT, "method 'onClick'");
    view2131296702 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    SettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mVersionCode = null;

    view2131296264.setOnClickListener(null);
    view2131296264 = null;
    view2131296369.setOnClickListener(null);
    view2131296369 = null;
    view2131296634.setOnClickListener(null);
    view2131296634 = null;
    view2131296633.setOnClickListener(null);
    view2131296633 = null;
    view2131296738.setOnClickListener(null);
    view2131296738 = null;
    view2131296482.setOnClickListener(null);
    view2131296482 = null;
    view2131296364.setOnClickListener(null);
    view2131296364 = null;
    view2131296702.setOnClickListener(null);
    view2131296702 = null;

    super.unbind();
  }
}

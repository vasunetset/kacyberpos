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

public class SearchResultsActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SearchResultsActivity target;

  @UiThread
  public SearchResultsActivity_ViewBinding(SearchResultsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchResultsActivity_ViewBinding(SearchResultsActivity target, View source) {
    super(target, source);

    this.target = target;

    target.mTitleView = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'mTitleView'", TextView.class);
  }

  @Override
  public void unbind() {
    SearchResultsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitleView = null;

    super.unbind();
  }
}

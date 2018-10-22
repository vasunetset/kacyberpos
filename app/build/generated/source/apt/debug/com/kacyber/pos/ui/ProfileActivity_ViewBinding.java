// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity_ViewBinding;
import com.rilixtech.CountryCodePicker;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileActivity_ViewBinding extends BaseActivity_ViewBinding {
  private ProfileActivity target;

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target, View source) {
    super(target, source);

    this.target = target;

    target.profileImage = Utils.findRequiredViewAsType(source, R.id.profileImage, "field 'profileImage'", CircleImageView.class);
    target.userNameTV = Utils.findRequiredViewAsType(source, R.id.userNameTV, "field 'userNameTV'", TextView.class);
    target.designationTV = Utils.findRequiredViewAsType(source, R.id.designationTV, "field 'designationTV'", TextView.class);
    target.firstNameET = Utils.findRequiredViewAsType(source, R.id.firstNameET, "field 'firstNameET'", EditText.class);
    target.lastNameET = Utils.findRequiredViewAsType(source, R.id.lastNameET, "field 'lastNameET'", EditText.class);
    target.countryCode = Utils.findRequiredViewAsType(source, R.id.countryCode, "field 'countryCode'", CountryCodePicker.class);
    target.phoneNoET = Utils.findRequiredViewAsType(source, R.id.phoneNoET, "field 'phoneNoET'", EditText.class);
    target.emailET = Utils.findRequiredViewAsType(source, R.id.emailET, "field 'emailET'", EditText.class);
  }

  @Override
  public void unbind() {
    ProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profileImage = null;
    target.userNameTV = null;
    target.designationTV = null;
    target.firstNameET = null;
    target.lastNameET = null;
    target.countryCode = null;
    target.phoneNoET = null;
    target.emailET = null;

    super.unbind();
  }
}

// Generated code from Butter Knife. Do not modify!
package com.kacyber.pos.ui;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
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

public class TicketActivity_ViewBinding extends BaseActivity_ViewBinding {
  private TicketActivity target;

  private View view2131296340;

  private View view2131296338;

  @UiThread
  public TicketActivity_ViewBinding(TicketActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TicketActivity_ViewBinding(final TicketActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.viewTicketLayout = Utils.findRequiredViewAsType(source, R.id.ll_ticket, "field 'viewTicketLayout'", LinearLayout.class);
    target.fromPlaceTextView = Utils.findRequiredViewAsType(source, R.id.tv_from_place, "field 'fromPlaceTextView'", TextView.class);
    target.toPlaceTextView = Utils.findRequiredViewAsType(source, R.id.to_place, "field 'toPlaceTextView'", TextView.class);
    target.departTimeTextView = Utils.findRequiredViewAsType(source, R.id.tv_depart_time, "field 'departTimeTextView'", TextView.class);
    target.arriveTimeTextView = Utils.findRequiredViewAsType(source, R.id.tv_arrive_time, "field 'arriveTimeTextView'", TextView.class);
    target.passengerNameTextView = Utils.findRequiredViewAsType(source, R.id.tv_passenger_name, "field 'passengerNameTextView'", TextView.class);
    target.seatNoTextView = Utils.findRequiredViewAsType(source, R.id.tv_seat_no, "field 'seatNoTextView'", TextView.class);
    target.priceTextView = Utils.findRequiredViewAsType(source, R.id.price, "field 'priceTextView'", TextView.class);
    target.pinNumberTextView = Utils.findRequiredViewAsType(source, R.id.tv_pin_number, "field 'pinNumberTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_print, "field 'printButton' and method 'onViewClicked'");
    target.printButton = Utils.castView(view, R.id.btn_print, "field 'printButton'", Button.class);
    view2131296340 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_needless, "field 'needlessButton' and method 'onViewClicked'");
    target.needlessButton = Utils.castView(view, R.id.btn_needless, "field 'needlessButton'", Button.class);
    view2131296338 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ticketTextView = Utils.findRequiredViewAsType(source, R.id.tv_ticket, "field 'ticketTextView'", TextView.class);
    target.coachPhoneNumberTextView = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'coachPhoneNumberTextView'", TextView.class);
    target.coachEmailAddressTextView = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'coachEmailAddressTextView'", TextView.class);
    target.ticketIdTextView = Utils.findRequiredViewAsType(source, R.id.tv_tid, "field 'ticketIdTextView'", TextView.class);
    target.mIcLogo = Utils.findRequiredViewAsType(source, R.id.ic_logo, "field 'mIcLogo'", ImageView.class);
    target.mTvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'mTvName'", TextView.class);
    target.mTvWebsite = Utils.findRequiredViewAsType(source, R.id.tv_website, "field 'mTvWebsite'", TextView.class);
    target.mRlCompanyInfo = Utils.findRequiredViewAsType(source, R.id.rl_company_info, "field 'mRlCompanyInfo'", RelativeLayout.class);
    target.mTvStart0 = Utils.findRequiredViewAsType(source, R.id.tv_start0, "field 'mTvStart0'", ImageView.class);
    target.mTvStart2 = Utils.findRequiredViewAsType(source, R.id.tv_start2, "field 'mTvStart2'", ImageView.class);
    target.mLlTermsAndConditions = Utils.findRequiredViewAsType(source, R.id.ll_terms_and_conditions, "field 'mLlTermsAndConditions'", LinearLayout.class);
    target.mIvQrCode = Utils.findRequiredViewAsType(source, R.id.iv_qr_code, "field 'mIvQrCode'", ImageView.class);
    target.mIvPrintPreview = Utils.findRequiredViewAsType(source, R.id.iv_print_preview, "field 'mIvPrintPreview'", ImageView.class);
    target.IdNumberTextView = Utils.findRequiredViewAsType(source, R.id.idNumberTextView, "field 'IdNumberTextView'", TextView.class);
    target.ClassTypeTextView = Utils.findRequiredViewAsType(source, R.id.classTypeTextView, "field 'ClassTypeTextView'", TextView.class);
    target.BusNumberTextView = Utils.findRequiredViewAsType(source, R.id.busNumberTextView, "field 'BusNumberTextView'", TextView.class);
    target.BusRouteTextView = Utils.findRequiredViewAsType(source, R.id.busRouteTextView, "field 'BusRouteTextView'", TextView.class);
    target.discountTextView = Utils.findRequiredViewAsType(source, R.id.discountTextView, "field 'discountTextView'", TextView.class);
    target.IssuingOfficerTextView = Utils.findRequiredViewAsType(source, R.id.issuingOfficerTextView, "field 'IssuingOfficerTextView'", TextView.class);
    target.BookingDateTextView = Utils.findRequiredViewAsType(source, R.id.bookingDateTextView, "field 'BookingDateTextView'", TextView.class);
    target.CodeImageView = Utils.findRequiredViewAsType(source, R.id.codeImageView, "field 'CodeImageView'", ImageView.class);
    target.printButtonLL = Utils.findRequiredViewAsType(source, R.id.printButtonLL, "field 'printButtonLL'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    TicketActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewTicketLayout = null;
    target.fromPlaceTextView = null;
    target.toPlaceTextView = null;
    target.departTimeTextView = null;
    target.arriveTimeTextView = null;
    target.passengerNameTextView = null;
    target.seatNoTextView = null;
    target.priceTextView = null;
    target.pinNumberTextView = null;
    target.printButton = null;
    target.needlessButton = null;
    target.ticketTextView = null;
    target.coachPhoneNumberTextView = null;
    target.coachEmailAddressTextView = null;
    target.ticketIdTextView = null;
    target.mIcLogo = null;
    target.mTvName = null;
    target.mTvWebsite = null;
    target.mRlCompanyInfo = null;
    target.mTvStart0 = null;
    target.mTvStart2 = null;
    target.mLlTermsAndConditions = null;
    target.mIvQrCode = null;
    target.mIvPrintPreview = null;
    target.IdNumberTextView = null;
    target.ClassTypeTextView = null;
    target.BusNumberTextView = null;
    target.BusRouteTextView = null;
    target.discountTextView = null;
    target.IssuingOfficerTextView = null;
    target.BookingDateTextView = null;
    target.CodeImageView = null;
    target.printButtonLL = null;

    view2131296340.setOnClickListener(null);
    view2131296340 = null;
    view2131296338.setOnClickListener(null);
    view2131296338 = null;

    super.unbind();
  }
}

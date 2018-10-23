package com.kacyber.pos.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.devices.DeviceManager;
import com.kacyber.pos.entity.QuickSale;
import com.kacyber.pos.entity.Ticket;
import com.kacyber.pos.entity.response.TicketInfoRes;
import com.kacyber.pos.net.RetrofitService;
import com.kacyber.pos.net.SimpleSubscriber;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.DialogHelper;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.BitmapUtils;
import com.kacyber.pos.util.common.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class TicketActivity extends BaseActivity {
    @BindView(R.id.ll_ticket)
    LinearLayout viewTicketLayout;
    @BindView(R.id.tv_from_place)
    TextView fromPlaceTextView;
    @BindView(R.id.to_place)
    TextView toPlaceTextView;
    @BindView(R.id.tv_depart_time)
    TextView departTimeTextView;
    @BindView(R.id.tv_arrive_time)
    TextView arriveTimeTextView;
    @BindView(R.id.tv_passenger_name)
    TextView passengerNameTextView;
    @BindView(R.id.tv_seat_no)
    TextView seatNoTextView;
    @BindView(R.id.price)
    TextView priceTextView;
    @BindView(R.id.tv_pin_number)
    TextView pinNumberTextView;
    @BindView(R.id.btn_print)
    Button printButton;
    @BindView(R.id.btn_needless)
    Button needlessButton;
    @BindView(R.id.tv_ticket)
    TextView ticketTextView;
    @BindView(R.id.tv_phone)
    TextView coachPhoneNumberTextView;
    @BindView(R.id.tv_email)
    TextView coachEmailAddressTextView;
    @BindView(R.id.tv_tid)
    TextView ticketIdTextView;
    @BindView(R.id.ic_logo)
    ImageView mIcLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_website)
    TextView mTvWebsite;
    @BindView(R.id.rl_company_info)
    RelativeLayout mRlCompanyInfo;
    @BindView(R.id.tv_start0)
    ImageView mTvStart0;
    @BindView(R.id.tv_start2)
    ImageView mTvStart2;
    @BindView(R.id.ll_terms_and_conditions)
    LinearLayout mLlTermsAndConditions;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.iv_print_preview)
    ImageView mIvPrintPreview;
    @BindView(R.id.idNumberTextView)
    TextView IdNumberTextView;
    @BindView(R.id.classTypeTextView)
    TextView ClassTypeTextView;
    @BindView(R.id.busNumberTextView)
    TextView BusNumberTextView;
    @BindView(R.id.busRouteTextView)
    TextView BusRouteTextView;
    @BindView(R.id.discountTextView)
    TextView discountTextView;
    @BindView(R.id.issuingOfficerTextView)
    TextView IssuingOfficerTextView;
    @BindView(R.id.bookingDateTextView)
    TextView BookingDateTextView;
    @BindView(R.id.codeImageView)
    ImageView CodeImageView;
    @BindView(R.id.printButtonLL)
    LinearLayout printButtonLL;

    private String passengerName = "";
    private String passengerSeatNo = "";
    private String idNumber = "";
    private String from = "";
    private Ticket mTicket;
    private JSONObject bookingDetails;
    private Bitmap totalBitmap;
    private Bitmap mainTicketBitmap;
    private Bitmap subTicketBitmap;
    private int bookingId;
    private String deviceIDName = "";

    public static void start(Context context, String code, QuickSale quickSale) {

        Intent intent = new Intent(context, TicketActivity.class);
        if (code != null) {
            intent.putExtra("code", code);
        }
        if (quickSale != null) {
            intent.putExtra("quick_sale", quickSale);
        }
        context.startActivity(intent);
    }


    public static void startForBookingDetails(Context context, JSONObject bookingDetails, QuickSale quickSale) {
        Intent intent = new Intent(context, TicketActivity.class);
        if (bookingDetails != null) {
            intent.putExtra("bookingDetails", bookingDetails.toString());
        }
        if (quickSale != null) {
            intent.putExtra("quick_sale", quickSale);
        }
        context.startActivity(intent);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_ticket;
    }

    @Override
    public boolean hideNavigationIcon() {
        return false;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        //  mCode = intent.getStringExtra("code");
        QuickSale quickSale = intent.getParcelableExtra("quick_sale");
        try {
            if (intent.hasExtra("bookingDetails")) {
                bookingDetails = new JSONObject(intent.getStringExtra("bookingDetails"));
                from = intent.getStringExtra("from");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (quickSale != null) {
            fromPlaceTextView.setText(quickSale.placeFrom);
            toPlaceTextView.setText(quickSale.placeTo);
        }
        // loadData();

        getDevNameID();
        getDataForBookingDetails();
    }

    private void getDevNameID() {
        if (checkPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 123, new PermCallback() {
            @Override
            public void permGranted(int resultCode) {
                deviceIDName = getDeviceId();
            }

            @Override
            public void permDenied(int resultCode) {

            }
        })) {
            deviceIDName = getDeviceId();
        }
    }

    @Override
    public void onBackPressed() {
        if (from.equalsIgnoreCase("cart")) {
            Intent intent = new Intent(TicketActivity.this, BusLayoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        } else {
            super.onBackPressed();
        }
        // super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataForBookingDetails() {
        try {
            JSONArray jsonArray = bookingDetails.getJSONArray("passangers");
            StringBuilder surname = new StringBuilder();
            StringBuilder seatNumber = new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject passengerObject = jsonArray.getJSONObject(i);
                if (jsonArray.length() <= 1) {
                    passengerName = passengerObject.getString("surname");
                    passengerSeatNo = passengerObject.getString("seatNumber");
                } else {
                    surname.append(passengerObject.getString("surname"));
                    surname.append(",");
                    seatNumber.append(passengerObject.getString("seatNumber"));
                    seatNumber.append(",");
                    passengerName = surname.substring(0, surname.length() - 1);
                    passengerSeatNo = seatNumber.substring(0, seatNumber.length() - 1);
                }
                idNumber = passengerObject.optString("idNumber");
                String ticketPrice = passengerObject.getString("seatPrice");
            }

            String code = bookingDetails.optString("pin");
            String totalFair = bookingDetails.optString("totalFair");
            String ticketQrCode = bookingDetails.optString("qrCode");
            String coachName = bookingDetails.optString("busOperatorName");
            String coachEmailAddress = bookingDetails.optString("busOperatorEmail");
            String issuingOfficerName = bookingDetails.optString("issuingOfficer");
            String coachPhoneNumber = bookingDetails.optString("busOperatorPhone");
            String departureTime = bookingDetails.optString("endTime");
            String arrivalTime = bookingDetails.optString("startTime");
            String sourceLocation = bookingDetails.optString("sourceLocation");
            String destinationLocation = bookingDetails.optString("destinationLocation");
            String classType = bookingDetails.optString("class");
            String bookingDate = bookingDetails.optString("bookingOfDate");
            String busNumber = bookingDetails.optString("busNumber");
            String ticketId = bookingDetails.optString("eTicket");
            String busRoute = bookingDetails.optString("busRoute");
            String busOperatorName = bookingDetails.optString("busOperatorName");
            String busOperatorLogo = bookingDetails.optString("busOperatorLogo");
            String discountedAmount = bookingDetails.optString("discountedAmount");
            int isPrint = bookingDetails.optInt("isPrint");
            int printingStatus = bookingDetails.optInt("printingStatus");
            bookingId = bookingDetails.optInt("id");

            if (isPrint == 0) {
                printButtonLL.setVisibility(View.GONE);
            } else {
                printButtonLL.setVisibility(View.VISIBLE);
            }
            pinNumberTextView.setText("(" + code + ")");
            passengerNameTextView.setText(passengerName);

            //eticketId.setText("477512477512");
            mTvName.setText(busOperatorName);
            coachEmailAddressTextView.setText(coachEmailAddress);
            seatNoTextView.setText(passengerSeatNo);
            priceTextView.setText("UGX " + totalFair);
            fromPlaceTextView.setText(sourceLocation);
            toPlaceTextView.setText(destinationLocation);
            departTimeTextView.setText(arrivalTime);
            arriveTimeTextView.setText(departureTime);
            ClassTypeTextView.setText(classType);
            BusNumberTextView.setText(busNumber);
            BusRouteTextView.setText(busRoute);
            BookingDateTextView.setText(bookingDate);
            //CoachNameTextView.setText(coachName);
            coachPhoneNumberTextView.setText(coachPhoneNumber);
            IssuingOfficerTextView.setText(issuingOfficerName);
            ticketIdTextView.setText(ticketId);
            discountTextView.setText("UGX " + discountedAmount);
            IdNumberTextView.setText(idNumber);
          /*  Picasso.with(getApplicationContext())
                    .load(ticketQrCode)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mIvQrCode);*/
            imageLoader(TicketActivity.this, ticketQrCode, mIvQrCode);
            imageLoader(TicketActivity.this, busOperatorLogo, mIcLogo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        RetrofitService.ticket().subscribe(new SimpleSubscriber<TicketInfoRes>() {
            @Override
            public void onSuccess(TicketInfoRes ticketInfoRes) {
                mTicket = ticketInfoRes.ticket;
                // refreshUI();
            }//010216

            @Override
            public void onFailed(Throwable e) {
            }
        });
    }

    //483496
    @OnClick({R.id.btn_print, R.id.btn_needless})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_print:
                if (!DeviceManager.getInstance().getPrinter().isReady()) {
                    ToastUtils.show(R.string.printer_not_ready);
                    return;
                }
                try {
                    DialogHelper.showLoadingDialog(TicketActivity.this, getString(R.string.toast_printing));
                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            ticketTextView.setText(R.string.tv_main_ticket);
                            mainTicketBitmap = BitmapUtils.getBitmapFromView(viewTicketLayout);
                        } else if (i == 1) {
                            ticketTextView.setText(R.string.tv_sub_ticket);
                            mRlCompanyInfo.setVisibility(View.GONE);
                            mLlTermsAndConditions.setVisibility(View.GONE);
                            mLlTermsAndConditions.postDelayed(new Runnable() {// 延迟执行，因为termsAndConditionsLayout.setVisibility(View.GONE)需要时间
                                @Override
                                public void run() {
                                    subTicketBitmap = BitmapUtils.getBitmapFromView(viewTicketLayout);

                                    // recover old view
                                    mRlCompanyInfo.setVisibility(View.VISIBLE);
                                    mLlTermsAndConditions.setVisibility(View.VISIBLE);
                                    ticketTextView.setText(R.string.tv_main_ticket);
                                }
                            }, 5);
                        }
                    }

                    hitPrintApi();

                    viewTicketLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            totalBitmap = BitmapUtils.combineBitmap(mainTicketBitmap, subTicketBitmap);
                            DeviceManager.getInstance().getPrinter().printImage(totalBitmap);
                        }
                    }, 50);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_needless:
                onBackPressed();
                break;
        }
    }

    private void hitPrintApi() {
        Call<JsonObject> printTicket = apiInterface.printTicket(GlobalStore.getToken(getApplicationContext()), bookingId, deviceIDName);
        apiHitAndHandle.makeApiCall(printTicket, false, new ApiResponse() {
            @Override
            public void onSuccess(Call call, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    int isPrint = jsonObject.optInt("isPrint");
                    if (isPrint == 0) {
                        printButtonLL.setVisibility(View.GONE);
                    } else {
                        printButtonLL.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //0890000105035616
            //IFSC Code: PUNB0089000
            @Override
            public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        DeviceManager.getInstance().getPrinter().init();
    }
}

package com.kacyber.pos.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.adapters.PassangerListAdapter;
import com.kacyber.pos.models.BookingModuleData;
import com.kacyber.pos.models.BusSeats;
import com.kacyber.pos.models.PassengerInfoData;
import com.kacyber.pos.models.PromoCode;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.Const;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.NonScrollRecyclerView;
import com.kacyber.pos.util.common.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Mostafa on 10/02/2017.
 */

public class CartActivity extends BaseActivity implements ApiResponse {

    private ScrollView mainScrollView;
    private TextView onwardTripTextView, busRoute;
    private TextView onwardTripDateTextView;
    private NonScrollRecyclerView passengerRecyclerView;
    private TextView priceTextView, returnPassenger;
    private TextView feesTextView, discountTV;
    private TextView totalTextView, applyTV;
    private RelativeLayout couponRL, discountRL;
    private EditText couponET;
    private ArrayList<BusSeats.SeatStructure> chosenSeats;
    private Button bookButton;
    private Call<JsonObject> bookingCall;
    private ArrayList<PassengerInfoData> passengerList = new ArrayList<>();
    private ArrayList<PassengerInfoData> passengerListForItemClick = new ArrayList<>();
    private BookingModuleData bookingModule;

    double TICKET_FEE = 7.5;
    private String totalAmount = "";
    /* private String payId = "", dropingPointName = "", boardingPointName = "";
     private int dropingPointId, boardingPointId;*/
    //TODO payPal
    public static final String PAYPAL_CLIENT_ID = "AUtThAt-hdztqqV7NemM9VyiU0S6ykWU9-2vE9hCOpXs-fomaMco7UcsXPfwVPZBWs8zE_b6DxVjpXFI";
    private Spinner payViaspinner;
    private int userRole;
    private Call<JsonObject> applyPromo;
    private boolean isDiscountApplicableForSingle;
    private String couponId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findIds();
        chosenSeats = getIntent().getParcelableArrayListExtra(Const.BOOKED_SEATS);
        if (chosenSeats == null) finish();
        bookingModule = BookingModuleData.getInstance();

        addDataToPassegerList();
        setupActionBar();
        fillSingleBookingInfo(bookingModule, passengerList);
        getPriceTotal();
        userRole = GlobalStore.getUserRoll(getApplicationContext());
    }

    private void getPriceTotal() {
        discountRL.setVisibility(View.GONE);
        discountTV.setText("");
        TICKET_FEE = bookingModule.busOperatorCommission; //TODO BUS FEE
        double price = passengerList.get(0).seatPrice;
        double priceTotal = price * passengerList.size();
        priceTextView.setText(bookingModule.currencyType + " " + priceTotal);

        double feePrice = (priceTotal * TICKET_FEE) / 100;
        DecimalFormat twoDForm = new DecimalFormat("#.00");
        String feeSingle = "" + bookingModule.currencyType + " " + twoDForm.format(feePrice);

        totalAmount = String.valueOf(twoDForm.format(priceTotal));
        totalTextView.setText(bookingModule.currencyType + " " + totalAmount);

    }

    private void fillSingleBookingInfo(BookingModuleData bookingData, ArrayList<PassengerInfoData> passengerInfoData) {
        onwardTripTextView.setText(bookingData.FromCityName
                + " To "
                + bookingData.TOCityName);
        onwardTripDateTextView.setText(bookingData.searchDateString);
        busRoute.setText("Route name: " + bookingData.busRoute);
        passengerRecyclerView.setAdapter(buildAdapter(passengerInfoData, 0));
      /*  passengerRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mainScrollView.scrollTo(0, 0);
            }
        });*/
    }

    private PassangerListAdapter buildAdapter(final ArrayList<PassengerInfoData> passengerDataList, final int typeClick) {
        String[] passengers = new String[passengerDataList.size()];
        for (int i = 0; i < passengers.length; i++) {
            passengers[i] = "Passenger " + (i + 1) + "\n Seat no " + passengerDataList.get(i).seatNumber;
        }
        passengerListForItemClick = passengerDataList;
        PassangerListAdapter adapter = new PassangerListAdapter(this, passengers, PassangerListAdapter.Mode.TEXT, this);
        return adapter;
    }


    private void addDataToPassegerList() {
        for (int i = 0; i < chosenSeats.size(); i++) {
            PassengerInfoData infoData = new PassengerInfoData(Parcel.obtain());
            infoData.surname = "";
            infoData.country = "";
            infoData.givenName = "";
            infoData.dateOfBirth = "";
            infoData.gender = "M";
            infoData.seatCategory = "";
            infoData.seatPrice = 0;
            infoData.idNumber = "";
            infoData.idType = "";
            infoData.phoneNumber = "";
            infoData.seatNumber = chosenSeats.get(i).seatNo;
            infoData.seatCategory = chosenSeats.get(i).seatCategory;
            infoData.seatPrice = chosenSeats.get(i).ticketPrice;
            passengerList.add(infoData);
        }
    }

    // 104936
    private void findIds() {
        discountRL = findViewById(R.id.discountRL);
        discountTV = findViewById(R.id.discountTV);
        couponRL = findViewById(R.id.couponRL);
        couponET = findViewById(R.id.couponET);
        applyTV = findViewById(R.id.applyTV);
        onwardTripTextView = findViewById(R.id.onward_trip_text_view);
        busRoute = findViewById(R.id.busRoute);
        onwardTripDateTextView = findViewById(R.id.onward_trip_date_text_view);
        passengerRecyclerView = (NonScrollRecyclerView) findViewById(R.id.passenger_recycler_view);
        priceTextView = findViewById(R.id.price_text_view);
        feesTextView = findViewById(R.id.fees_text_view);
        bookButton = findViewById(R.id.book_button);
        totalTextView = findViewById(R.id.total_text_view);
        payViaspinner = findViewById(R.id.payViaspinner);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Booking();
            }
        });
        userRole = GlobalStore.getUserRoll(getApplicationContext());
        applyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCoupanCode(couponET.getText().toString().trim());
            }
        });
        if (userRole == Const.USER_SUB_ADMIN || userRole == Const.USER_BOOKING_CLER) {
            couponRL.setVisibility(View.VISIBLE);
        } else {
            couponRL.setVisibility(View.GONE);
        }
    }

    private void checkCoupanCode(String trim) {

        String buttontext = applyTV.getText().toString().trim();
        if (trim.isEmpty()) {
            showToast("Please enter coupon code");
        } else if (buttontext.equals("Remove")) {
            applyTV.setText("Apply");
            couponET.setText("");
            couponET.setEnabled(true);
            isDiscountApplicableForSingle = false;
            couponId = "";
            getPriceTotal();
        } else {

            HashMap<String, Object> hashMa = new HashMap<>();
            hashMa.put("busFleetId", bookingModule.busFleetId);
            hashMa.put("returnBusFleetId", -1);
            hashMa.put("promoCode", trim);
            applyPromo = apiInterface.applyPromoCode(GlobalStore.getToken(getApplicationContext()), hashMa);
            apiHitAndHandle.makeApiCall(applyPromo, true, this);
        }
    }

    private void Booking() {
        int paymentFrom = payViaspinner.getSelectedItemPosition();

        JSONArray arraySinglePassanger;
        arraySinglePassanger = getPassengerArray(passengerList);

        if (arraySinglePassanger != null && arraySinglePassanger.length() > 0) {
            Log.e("array>>", arraySinglePassanger.toString());
            try {
                JSONObject jsonObject = new JSONObject();
                //TODO hit api For Single booking
                jsonObject.put("passangersDetails", arraySinglePassanger);
                int busFleetId = bookingModule.busFleetId;// single bus fleet is
                String bookingOfDate = parseDateToddMMyyyy(bookingModule.searchDateLong);// singe booking date "31-12-207"
                // show dialog
                hitBookingApi("", busFleetId, bookingOfDate, jsonObject.toString(),
                        paymentFrom, bookingModule.FromCityId, bookingModule.TOCityId
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //  ToastUtils.show(" single user Array null");
        }
    }


    private void hitBookingApi(String bookedBy, int busFleetId,
                               String bookingOfDate, String singlePassangerDatails,
                               int paymentFrom, String boardingPointsId, String DropingPointsId) {
        bookingCall = apiInterface.bookTicket(
                GlobalStore.getToken(getApplicationContext()),
                busFleetId,bookingModule.currencyType, isDiscountApplicableForSingle, couponId,
                bookingOfDate,
                singlePassangerDatails,
                paymentFrom,
                boardingPointsId,
                DropingPointsId);
        apiHitAndHandle.makeApiCall(bookingCall, true, this);
    }

    // phone no ===256 784007939
    @NonNull
    private JSONArray getPassengerArray(ArrayList<PassengerInfoData> passengerListData) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < passengerListData.size(); i++) {
            PassengerInfoData infoData = passengerListData.get(i);
            JSONObject object = new JSONObject();
            try {
                if (infoData.surname.isEmpty() || infoData.seatCategory.isEmpty()) {
                    ToastUtils.show("Please add passenger no " + (i + 1) + " detail");
                    return null;
                } else {
                    object.put("givenName", infoData.givenName);
                    object.put("dateOfBirth", infoData.dateOfBirth);
                    object.put("gender", infoData.gender);
                    object.put("idType", infoData.idType);
                    object.put("idNumber", infoData.idNumber);
                    object.put("phoneNumber", infoData.phoneNumber);
                    object.put("seatNumber", infoData.seatNumber);
                    object.put("seatPrice", infoData.seatPrice);
                    object.put("surname", infoData.surname);
                    object.put("country", infoData.country);
                    object.put("seatCategory", Integer.parseInt(infoData.seatCategory));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(object);
        }
        return jsonArray.length() == 0 ? null : jsonArray;
    }

    ProgressDialog progressDialog;

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }

    private void setupActionBar() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 123:
                if (resultCode == RESULT_OK) {
                    try {
                        int position = data.getExtras().getInt("PassengerPosition");
                        addDataInArray((PassengerInfoData) data.getExtras().getParcelable("Passengers"), position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void addDataInArray(PassengerInfoData passengerInfo, int position) throws JSONException {
        passengerList.set(position, passengerInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSuccess(Call call, Object object) {

        if (applyPromo == call) {
            try {
                PromoCode code = new Gson().fromJson(object.toString(), PromoCode.class);
                if (code.status.equals("1")) {
                    couponId = code.discountCouponSingle.id;
                    applyTV.setText("Remove");
                    couponET.setEnabled(false);
                    isDiscountApplicableForSingle = true;
                    discountRL.setVisibility(View.VISIBLE);
                    double price = passengerList.get(0).seatPrice;
                    double priceTotal = price * passengerList.size();
                    double discontPrice = (priceTotal * code.discountCouponSingle.amount) / 100;
                    priceTotal = priceTotal - discontPrice;
                    discountTV.setText(bookingModule.currencyType + " " + discontPrice);
                    DecimalFormat twoDForm = new DecimalFormat("#.00");
                    totalAmount = String.valueOf(twoDForm.format(priceTotal));
                    totalTextView.setText(bookingModule.currencyType + " " + totalAmount);
                } else {
                    isDiscountApplicableForSingle = false;
                    couponId = "";
                    showToast(code.message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Log.e("resultCart====", object.toString());
            try {
                JSONObject jsonObject = new JSONObject(object.toString());
                String status = jsonObject.getString("status");
                String message = jsonObject.getString("message");
                if (status.equalsIgnoreCase("1")) {
                    Log.e("result>>", object.toString());
                    JSONObject bookingDetailObject = jsonObject.getJSONObject("BookingDetails");

                    Intent intent = new Intent(getApplicationContext(), TicketActivity.class);
                    intent.putExtra("bookingDetails", bookingDetailObject.toString());
                    intent.putExtra("from", "cart");
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    public void itemClick(int position) {
        PassengerInfoData infoData = passengerListForItemClick.get(position);
        Intent intent = new Intent(CartActivity.this, PassengerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("PassengerInfo", infoData);
        intent.putExtra("PassengerPosition", position);
        // intent.putExtra(Intent.EXTRA_TITLE, item);
        startActivityForResult(intent, 123);
    }
}

package com.kacyber.pos.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.adapters.ActivityAdapter;
import com.kacyber.pos.adapters.AllServicesAdapter;
import com.kacyber.pos.interfaces.OnSelectCallBack;
import com.kacyber.pos.models.ActivityData;
import com.kacyber.pos.models.InsightData;
import com.kacyber.pos.models.ServicesData;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.ui.fragment.AllServicesFragment;
import com.kacyber.pos.util.Const;
import com.kacyber.pos.util.GlobalStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class ReportsActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, ApiResponse {
    @BindView(R.id.isightTV)
    TextView isightTV;
    @BindView(R.id.activityTV)
    TextView activityTV;
    @BindView(R.id.todayTV)
    TextView todayTV;
    @BindView(R.id.serviceTV)
    TextView serviceTV;
    @BindView(R.id.reportRL)
    RelativeLayout reportRL;
    @BindView(R.id.searchRL)
    RelativeLayout searchRL;
    @BindView(R.id.startDateTV)
    TextView startDateTV;
    @BindView(R.id.endDateTV)
    TextView endDateTV;
    @BindView(R.id.activityRC)
    RecyclerView activityRC;
    @BindView(R.id.totalTicketIssueTV)
    TextView totalTicketIssueTV;
    @BindView(R.id.bookedSeatTV)
    TextView bookedSeatTV;
    @BindView(R.id.totalCancelTicketTV)
    TextView totalCancelTicketTV;
    @BindView(R.id.priceCancelAmtTV)
    TextView priceCancelAmtTV;
    @BindView(R.id.totalAmountTV)
    TextView totalAmountTV;
    @BindView(R.id.printedTotalTV)
    TextView printedTotalTV;
    @BindView(R.id.verifiedTotalTV)
    TextView verifiedTotalTV;
    @BindView(R.id.backIV)
    ImageView backIV;
    @BindView(R.id.header)
    LinearLayout header;


    private String selectedText;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
    private LinearLayoutManager linearLayoutManager;
    private boolean loading = true;
    private Call<JsonObject> getBusFleetsBy;
    private Call<JsonObject> getInsight;
    private ServicesData servicesData;
    private String selectedBusId = "-1";
    private Call<JsonObject> getActivity;
    private ActivityAdapter activityAdapter;
    private List<ActivityData.Datum> activityData = new ArrayList<>();
    private int pageNo = 1;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private Call<JsonObject> getTicketDetail;
    private int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_report;
    }

    @Override
    public boolean hideNavigationIcon() {
        return false;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        linearLayoutManager = new LinearLayoutManager(ReportsActivity.this);
        activityRC.setLayoutManager(linearLayoutManager);
        // getInsightData();
        setupDate(endDateTV, calendar);
        //calendar.add(Calendar.MONTH, -1);
        setupDate(startDateTV, calendar);

        // TODO Set Adapter
        activityAdapter = new ActivityAdapter(getApplicationContext(), activityData);
        AllServicesAdapter.selectALL=true;
        activityRC.setAdapter(activityAdapter);
        activityAdapter.onItemSelect(new OnSelectCallBack() {
            @Override
            public void onSelect(int po, String is) {
                String code = activityData.get(po).eTicket;
               // code = code.substring(code.length() - 6, code.length());
                getBookinDetailsByETicket(code);
            }
        });

        getInsightData(startDateTV.getText().toString(), endDateTV.getText().toString(), selectedBusId);
        getAllServices();

        activityRC.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            pageNo++;
                            getActivityData(startDateTV.getText().toString(), endDateTV.getText().toString(), selectedBusId, pageNo);
                        }
                    }
                }
            }
        });
         userRole = GlobalStore.getUserRoll(getApplicationContext());
    }
//
    private void getAllServices() {
        try {
            getBusFleetsBy = apiInterface.getBusFleetsByPOS(GlobalStore.getToken(getApplicationContext()));
            apiHitAndHandle.makeApiCall(getBusFleetsBy, false, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBookinDetailsByETicket(String code) {
        // RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        getTicketDetail = apiInterface.getTicketDetailsByETicket(GlobalStore.getToken(getApplicationContext()), code, getDeviceId());
        apiHitAndHandle.makeApiCall(getTicketDetail, this);
    }

    private void getInsightData(String startD, String endD, String busid) {
        try {
            startD = convertDataFormate(startD);
            endD = convertDataFormate(endD);
            HashMap<String, String> hashMa = new HashMap<>();
            hashMa.put("startDate", startD);
            hashMa.put("endDate", endD);
            hashMa.put("busFleetId", busid);
            getInsight = apiInterface.getInsightData(GlobalStore.getToken(getApplicationContext()), hashMa);
            apiHitAndHandle.makeApiCall(getInsight, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDate(TextView textView, Calendar mcalendar) {
        textView.setText(simpleDateFormat.format(mcalendar.getTimeInMillis()));
    }

    @Override
    public void onSuccess(Call call, Object object) {
        if (call == getBusFleetsBy) {
            if (object != null) {
                servicesData = new Gson().fromJson(object.toString(), ServicesData.class);
            }
        } else if (call == getInsight) {
            if (object != null) {
                InsightData insightData = new Gson().fromJson(object.toString(), InsightData.class);
                setInsightData(insightData.data);
            }
        } else if (call == getActivity) {
            if (object != null) {
                ActivityData servicesData = new Gson().fromJson(object.toString(), ActivityData.class);
                if (servicesData.totalCount > activityData.size()) {
                    loading = true;
                } else {
                    loading = false;
                }
                if (servicesData.dataList.size() == 0 && activityData.size() == 0) {
                    showToast("No Activity found");
                } else {
                    activityData.addAll(servicesData.dataList);
                }
                activityAdapter.notifyDataChange(activityData);
            }
        } else if (getTicketDetail == call) {
            try {
                JSONObject jsonObject = new JSONObject(object.toString());
                String status = jsonObject.getString("status");
                String message = jsonObject.getString("message");
                if (status.equalsIgnoreCase("1")) {
                    JSONObject bookingDetailObject = jsonObject.getJSONObject("BookingDetails");
                    //TicketActivity.startForBookingDetails(this, bookingDetailObject, null);
                    Intent intent = new Intent(ReportsActivity.this, TicketActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("bookingDetails", bookingDetailObject.toString());
                    intent.putExtra("from", "report");
                    startActivity(intent);
                }else {
                    showToast(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setInsightData(InsightData.Datum insightData) {
        try {
            totalAmountTV.setText("UGX" + insightData.amountCollected);
            bookedSeatTV.setText("" + insightData.seatsBooked + " Seats Booked");
            totalCancelTicketTV.setText("" + insightData.cancellationAmount);
            priceCancelAmtTV.setText("UGX" + insightData.cancellationAmount);
            totalCancelTicketTV.setText(" " + insightData.cancelledSeats);
            totalTicketIssueTV.setText(" " + insightData.totalTicketsIssued);
            verifiedTotalTV.setText("" + insightData.ticketsVerified);
            printedTotalTV.setText("" + insightData.ticketsPrinted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.isightTV, R.id.activityTV, R.id.backIV, R.id.todayTV, R.id.serviceTV, R.id.endDateTV, R.id.startDateTV, R.id.searchBT})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.isightTV:
                setButton(0);
                reportRL.setVisibility(View.VISIBLE);
                activityRC.setVisibility(View.GONE);
                break;
            case R.id.activityTV:
                setButton(1);
                goToActivity();
                break;
            case R.id.todayTV:
                openSearchView();
                break;
            case R.id.serviceTV:
                goToServices();
                break;
            case R.id.startDateTV:
                showDatePicker("startDate");
                break;
            case R.id.endDateTV:
                showDatePicker("endDate");
                break;
            case R.id.searchBT:
                checkvalidate();

                break;
            case R.id.backIV:
                onBackPressed();

                break;
        }
    }

    private void goToServices() {
        if (servicesData != null) {
            AllServicesFragment servicesFragment = new AllServicesFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("servicesData", servicesData);
            servicesFragment.setArguments(bundle);
            servicesFragment.show(getSupportFragmentManager(), "");
            servicesFragment.setOnItemSelect(new OnSelectCallBack() {
                @Override
                public void onSelect(int po, String is) {
                    if (po < 0) {
                        selectedBusId = "-1";
                        serviceTV.setText("All Services");
                    } else {
                        selectedBusId = "" + servicesData.list.get(po).id;
                        serviceTV.setText(servicesData.list.get(po).busRoute);
                    }

                    String startDate = startDateTV.getText().toString().trim();
                    String endDate = endDateTV.getText().toString().trim();
                    hitApiByfilter(startDate, endDate);
                }
            });
        }
    }

    private void hitApiByfilter(String startDate, String endDate) {
        if (reportRL.getVisibility() == View.VISIBLE) {
            getInsightData(startDate, endDate, selectedBusId);
        } else {
            activityData = new ArrayList<>();
            getActivityData(startDate, endDate, selectedBusId, pageNo);
        }
    }

    private void goToActivity() {
        activityRC.setVisibility(View.VISIBLE);
        searchRL.setVisibility(View.GONE);
        reportRL.setVisibility(View.GONE);
        setTextViewDrawableColor(todayTV, R.color.colorPrimary);
        getActivityData(startDateTV.getText().toString(), endDateTV.getText().toString(), selectedBusId, pageNo);

        todayTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));


    }

    private void openSearchView() {
        if (searchRL.getVisibility() == View.VISIBLE) {
            searchRL.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            searchRL.setVisibility(View.GONE);
                        }
                    });

            todayTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            setTextViewDrawableColor(todayTV, R.color.colorPrimary);
        } else {
            searchRL.setAlpha(0f);
            searchRL.setVisibility(View.VISIBLE);
            searchRL.animate()
                    .alpha(1f)
                    .setDuration(1000)
                    .setListener(null);
            todayTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange_Button));
            setTextViewDrawableColor(todayTV, R.color.orange_Button);
        }
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void setButton(int i) {
        if (i == 0) {
            isightTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            isightTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            activityTV.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activity_button_bg));
            activityTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        } else {
            activityTV.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            activityTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            isightTV.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activity_button_bg));
            isightTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }
    }


    private void checkvalidate() {
        String startDate = startDateTV.getText().toString().trim();
        String endDate = endDateTV.getText().toString().trim();
        if (startDate.isEmpty()) {
            showToast("Please select from date");
        } else if (endDate.isEmpty()) {
            showToast("Please select to date");
        } else {
            if (startDate.equals(endDate)) {
                todayTV.setText("Today");
            } else {
                String searchDate = startDate + " To " + endDate;
                todayTV.setText(searchDate);
            }
            hitApiByfilter(startDate, endDate);
            openSearchView();
        }
    }

    private void getActivityData(String startDate, String endDate, String selectedId, int PageNo) {
        try {
            startDate = convertDataFormate(startDate);
            endDate = convertDataFormate(endDate);
            HashMap<String, String> hashMa = new HashMap<>();

            hashMa.put("startDate", startDate);
            hashMa.put("endDate", endDate);
            hashMa.put("busFleetId", selectedId);
            hashMa.put("pageNo", "" + PageNo);
            hashMa.put("maxRecords", "20");
            getActivity = apiInterface.getActivityDataForPOS(GlobalStore.getToken(getApplicationContext()), hashMa);
            apiHitAndHandle.makeApiCall(getActivity, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertDataFormate(String date) {
        Date mdate = null;
        String convertDate = "";
        try {
            mdate = simpleDateFormat.parse(date);
            SimpleDateFormat convertdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            convertDate = convertdate.format(mdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertDate;
    }


    private void showDatePicker(String textView) {
        selectedText = textView;
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        if (userRole == Const.USER_SUB_ADMIN) {
        } else {
            Calendar calendar1=Calendar.getInstance();
            calendar1.add(Calendar.MONTH, -1);
            datePickerDialog.getDatePicker().setMinDate(calendar1.getTimeInMillis());
        }
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        /* datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (86400000 * 1));*/
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, d);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        String dates = simpleDateFormat.format(calendar.getTime());
        if (selectedText.equals("startDate")) {
            startDateTV.setText(dates); //2018-09-26 00:00:00
        } else {
            endDateTV.setText(dates);//2018-09-26 23:58:00
        }

      /*departureTimeMilliSeconds = calendar.getTimeInMillis();
        String dates = simpleDateFormat.format(calendar.getTime());
        System.out.println("selected time => " + dates);
        date.setText(dates);*/
    }


    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        //   (call, errorMessage, apiResponse);
        showToast(errorMessage);
    }
}

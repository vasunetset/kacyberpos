package com.kacyber.pos.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.entity.QuickSale;
import com.kacyber.pos.models.CityModel;
import com.kacyber.pos.models.SearchResultModel;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class QuickSaleActivity extends BaseActivity implements ApiResponse, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.place_from)
    EditText placeFrom;
    @BindView(R.id.place_to)
    EditText placeTo;
    @BindView(R.id.date)
    TextView date;

    private Call<JsonObject> citiesData;
    private Call<JsonObject> searchBusFleet;
    private ArrayList<CityModel> cityModelList = new ArrayList<>();
    public static int REQUEST_ID_FROM_CITY = 123;
    public static int REQUEST_ID_TO_CITY = 456;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM-dd-yyyy", Locale.getDefault());
    private String cityIdFrom = "", cityNameFrom = "", cityIdTo = "", cityNameTo = "";
    private long departureTimeMilliSeconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_quick_sale;
    }

    @Override
    public boolean hideNavigationIcon() {
        return false;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setCurrentData();
        getCitiesListApi();
    }

    private void setCurrentData() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        departureTimeMilliSeconds = c.getTime();
        String formattedDate = simpleDateFormat.format(c);
        date.setText(formattedDate);
    }

    private void getCitiesListApi() {
        citiesData = apiInterface.getCitiesApi(GlobalStore.getToken(getApplicationContext()));
        apiHitAndHandle.makeApiCall(citiesData, this);
    }

    @OnClick({R.id.btn_change, R.id.date, R.id.btn_search,
            R.id.recent, R.id.place_from, R.id.place_to})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                String temp;
                String from = placeFrom.getText().toString().trim();
                String to = placeTo.getText().toString().trim();
                temp = cityIdFrom;
                cityIdFrom = cityIdTo;
                cityIdTo = temp;

                placeFrom.setText(to);
                placeTo.setText(from);
                break;
            case R.id.date:
                showDatePicker();
                break;
            case R.id.btn_search:
                if (checkValid()) {
                    QuickSale quickSale = new QuickSale();
                    quickSale.date = date.getText().toString().trim();
                    quickSale.placeFrom = placeFrom.getText().toString().trim();
                    quickSale.placeTo = placeTo.getText().toString().trim();
                    searchBusFleet();
                }
                break;
            case R.id.recent:
                break;
            case R.id.place_from:
                setCitiesListToNext(REQUEST_ID_FROM_CITY, "from");
                break;
            case R.id.place_to:
                setCitiesListToNext(REQUEST_ID_TO_CITY, "to");
                break;
        }
    }

    private void searchBusFleet() {
        JSONObject filterObject = new JSONObject();
        JSONArray busoperatorsArray = new JSONArray();
        JSONArray boardingPoints = new JSONArray();
        JSONArray dropOffPoints = new JSONArray();
        JSONArray amenities = new JSONArray();
        JSONArray busTypes = new JSONArray();
        JSONArray timeIntervals = new JSONArray();
        try {
            filterObject.put("busCompanies", busoperatorsArray);
            filterObject.put("boardingPoints", boardingPoints);
            filterObject.put("dropOffPoints", dropOffPoints);
            filterObject.put("amenities", amenities);
            filterObject.put("busTypes", busTypes);
            filterObject.put("timeIntervals", timeIntervals);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        searchBusFleet = apiInterface.searchBusFleets(
                GlobalStore.getToken(getApplicationContext()),
                cityIdFrom, cityIdTo, "" + departureTimeMilliSeconds, "" + filterObject);
        apiHitAndHandle.makeApiCall(searchBusFleet, this);
    }

    public boolean checkValid() {
        if (placeFrom.getText().toString().isEmpty()) {
            ToastUtils.show(getResources().getString(R.string.please_select_from_city));
            return false;
        } else if (placeTo.getText().toString().isEmpty()) {
            ToastUtils.show(getResources().getString(R.string.please_select_to_city));
            return false;
        } else if (date.getText().toString().isEmpty()) {
            ToastUtils.show(getResources().getString(R.string.please_select_departure_date));
            return false;
        }
        return true;
    }

    private void setCitiesListToNext(int requestCode, String place) {
        Intent intent = new Intent(QuickSaleActivity.this, CityListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("cityList", cityModelList);
        intent.putExtra("from", place);
        startActivityForResult(intent, requestCode);
    }

    private void dateIntoMilliSeconds(String givenDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM-dd-yyyy");
        try {
            Date mDate = sdf.parse(givenDateString);
            departureTimeMilliSeconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ID_FROM_CITY) {
                cityIdFrom = data.getStringExtra("cityId");
                cityNameFrom = data.getStringExtra("cityName");
                placeFrom.setText(cityNameFrom);
            } else if (requestCode == REQUEST_ID_TO_CITY) {
                cityIdTo = data.getStringExtra("cityId");
                cityNameTo = data.getStringExtra("cityName");
                placeTo.setText(cityNameTo);
            }
        }
    }

    private void showDatePicker() {
        // 24 hours = 86400 seconds = 86400000 milliseconds.
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (86400000 * 1));
        datePickerDialog.show();
    }


    @Override
    public void onSuccess(Call call, Object object) {
        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            if (call == citiesData) {
                String status = jsonObject.getString("status");
                String message = jsonObject.optString("message");
                if (status.equalsIgnoreCase("1")) {
                    cityModelList.clear();
                    JSONArray citiesArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < citiesArray.length(); i++) {
                        JSONObject jsonObject1 = citiesArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String name = jsonObject1.getString("station");
                        CityModel cityModel = new CityModel(id, name);
                        cityModelList.add(cityModel);
                    }
                }
            } else if (call == searchBusFleet) {
                Gson mGson = new Gson();
                SearchResultModel searchResultModel = mGson.fromJson(jsonObject.toString(),
                        SearchResultModel.class);
                Intent intent = new Intent(QuickSaleActivity.this, SearchResultsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("fromCityName", placeFrom.getText().toString().trim());
                intent.putExtra("toCityName", placeTo.getText().toString().trim());
                intent.putExtra("toCityId", cityIdTo);
                intent.putExtra("fromCityId", cityIdFrom);
                intent.putExtra("busList", searchResultModel);
                intent.putExtra("departureTime", departureTimeMilliSeconds);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, d);
        departureTimeMilliSeconds = calendar.getTimeInMillis();
        String dates = simpleDateFormat.format(calendar.getTime());
        System.out.println("selected time => " + dates);
        date.setText(dates);
    }
}

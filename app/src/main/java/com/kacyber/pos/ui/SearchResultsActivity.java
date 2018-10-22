package com.kacyber.pos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.adapters.SearchResultsAdapter;
import com.kacyber.pos.models.BookingModuleData;
import com.kacyber.pos.models.SearchResultModel;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.GlobalStore;
import com.kacyber.pos.util.common.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import retrofit2.Call;


public class SearchResultsActivity extends BaseActivity implements View.OnClickListener, ApiResponse {

    @BindView(R.id.toolbar_title)
    TextView mTitleView;
    private Gson mGson;
    private RecyclerView resultsRecyclerView;
    private TextView dateTextView;
    private Calendar calendar = Calendar.getInstance();
    private String formCityId, toCityId;
    private SearchResultModel searchResultModel;
    private String toName, fromName, busList;
    private TextView errorTV;
    private long departureDate;
    private Call<JsonObject> searchBuses;
    Calendar currnetcalendar = Calendar.getInstance();
    Calendar previouscalendar = Calendar.getInstance();
    private ImageButton forward_image_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findsIds() {
        resultsRecyclerView = findViewById(R.id.result_recycler_view);
        dateTextView = findViewById(R.id.date_text_view);
        errorTV = findViewById(R.id.errorTV);
        forward_image_button = findViewById(R.id.forward_image_button);
        ImageButton back_image_button = findViewById(R.id.back_image_button);
        forward_image_button.setOnClickListener(this);
        back_image_button.setOnClickListener(this);
        currnetcalendar.add(Calendar.DATE, 1);
        // previouscalendar.add(Calendar.DATE,-1);
    }


    private void setupRecyclerView() {
        Log.e("listSize>>", searchResultModel.list.size() + "");
        if (searchResultModel.list.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchResultsActivity.this);
            resultsRecyclerView.setLayoutManager(linearLayoutManager);
            SearchResultsAdapter adapter = new SearchResultsAdapter(this, searchResultModel.list, this);
            resultsRecyclerView.setAdapter(adapter);
        } else {
            errorTV.setVisibility(View.VISIBLE);
            resultsRecyclerView.setVisibility(View.GONE);
            errorTV.setText("Buses not found !!");
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_search_results;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toName = getIntent().getStringExtra("toCityName");
        fromName = getIntent().getStringExtra("fromCityName");
        departureDate = getIntent().getLongExtra("departureTime", 0);
        formCityId = getIntent().getStringExtra("fromCityId");
        toCityId = getIntent().getStringExtra("toCityId");
        searchResultModel = (SearchResultModel) getIntent().getSerializableExtra("busList");
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(departureDate);
        findsIds();
        setupDateBar();
        setupRecyclerView();

    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        mTitleView.setText(fromName + " to " + toName);
    }

    private void setupDateBar() {
        dateTextView.setText(formatDate(calendar.getTimeInMillis()));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.forward_image_button) {
            calendar.add(Calendar.DAY_OF_MONTH, 1); //add a day
            if (calendar.before(currnetcalendar)) {
                setupDateBar();
                getSearchBuses(calendar.getTimeInMillis());
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, -1); //add a day
                showToast("invalid search date");
            }

        } else if (i == R.id.back_image_button) {
            //calendar.add(Calendar.DAY_OF_MONTH, -1);
            if (calendar.after(previouscalendar)) {
                forward_image_button.setEnabled(true);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                setupDateBar();
                getSearchBuses(calendar.getTimeInMillis());
            } else {
                ToastUtils.show("invalid search date");
            }
        }
    }


    private void getSearchBuses(long onwarddate) {
        JSONObject jsonObject = new JSONObject();
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

            jsonObject.put("token", GlobalStore.getToken(SearchResultsActivity.this));
            jsonObject.put("sourceStation", formCityId);
            jsonObject.put("destinationStation", toCityId);
            jsonObject.put("onwarddate", onwarddate);
            jsonObject.put("timeZone", "" + TimeZone.getDefault().getID());
            jsonObject.put("filters", filterObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        searchBuses = apiInterface.searchBusFleets(
                GlobalStore.getToken(getApplicationContext()),
                formCityId, toCityId, "" + onwarddate, "" + filterObject);
        apiHitAndHandle.makeApiCall(searchBuses, this);
    }


    @Override
    public void onSuccess(Call call, Object object) {
        try {
            searchResultModel.list.clear();
            JSONObject jsonObject = new JSONObject(object.toString());
            mGson = new Gson();
            searchResultModel = mGson.fromJson(jsonObject.toString(),
                    SearchResultModel.class);
            if (searchResultModel.list.size() > 0) {
                errorTV.setVisibility(View.GONE);
                resultsRecyclerView.setVisibility(View.VISIBLE);
                setupRecyclerView();
            } else {
                errorTV.setVisibility(View.VISIBLE);
                resultsRecyclerView.setVisibility(View.GONE);
                errorTV.setText("Buses not found !!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        onError(call, errorMessage, apiResponse);
    }

    public void itemClick(int adapterPosition) {
        Log.e("bus started==", searchResultModel.list.get(adapterPosition).started);
        Log.e("calendar time==", "" + calendar.getTimeInMillis());
        Calendar mycalendar = Calendar.getInstance();
        mycalendar.setTimeInMillis(calendar.getTimeInMillis());
        if (searchResultModel.list.get(adapterPosition).started.equals("Previous From Search Date")) {
            mycalendar.add(Calendar.DATE, -1);
        }
        Intent intent = new Intent(SearchResultsActivity.this, BusLayoutActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        BookingModuleData moduleData = BookingModuleData.getInstance();
        moduleData.FromCityName = fromName;
        moduleData.TOCityName = toName;
        moduleData.FromCityId = formCityId;
        moduleData.TOCityId = toCityId;
        moduleData.currencyType = searchResultModel.list.get(adapterPosition).currency;
        moduleData.busRoute = searchResultModel.list.get(adapterPosition).sourceLocation + " To " + searchResultModel.list.get(adapterPosition).destinationLocation;
        moduleData.searchDateLong = mycalendar.getTimeInMillis();
        moduleData.searchDateString = dateTextView.getText().toString();
        intent.putExtra("searchDate", dateTextView.getText().toString().trim());
        intent.putExtra("onwardDate", mycalendar.getTimeInMillis());
        intent.putExtra("busFleetId", searchResultModel.list.get(adapterPosition).id);
        startActivity(intent);
    }
}
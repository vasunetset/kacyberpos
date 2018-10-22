package com.kacyber.pos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.models.BusDetails;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Mostafa on 10/03/2017.
 */

public class DepartureActivity extends BaseActivity implements ApiResponse {

    ExpandableLayout termsExpandableLayout, amenities_expandable_layout;
    ImageView termsChevron;
    TextView companyNameTextView;
    TextView priceTextView;
    TextView passengersTextView;
    TextView dateTextView;
    TextView departureCityTextView;
    TextView departureTimeTextView;
    TextView departureLocationTextView;
    TextView tripDurationTextView;
    TextView destinationCityTextView;
    TextView destinationTimeTextView;
    TextView destinationLocationTextView;
    ImageView amenitiesChevron;
    //  private ArrayList<BusSeats.SeatStructure> chosenSeats = new ArrayList<>();
    BusDetails busDetails;
    LinearLayout amenitiesLL;
    private Call<JsonObject> busCall;
    private TextView baggagePolicyTv, routeTV, refundPolicyTV;
    int busFleetId;
    String searchDate = "";
    Gson gson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findIds() {
        termsExpandableLayout = (ExpandableLayout) findViewById(R.id.terms_expandable_layout);
        amenities_expandable_layout = (ExpandableLayout) findViewById(R.id.amenities_expandable_layout);
        termsChevron = (ImageView) findViewById(R.id.terms_chevron);
        companyNameTextView = (TextView) findViewById(R.id.company_name_text_view);
        priceTextView = (TextView) findViewById(R.id.price_text_view);
        passengersTextView = (TextView) findViewById(R.id.passengers_text_view);
        dateTextView = (TextView) findViewById(R.id.date_text_view);
        departureCityTextView = (TextView) findViewById(R.id.departure_city_text_view);
        departureTimeTextView = (TextView) findViewById(R.id.departure_time_text_view);
        departureLocationTextView = (TextView) findViewById(R.id.departure_location_text_view);
        tripDurationTextView = (TextView) findViewById(R.id.trip_duration_text_view);
        destinationCityTextView = (TextView) findViewById(R.id.destination_city_text_view);
        destinationTimeTextView = (TextView) findViewById(R.id.destination_time_text_view);
        baggagePolicyTv = (TextView) findViewById(R.id.baggagePolicyTv);
        refundPolicyTV = (TextView) findViewById(R.id.refundPolicyTV);
        routeTV = (TextView) findViewById(R.id.routeTV);
        destinationLocationTextView = (TextView) findViewById(R.id.destination_location_text_view);
        amenitiesChevron = (ImageView) findViewById(R.id.amenities_chevron);
        amenitiesChevron.setVisibility(View.INVISIBLE);
        amenitiesLL = (LinearLayout) findViewById(R.id.amenitiesLL);
        Button book_button = (Button) findViewById(R.id.book_button);
        RelativeLayout terms_group = (RelativeLayout) findViewById(R.id.terms_group);
        RelativeLayout amenities_group = (RelativeLayout) findViewById(R.id.amenities_group);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startBusTypeActivity();
            }
        });
        terms_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTermsGroupClick();
            }
        });

    }

    private void setupActionBar() {
    }

    private void fillInfo(BusDetails busdtl) throws ParseException {
        BusDetails.BusDetails_ bus = busdtl.busDetails;
        dateTextView.setText(searchDate);
        companyNameTextView.setText(bus.name);
        priceTextView.setText(bus.currency + " " + (bus.ticketPricePerEconomySeat + bus.ticketPricePerBusinessSeat));
        departureCityTextView.setText(bus.sourceCity);
        departureTimeTextView.setText(bus.startTime);
        departureLocationTextView.setText(bus.sourceLocation);
        tripDurationTextView.setText(bus.duration);
        destinationCityTextView.setText(bus.destinationCity);
        destinationTimeTextView.setText(bus.endTime);
        destinationLocationTextView.setText(bus.destinationLocation);
        routeTV.setText(bus.busRoute);
        setNumberOfPassengers(bus.totalEconomySeats);
        setAmenitiesAdapter(busdtl.amenities);
        setPolicy(busdtl.termsAndPolicies);
        setNumberOfPassengers(1);
    }

    private void setPolicy(List<BusDetails.TermsAndPolicy> termsAndPolicies) {
        for (int i = 0; i < termsAndPolicies.size(); i++) {
            String PoliciesKey = termsAndPolicies.get(i).key;
            if (PoliciesKey.equals("Refund Policy")) {
                refundPolicyTV.setText(termsAndPolicies.get(i).description);
            } else if (PoliciesKey.equals("Baggage Policy")) {
                baggagePolicyTv.setText(termsAndPolicies.get(i).description);
            }
        }
    }

    private void setAmenitiesAdapter(final List<BusDetails.Amenity> amenitiesList) {
        amenitiesLL.removeAllViews();
        for (int i = 0; i < amenitiesList.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.amenities_item_layout, null);
            TextView itemName = (TextView) view.findViewById(R.id.itemNameTV);
            itemName.setText(amenitiesList.get(i).amenity);
            amenitiesLL.addView(view);
        }
    }

    private void setNumberOfPassengers(int numberOfPassengers) {
        passengersTextView.setText(getResources().getQuantityString(R.plurals.passenger, numberOfPassengers, numberOfPassengers));
    }

    public void onTermsGroupClick() {
        if (termsExpandableLayout.isExpanded()) {
            termsExpandableLayout.collapse();
            termsChevron.setRotation(90);
        } else {
            termsExpandableLayout.expand();
            termsChevron.setRotation(270);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_departure;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findIds();
        setupActionBar();
        busFleetId = getIntent().getIntExtra("busFleetId", 0);
        searchDate = getIntent().getStringExtra("searchDate");
        getBusDetails("" + busFleetId);
    }

    private void getBusDetails(String busId) {
        busCall = apiInterface.getBusDeatils(busId/*, Utils.parseDateToddMMyyyy(dateinLong)*/);
        apiHitAndHandle.makeApiCall(busCall, this);
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("busDetails", object.toString());
        gson = new Gson();
        busDetails = gson.fromJson(object.toString(), BusDetails.class);
        try {
            fillInfo(busDetails);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}

package com.kacyber.pos.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kacyber.pos.R;
import com.kacyber.pos.adapters.BusLayoutRecyclerview;
import com.kacyber.pos.interfaces.seatListener;
import com.kacyber.pos.models.BookingModuleData;
import com.kacyber.pos.models.BusSeats;
import com.kacyber.pos.retrofitManager.ApiHitAndHandle;
import com.kacyber.pos.retrofitManager.ApiResponse;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.Const;
import com.kacyber.pos.util.GlobalStore;

import java.util.ArrayList;

import retrofit2.Call;

public class BusLayoutActivity extends BaseActivity implements seatListener, ApiResponse {
    private MenuItem doneMenuItem;
    private Gson gson = new Gson();
    private int busFleetId;
    private long onwardTime;
    private Call<JsonObject> busSeatLayout, holdUnholdCall, allUnhold;
    private ArrayList<BusSeats.SeatStructure> selectSeatsByUser = new ArrayList<>();
    private RelativeLayout fotter;
    private BusSeats busSeats;
    private BookingModuleData bookingModule;
    private TextView totalAmount, seatNoTV;
    private RecyclerView recyclerViewBus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        apiHitAndHandle = ApiHitAndHandle.getInstance(this);
        selectSeatsByUser.clear();
        getSelectSeats(selectSeatsByUser);
        setIntentData();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_bus_type;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // busGridView = (GridView) findViewById(R.id.busGridView);
        fotter = findViewById(R.id.fotter);
        totalAmount = findViewById(R.id.totalAmount);
        seatNoTV = findViewById(R.id.seatNoTV);
        fotter.setVisibility(View.GONE);
        recyclerViewBus = findViewById(R.id.recyclerViewBus);
        setIntentData();
    }

    private void setIntentData() {
        Intent intent = getIntent();
        bookingModule = BookingModuleData.getInstance();
        busFleetId = intent.getIntExtra("busFleetId", 0);
        onwardTime = intent.getLongExtra("onwardDate", 0);
        bookingModule.busFleetId = busFleetId;
        getBusSeatsLayout(busFleetId, onwardTime);
    }

    private void getBusSeatsLayout(int busId, long searchDateInlong) {
        busSeatLayout = apiInterface.getBusSeatsDetails(
                GlobalStore.getToken(getApplicationContext()),
                bookingModule.FromCityId,
                bookingModule.TOCityId,
                parseDateToddMMyyyy(searchDateInlong),
                busId);
        apiHitAndHandle.makeApiCall(busSeatLayout, this);
    }

    private void setBusAdapter(ArrayList<BusSeats.SeatStructure> seatStructuresData, int column) {
        recyclerViewBus.setLayoutManager(new GridLayoutManager(this, column));
        recyclerViewBus.setAdapter(new BusLayoutRecyclerview(getApplicationContext(), 0, seatStructuresData, selectSeatsByUser, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bus_type_menu, menu);
        doneMenuItem = menu.getItem(0);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void getSelectSeats(ArrayList<BusSeats.SeatStructure> structures) {
        selectSeatsByUser.clear();
        selectSeatsByUser.addAll(structures);
        if (selectSeatsByUser.size() == 0) {
            fotter.setVisibility(View.GONE);
            seatNoTV.setText("No selected");
            totalAmount.setText("0");
        } else {
            fotter.setVisibility(View.VISIBLE);
            StringBuilder seatNo = new StringBuilder();
            int totalPrice = 0;

            for (int i = 0; i < selectSeatsByUser.size(); i++) {
                seatNo = seatNo.append(selectSeatsByUser.get(i).seatNo).append(",");
                totalPrice = totalPrice + selectSeatsByUser.get(i).ticketPrice;
            }
            seatNoTV.setText(removeLastChar(seatNo.toString()));
            totalAmount.setText("" + totalPrice);
        }
    }


    @Override
    public void getHodlSeats(BusSeats.SeatStructure data, boolean ishold) {
        int holdOrNot = ishold ? 1 : 0;
        holdUnholdCall = apiInterface.checkOrHoldUnholdSeats(
                GlobalStore.getToken(BusLayoutActivity.this),
                bookingModule.FromCityId,
                bookingModule.TOCityId,
                parseDateToddMMyyyy(onwardTime),
                busFleetId,
                holdOrNot,
                data.seatNo
        );
        apiHitAndHandle.makeApiCall(holdUnholdCall, false, this);
    }

    public void unholdAllSeats() {
        allUnhold = apiInterface.unholdAllSeatsByUser(GlobalStore.getToken(getApplicationContext()));
        apiHitAndHandle.makeApiCall(allUnhold, false, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unholdAllSeats();
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        } else if (i == R.id.action_done) {
            if (!selectSeatsByUser.isEmpty()) {
                Intent cartIntent = new Intent(this, CartActivity.class);
                cartIntent.putParcelableArrayListExtra(Const.BOOKED_SEATS, selectSeatsByUser);
                startActivity(cartIntent);
            } else {
                Toast.makeText(this, "You have to select at least one seat", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        builder.setMessage("Are you sure you want to remove selected seats");
        // add the buttons
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onSuccess(Call call, Object object) {
        try {
            if (busSeatLayout == call) {
                Log.e("tag>>", object.toString());
                busSeats = gson.fromJson(object.toString(), BusSeats.class);
                bookingModule.busOperatorCommission = busSeats.busOperatorCommission;
                bookingModule.acceptedPaymentMethods = busSeats.acceptedPaymentMethods;
                setBusAdapter(busSeats.seatStructures, busSeats.column);
            } else if (call == holdUnholdCall) {
                Log.e("tag>>", object.toString());
            } else if (call == allUnhold) {
                Log.e("tag>>", object.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        showToast(errorMessage);
    }
}

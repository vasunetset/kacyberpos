package com.kacyber.pos.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kacyber.pos.R;
import com.kacyber.pos.models.PassengerInfoData;
import com.kacyber.pos.ui.base.BaseActivity;
import com.kacyber.pos.util.Const;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;
import com.rilixtech.CountryCodePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;


/**
 * Created by Mostafa on 10/02/2017.
 */

public class PassengerActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    EditText surnameET, givenNameET, countryET;

    private EditText birthdayEditText;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private Spinner id_type_edit_Spinne;
    private EditText idNumberEditText;
    private EditText phoneNumberEditText;
    private RadioGroup genderRadioGroup;
    private int colorAccent;
    private int colorTextLight;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private PassengerInfoData passengerInfo;
    private int passengerPosition;
    private CountryCodePicker countryCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_passenger;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        passengerInfo = getIntent().getExtras().getParcelable("PassengerInfo");
        passengerPosition = getIntent().getExtras().getInt("PassengerPosition");
        findsIds();
        setupRadioButtons();
        setData();
    }

    private void setData() {
        if (passengerInfo.surname != null) {
            surnameET.setText(passengerInfo.surname);
            givenNameET.setText(passengerInfo.givenName);
            countryET.setText(passengerInfo.country);
            birthdayEditText.setText(passengerInfo.dateOfBirth);
            if (passengerInfo.gender.contains("M")) {
                femaleRadioButton.setChecked(false);
                maleRadioButton.setChecked(true);
            } else {
                femaleRadioButton.setChecked(true);
                maleRadioButton.setChecked(false);
            }
            // idTypeEditText.setText(passengerInfo.idType);
            idNumberEditText.setText(passengerInfo.idNumber);
            int countryCodeNo;
            long phoneNo;
            if (passengerInfo.phoneNumber != null && !passengerInfo.phoneNumber.isEmpty()) {
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(getApplicationContext());
                try {
                    Phonenumber.PhoneNumber numberProto = phoneUtil.parse(passengerInfo.phoneNumber, "");
                    countryCodeNo = numberProto.getCountryCode();
                    phoneNo = numberProto.getNationalNumber();
                    phoneNumberEditText.setText(new StringBuilder().append(phoneNo).append("").toString());
                    countryCode.setCountryForPhoneCode(countryCodeNo);
                } catch (NumberParseException e) {
                    System.err.println("NumberParseException was thrown: " + e.toString());
                }
            }
            for (int i = 0; i < getResources().getStringArray(R.array.id_type).length; i++) {
                if (passengerInfo.idType.equals(getResources().getStringArray(R.array.id_type)[i])) {
                    id_type_edit_Spinne.setSelection(i);
                    break;
                }
            }
        }
    }

    private void findsIds() {
        surnameET = findViewById(R.id.surnameET);
        givenNameET = findViewById(R.id.givenNameET);
        countryET = findViewById(R.id.countryET);
        countryCode = (CountryCodePicker) findViewById(R.id.countryCode);
        birthdayEditText = findViewById(R.id.birthday_edit_text);
        maleRadioButton = findViewById(R.id.male_radio_button);
        femaleRadioButton = findViewById(R.id.female_radio_button);
        id_type_edit_Spinne = (Spinner) findViewById(R.id.id_type_edit_Spinne);
        idNumberEditText = findViewById(R.id.id_number_edit_text);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        colorAccent = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        colorTextLight = ContextCompat.getColor(getApplicationContext(), R.color.colorTextLight);
        birthdayEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    showDatePicker();
                }
                return false;
            }
        });

        countryET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPlaceSearch();
            }
        });
    }

    public void getPlaceSearch() {
        CountryPicker countryPicker =
                new CountryPicker.Builder().with(getApplicationContext())
                        .listener(new OnCountryPickerListener() {
                            @Override
                            public void onSelectCountry(Country country) {
                                countryET.setText(country.getName());
                            }
                        })
                        .build();
        countryPicker.showDialog(getSupportFragmentManager()); // Show the dialog
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude,
                            place.getLatLng().longitude, 1);
               // String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
               *//* String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();*//*
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    countryET.setText(country);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    private void setupRadioButtons() {
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    radioButton.setTextColor(checkedId == radioButton.getId() ? colorAccent : colorTextLight);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        } else if (i == R.id.action_done) {
            validateData();
            //  finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void validateData() {
        String phoneNo = phoneNumberEditText.getText().toString().trim();
        PassengerInfoData passengerData = new PassengerInfoData(Parcel.obtain());
        passengerData.surname = surnameET.getText().toString().trim();
        passengerData.givenName = givenNameET.getText().toString().trim();
        passengerData.country = countryET.getText().toString().trim();
        passengerData.dateOfBirth = birthdayEditText.getText().toString().trim();
        passengerData.gender = maleRadioButton.isChecked() ? "M" : "F";
        passengerData.idType = id_type_edit_Spinne.getSelectedItem().toString();
        passengerData.idNumber = idNumberEditText.getText().toString().trim();
        passengerData.phoneNumber = phoneNumberEditText.getText().toString().trim();
        if (!passengerData.phoneNumber.isEmpty()) {
            passengerData.phoneNumber = "+" + countryCode.getSelectedCountryCode() + passengerData.phoneNumber;
        }
        passengerData.seatCategory = passengerInfo.seatCategory;
        passengerData.seatPrice = passengerInfo.seatPrice;
        passengerData.seatNumber = passengerInfo.seatNumber;

        if (passengerData.surname.isEmpty()) {
            showToast("Please enter the surname");
        } else if (passengerData.givenName.isEmpty()) {
            showToast("Please enter the given name");
        } else if (passengerData.gender.isEmpty()) {
            showToast("Please select gender");
        } else if (!phoneNo.isEmpty() && String.valueOf(phoneNo.charAt(0)).equals("0")) {
            showToast("Please enter valid phone number");
        } else if (!phoneNo.isEmpty() && !validateUsing_libphonenumber(countryCode.getSelectedCountryCode(), phoneNo)) {
            //  showToast("Please enter valid phone number");
            // TODO Toast already show
        } else {
            Intent intent = new Intent();
            intent.putExtra("Passengers", passengerData);
            intent.putExtra("PassengerPosition", passengerPosition);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public boolean onBirthdayEditTextTouch(MotionEvent e) {
        return false;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        String date = simpleDateFormat.format(calendar.getTime());
        birthdayEditText.setText(date);
    }
}

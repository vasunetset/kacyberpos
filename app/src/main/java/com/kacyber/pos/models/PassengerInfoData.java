package com.kacyber.pos.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by netset on 27/12/17.
 */

public class PassengerInfoData implements Parcelable {
    // public String fullName = "";
    public String dateOfBirth = "";
    public String gender = "";
    public String idType = "";
    public String idNumber = "";
    public String phoneNumber = "";
    public int seatNumber;
    public String seatCategory = "";
    public int seatPrice;
    public String country = "";
    public String surname = "";
    public String givenName = "";

    public PassengerInfoData(Parcel in) {
        dateOfBirth = in.readString();
        gender = in.readString();
        idType = in.readString();
        idNumber = in.readString();
        phoneNumber = in.readString();
        seatNumber = in.readInt();
        seatCategory = in.readString();
        seatPrice = in.readInt();
        country = in.readString();
        surname = in.readString();
        givenName = in.readString();
    }

    public static final Creator<PassengerInfoData> CREATOR = new Creator<PassengerInfoData>() {
        @Override
        public PassengerInfoData createFromParcel(Parcel in) {
            return new PassengerInfoData(in);
        }

        @Override
        public PassengerInfoData[] newArray(int size) {
            return new PassengerInfoData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dateOfBirth);
        parcel.writeString(gender);
        parcel.writeString(idType);
        parcel.writeString(idNumber);
        parcel.writeString(phoneNumber);
        parcel.writeInt(seatNumber);
        parcel.writeString(seatCategory);
        parcel.writeInt(seatPrice);
        parcel.writeString(country);
        parcel.writeString(surname);
        parcel.writeString(givenName);
    }
}

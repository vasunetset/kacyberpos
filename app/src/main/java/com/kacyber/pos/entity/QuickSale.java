package com.kacyber.pos.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mzy on 2017/10/11.
 */
public class QuickSale implements Parcelable {
    public String placeFrom;
    public String placeTo;
    public String date;
    public int passengerNum;

    public QuickSale() {
        super();
    }

    public QuickSale(Parcel in) {
        placeFrom = in.readString();
        placeTo = in.readString();
        date = in.readString();
        passengerNum = in.readInt();
    }

    public static final Creator<QuickSale> CREATOR = new Creator<QuickSale>() {
        @Override
        public QuickSale createFromParcel(Parcel in) {
            return new QuickSale(in);
        }

        @Override
        public QuickSale[] newArray(int size) {
            return new QuickSale[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeFrom);
        parcel.writeString(placeTo);
        parcel.writeString(date);
        parcel.writeInt(passengerNum);
    }
}

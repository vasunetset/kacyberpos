package com.kacyber.pos.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netset on 26/12/17.
 */

public class BookingModuleData implements Parcelable {
    private static BookingModuleData ourInstance;
    public int busFleetId;
    public String searchDateString;
    public long searchDateLong;
    public String FromCityName;
    public String FromCityId;
    public String TOCityId;
    public String TOCityName;
    public String currencyType;
    public String bookingType;// return / single
    public String busRoute;// return / single
    public double busOperatorCommission;
    public int selectboardingPointsId;
    public int selectDropingPointsId;
    public String selectboardingPointsName;
    public String selectDropingPointsName;
    public ArrayList<BusSeats.BoardingPoint> mBoardingPoints = new ArrayList<>();
    public ArrayList<BusSeats.DropingPoint> mDropingPoints = new ArrayList<>();

    public List<BusSeats.AcceptedPaymentMethod> acceptedPaymentMethods = new ArrayList<>();

    protected BookingModuleData(Parcel in) {
        busFleetId = in.readInt();
        searchDateString = in.readString();
        searchDateLong = in.readLong();
        FromCityName = in.readString();
        FromCityId = in.readString();
        TOCityId = in.readString();
        TOCityName = in.readString();
        currencyType = in.readString();
        bookingType = in.readString();
        busOperatorCommission = in.readDouble();
        selectboardingPointsId = in.readInt();
        selectDropingPointsId = in.readInt();
        selectboardingPointsName = in.readString();
        selectDropingPointsName = in.readString();
        acceptedPaymentMethods = in.createTypedArrayList(BusSeats.AcceptedPaymentMethod.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(busFleetId);
        dest.writeString(searchDateString);
        dest.writeLong(searchDateLong);
        dest.writeString(FromCityName);
        dest.writeString(FromCityId);
        dest.writeString(TOCityId);
        dest.writeString(TOCityName);
        dest.writeString(currencyType);
        dest.writeString(bookingType);
        dest.writeDouble(busOperatorCommission);
        dest.writeInt(selectboardingPointsId);
        dest.writeInt(selectDropingPointsId);
        dest.writeString(selectboardingPointsName);
        dest.writeString(selectDropingPointsName);
        dest.writeTypedList(acceptedPaymentMethods);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingModuleData> CREATOR = new Creator<BookingModuleData>() {
        @Override
        public BookingModuleData createFromParcel(Parcel in) {
            return new BookingModuleData(in);
        }

        @Override
        public BookingModuleData[] newArray(int size) {
            return new BookingModuleData[size];
        }
    };

    public static BookingModuleData getInstance() {
        if (ourInstance == null) {
            ourInstance = new BookingModuleData(Parcel.obtain());
        }
        return ourInstance;
    }

}



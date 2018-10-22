package com.kacyber.pos.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netset on 25/12/17.
 */

public class BusSeats {
    @SerializedName("seatStructures")
    @Expose
    public ArrayList<SeatStructure> seatStructures = new ArrayList<>();
    @SerializedName("column")
    @Expose
    public Integer column;

    @SerializedName("busOperatorCommission")
    @Expose
    public double busOperatorCommission;

    @SerializedName("acceptedPaymentMethods")
    @Expose
    public List<AcceptedPaymentMethod> acceptedPaymentMethods = new ArrayList<>();

    @SerializedName("boardingPoints")
    @Expose
    public ArrayList<BoardingPoint> boardingPoints = new ArrayList<>();

    @SerializedName("dropingPoints")
    @Expose
    public ArrayList<DropingPoint> dropingPoints = new ArrayList<>();

    public class SeatCategoryArray {

        @SerializedName("value")
        @Expose
        public String value;
        @SerializedName("key")
        @Expose
        public Integer key;

    }


    public class TypeArray {

        @SerializedName("value")
        @Expose
        public String value;
        @SerializedName("key")
        @Expose
        public Integer key;

    }

    public class DropingPoint {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("location")
        @Expose
        public String location;

    }

    public class BoardingPoint {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("location")
        @Expose
        public String location;

    }

    public static class SeatStructure implements Parcelable {
        @SerializedName("seatNo")
        @Expose
        public int seatNo;
        @SerializedName("ticketPrice")
        @Expose
        public int ticketPrice;
        @SerializedName("seatCategory")
        @Expose
        public String seatCategory;
        @SerializedName("type")
        @Expose
        public int type;
        @SerializedName("isBooked")
        @Expose
        public int isBooked;

        public SeatStructure(Parcel in) {
            if (in.readByte() == 0) {
                seatNo = 0;
            } else {
                seatNo = in.readInt();
            }
            if (in.readByte() == 0) {
                ticketPrice = 0;
            } else {
                ticketPrice = in.readInt();
            }
            seatCategory = in.readString();
            if (in.readByte() == 0) {
                type = 0;
            } else {
                type = in.readInt();
            }
            if (in.readByte() == 0) {
                isBooked = 0;
            } else {
                isBooked = in.readInt();
            }
        }

        public static Creator<SeatStructure> CREATOR = new Creator<SeatStructure>() {
            @Override
            public SeatStructure createFromParcel(Parcel in) {
                return new SeatStructure(in);
            }

            @Override
            public SeatStructure[] newArray(int size) {
                return new SeatStructure[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (seatNo == 0) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(seatNo);
            }
            if (ticketPrice == 0) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(ticketPrice);
            }
            dest.writeString(seatCategory);
            if (type == 0) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(type);
            }
            if (isBooked == 0) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(isBooked);
            }
        }
    }

    public static class AcceptedPaymentMethod implements Parcelable {

        @SerializedName("paymentMethod")
        @Expose
        public String paymentMethod;
        @SerializedName("id")
        @Expose
        public Integer id;


        protected AcceptedPaymentMethod(Parcel in) {
            paymentMethod = in.readString();
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
        }

        public static final Creator<AcceptedPaymentMethod> CREATOR = new Creator<AcceptedPaymentMethod>() {
            @Override
            public AcceptedPaymentMethod createFromParcel(Parcel in) {
                return new AcceptedPaymentMethod(in);
            }

            @Override
            public AcceptedPaymentMethod[] newArray(int size) {
                return new AcceptedPaymentMethod[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(paymentMethod);
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
        }
    }

    public BusSeats(Parcel in) {
        seatStructures = in.createTypedArrayList(SeatStructure.CREATOR);
        if (in.readByte() == 0) {
            column = null;
        } else {
            column = in.readInt();
        }
        busOperatorCommission = in.readDouble();
        acceptedPaymentMethods = in.createTypedArrayList(AcceptedPaymentMethod.CREATOR);
    }
}

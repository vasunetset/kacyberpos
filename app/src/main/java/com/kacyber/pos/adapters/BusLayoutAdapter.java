package com.kacyber.pos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kacyber.pos.R;
import com.kacyber.pos.interfaces.seatListener;
import com.kacyber.pos.models.BusSeats;
import com.kacyber.pos.util.Const;

import java.util.ArrayList;

/**
 *
 * Created by netset on 25/12/17.
 *
 */

public class BusLayoutAdapter extends ArrayAdapter<ArrayList<String>> implements View.OnClickListener {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private ArrayList<BusSeats.SeatStructure> busSeatsList = new ArrayList<>();
    private ArrayList<BusSeats.SeatStructure> selectedSeats = new ArrayList<>();
    private seatListener mListener;
    public enum SeatType {
        EMPTY, OCCUPIED, BOOKED, DRIVER, DOOR, INVIABLE, STAFF
    }
    public BusLayoutAdapter(@NonNull Context context, int resource, ArrayList<BusSeats.SeatStructure> seatStructures,
                            ArrayList<BusSeats.SeatStructure>
                                    busSeatsSelected, seatListener listener) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.busSeatsList = seatStructures;
        //this.selectedSeats = busSeatsSelected;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return busSeatsList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View grid = null;
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        grid = layoutInflater.inflate(R.layout.seat_selector, null);
        ImageView seat_image = (ImageView) grid.findViewById(R.id.seat_image);
        TextView seat_number = (TextView) grid.findViewById(R.id.seat_number);
        BusSeats.SeatStructure seats = busSeatsList.get(position);
        seat_image.setTag(seats);
       // seat_image.setVisibility(View.GONE);

        if (seats.type == Const.Invisible) {
            setSeatState(SeatType.INVIABLE, seat_image, false);
        } else if (seats.type == Const.Visible) {
            if (seats.isBooked == 1 || seats.isBooked == 2) {
                setSeatState(SeatType.OCCUPIED, seat_image, true);
            } else {
                setSeatState(SeatType.EMPTY, seat_image, true);
            }
        } else if (seats.type == Const.Door) {
            setSeatState(SeatType.DOOR, seat_image, false);
        } else if (seats.type == Const.Driver) {
            setSeatState(SeatType.DRIVER, seat_image, false);
        } else if (seats.type == Const.BOOKING) {
            setSeatState(SeatType.BOOKED, seat_image, true);
        } else if (seats.type == Const.STAFF) {
            setSeatState(SeatType.STAFF, seat_image, false);
        } else {
            setSeatState(SeatType.INVIABLE, seat_image, false);
        }

        if (seats.seatNo == 0 && seats.type == Const.STAFF) {
            seat_number.setText("S");
        } else if (seats.seatNo == 0) {
            seat_number.setText("");
        } else {
            seat_number.setText("" + seats.seatNo);
        }
        return grid;
    }

    private void setSeatState(SeatType seatState, ImageView imageView, boolean formUser) {
        imageView.setVisibility(View.VISIBLE);
        switch (seatState) {
            case BOOKED:
                imageView.setImageResource(R.drawable.seat_selected);
                break;
            case EMPTY:
                imageView.setImageResource(R.drawable.seat_empty);
                break;
            case DOOR:
                imageView.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.stearing_wheel);
                break;
            case OCCUPIED:
                imageView.setImageResource(R.drawable.seat_occupied);
                break;
            case DRIVER:
                imageView.setImageResource(R.drawable.stearing_wheel);
                imageView.setPadding(0, 10, 0, 0);
                break;
            case STAFF:
                imageView.setImageResource(R.drawable.seat_occupied);
                imageView.setVisibility(View.VISIBLE);
                break;
            case INVIABLE:
                imageView.setImageResource(R.drawable.stearing_wheel);
                imageView.setVisibility(View.INVISIBLE);
                break;
        }
        if (formUser) {
            imageView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        BusSeats.SeatStructure seats = (BusSeats.SeatStructure) v.getTag();
        int pos = busSeatsList.lastIndexOf(seats);
        if (selectedSeats.size() >= 5) {
            if (selectedSeats.contains(seats)) {
                seats.type = Const.Visible;
                busSeatsList.set(pos, seats);
                selectedSeats.remove(seats);
                holdUnHoldSeat(seats, false);
            }
        } else {
            if (seats.isBooked == 1 || seats.isBooked == 2) {
                Toast.makeText(mContext, "This seat already booked please try other one", Toast.LENGTH_SHORT).show();
            } else {
                if (seats.type == Const.Visible) {
                    seats.type = Const.BOOKING;
                    busSeatsList.set(pos, seats);
                    selectedSeats.add(seats);
                    holdUnHoldSeat(seats, true);
                } else if (seats.type == Const.BOOKING) {
                    seats.type = Const.Visible;
                    busSeatsList.set(pos, seats);
                    selectedSeats.remove(seats);
                    holdUnHoldSeat(seats, false);
                    // un load
                }
            }
        }
        notifyDataSetChanged();
        if (mListener != null) {
            mListener.getSelectSeats(selectedSeats);
        }
    }

    private void holdUnHoldSeat(BusSeats.SeatStructure seats, boolean ishold) {
        if (mListener != null) {
            mListener.getHodlSeats(seats, ishold);
        }
    }
}

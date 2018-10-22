package com.kacyber.pos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kacyber.pos.R;
import com.kacyber.pos.interfaces.seatListener;
import com.kacyber.pos.models.BusSeats;
import com.kacyber.pos.util.Const;

import java.util.ArrayList;

/**
 * Created by netset on 28/4/18.
 */

public class BusLayoutRecyclerview extends RecyclerView.Adapter<BusLayoutRecyclerview.ViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<BusSeats.SeatStructure> busSeatsList = new ArrayList<>();
    private ArrayList<BusSeats.SeatStructure> selectedSeats = new ArrayList<>();
    private seatListener mListener;
    private ItemClickListener mClickListener;

    private enum SeatType {
        EMPTY, OCCUPIED, BOOKED, DRIVER, DOOR, INVIABLE, STAFF
    }

    public BusLayoutRecyclerview(@NonNull Context context, int resource, ArrayList<BusSeats.SeatStructure> seatStructures,
                                 ArrayList<BusSeats.SeatStructure>
                                         busSeatsSelected, seatListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.busSeatsList = seatStructures;
        //this.selectedSeats = busSeatsSelected;
        this.mListener = listener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.seat_selector, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusSeats.SeatStructure seats = busSeatsList.get(position);
        holder.seat_image.setTag(seats);
        if (seats.type == Const.Invisible) {
            setSeatState(BusLayoutAdapter.SeatType.INVIABLE, holder.seat_image, false);
        } else if (seats.type == Const.Visible) {
            if (seats.isBooked == 1 || seats.isBooked == 2) {
                setSeatState(BusLayoutAdapter.SeatType.OCCUPIED, holder.seat_image, true);
            } else {
                setSeatState(BusLayoutAdapter.SeatType.EMPTY, holder.seat_image, true);
            }
        } else if (seats.type == Const.Door) {
            setSeatState(BusLayoutAdapter.SeatType.DOOR, holder.seat_image, false);
        } else if (seats.type == Const.Driver) {
            setSeatState(BusLayoutAdapter.SeatType.DRIVER, holder.seat_image, false);
        } else if (seats.type == Const.BOOKING) {
            setSeatState(BusLayoutAdapter.SeatType.BOOKED, holder.seat_image, true);
        } else if (seats.type == Const.STAFF) {
            setSeatState(BusLayoutAdapter.SeatType.STAFF, holder.seat_image, false);
        } else {
            setSeatState(BusLayoutAdapter.SeatType.INVIABLE, holder.seat_image, false);
        }

        if (seats.seatNo == 0 && seats.type == Const.STAFF) {
            holder.seat_number.setText("S");
        } else if (seats.seatNo == 0) {
            holder.seat_number.setText("");
        } else {
            holder.seat_number.setText("" + seats.seatNo);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return busSeatsList.size();
    }


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView seat_number;
        ImageView seat_image;

        ViewHolder(View itemView) {
            super(itemView);
            seat_image = (ImageView) itemView.findViewById(R.id.seat_image);
            seat_number = (TextView) itemView.findViewById(R.id.seat_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    private void setSeatState(BusLayoutAdapter.SeatType seatState, ImageView imageView, boolean formUser) {
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
                default:
                    imageView.setVisibility(View.INVISIBLE);

        }
        if (formUser) {
            imageView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        BusSeats.SeatStructure seats = (BusSeats.SeatStructure) view.getTag();
        int pos = busSeatsList.lastIndexOf(seats);
        if (selectedSeats.size() >= 5) {
            if (selectedSeats.contains(seats)) {
                seats.type = Const.Visible;
                busSeatsList.set(pos, seats);
                selectedSeats.remove(seats);
                holdUnHoldSeat(seats, false);
            } else {
                Toast.makeText(mContext, R.string.more_than_5_seats, Toast.LENGTH_SHORT).show();
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
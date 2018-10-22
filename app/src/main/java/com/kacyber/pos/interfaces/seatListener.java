package com.kacyber.pos.interfaces;

import com.kacyber.pos.models.BusSeats;
import java.util.ArrayList;
public interface seatListener {
    public void getSelectSeats(ArrayList<BusSeats.SeatStructure> structures);
    public void getHodlSeats(BusSeats.SeatStructure data, boolean ishold);
}
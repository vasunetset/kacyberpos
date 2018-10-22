package com.kacyber.pos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kacyber.pos.R;
import com.kacyber.pos.interfaces.OnSelectCallBack;
import com.kacyber.pos.models.ServicesData;
import com.kacyber.pos.ui.fragment.AllServicesFragment;

import java.util.List;

import butterknife.ButterKnife;

public class AllServicesAdapter extends RecyclerView.Adapter<AllServicesAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    List<ServicesData.ListData> dataList;
    static int selectedItem;
    private OnSelectCallBack clickCallback;
    AllServicesFragment fragment;
    public static boolean selectALL = true;

    public AllServicesAdapter(AllServicesFragment context, List<ServicesData.ListData> data) {
        this.dataList = data;
        this.layoutInflater = LayoutInflater.from(context.getActivity());
        this.fragment = context;
    }

    public void selectAll() {
        selectALL = true;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AllServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView;
        contentView = layoutInflater.inflate(R.layout.services_item_layout, parent, false);
        return new ViewHolder(contentView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull AllServicesAdapter.ViewHolder holder, int position) {


        if (selectALL) {
            holder.checkIV.setVisibility(View.VISIBLE);
        } else {
            if (position == selectedItem) {
                holder.checkIV.setVisibility(View.VISIBLE);
            } else {
                holder.checkIV.setVisibility(View.INVISIBLE);
            }
        }

        holder.titleTV.setText(dataList.get(position).busRoute + " (" + dataList.get(position).startTime + ")");
    }

 /*   @Override
    public void onBindViewHolder(@NonNull AllServicesAdapter.ViewHolder holder, int position) {
        holder.userNameTV.setText("Passenger Name: " + dataList.get(position).passangerName);
        holder.companyTV.setText("Company Name: " + dataList.get(position).name);
        holder.priceTV.setText("UGX " + dataList.get(position).totalFair);
        holder.busRouteTV.setText("Bus Route: " + dataList.get(position).busRoute);
        holder.bookingDateTV.setText("Booking Date: " + dataList.get(position).bookingOfDate);
        holder.bookedByName.setText("Booking by: " + dataList.get(position).bookedByName);
        holder.bookingFrom.setText("Booking From: " + dataList.get(position).bookingFrom);
        holder.ticketNo.setText("Ticket No: " + dataList.get(position).eTicket);
    }*/

    @Override
    public int getItemCount() {
        return dataList.size();
    }

   /* public void notifyDataChange(List<ReportData.Datum> data) {
        this.dataList = data;
        notifyDataSetChanged();
    }*/


    public void setCallBack(OnSelectCallBack callBack) {
        clickCallback = callBack;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemViewRL;
        ImageView checkIV;
        TextView titleTV;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemViewRL = itemView.findViewById(R.id.itemViewRL);
            checkIV = itemView.findViewById(R.id.checkIV);
            titleTV = itemView.findViewById(R.id.title);
            itemViewRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectALL = false;
                    selectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                    clickCallback.onSelect(selectedItem, dataList.get(selectedItem).id + "");
                    fragment.dismiss();
                }
            });
        }

    }

    public static String getMinutesFromMillis(Long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60)) % 60);
    }
}

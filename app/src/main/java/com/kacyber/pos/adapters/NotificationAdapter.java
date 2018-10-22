package com.kacyber.pos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kacyber.pos.R;
import com.kacyber.pos.models.SearchResultModel;
import com.kacyber.pos.ui.NotificationActivity;
import com.kacyber.pos.ui.SearchResultsActivity;

import java.util.List;

/**
 * Created by netset on 27/7/18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private final LayoutInflater layoutInflater;

    public NotificationAdapter(Context context, List<SearchResultModel.SearchResultBusList> busList, NotificationActivity searchResultsActivity) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = layoutInflater.inflate(R.layout.notification_adapter, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       /* SearchResultModel.SearchResultBusList bus=searchResultModelList.get(position);
        holder.nameTextView.setText(bus.name);
        holder.infoTextView.setText(String.format("%s To %s",bus.sourceLocation,bus.destinationLocation));
        holder.timeTextView.setText(String.format("%s-%s\n%s",bus.startTime,bus.endTime,bus.started));
        holder.priceTextView.setText(String.format("%s %s",currency,bus.price));
        holder.seatsTextView.setText(String.format(Locale.getDefault(),"%d %s",bus.totalSeats,seats));
        holder.itemView.setTag(bus);
        if(bus.busCompanyLogo!=null&&!bus.busCompanyLogo.equals("")){
        Picasso.with(holder.iconImageview.getContext()).load(bus.busCompanyLogo)
        .placeholder(R.drawable.bus_w_background).into(holder.iconImageview);
        }*/
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView buName, description;
        ImageView busImage;

        private ViewHolder(View itemView) {
            super(itemView);
            busImage=itemView.findViewById(R.id.circleImageView);
            buName=itemView.findViewById(R.id.busName);
            description=itemView.findViewById(R.id.description);

        }

    }

    public static String getMinutesFromMillis(Long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60)) % 60);
    }
}


package com.kacyber.pos.adapters;

/**
 * Created by Mostafa on 10/01/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kacyber.pos.R;
import com.kacyber.pos.models.SearchResultModel;
import com.kacyber.pos.ui.SearchResultsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;


public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private List<SearchResultModel.SearchResultBusList> searchResultModelList;
    private String seats;
    // private String currency;
    private LayoutInflater layoutInflater;
    private SearchResultsActivity searchResultsActivity;

    public SearchResultsAdapter(Context context, List<SearchResultModel.SearchResultBusList> busList, SearchResultsActivity searchResultsActivity) {
        this.layoutInflater = LayoutInflater.from(context);
        this.searchResultModelList = busList;
        this.searchResultsActivity = searchResultsActivity;
        this.seats = " seats";
        //this.currency = "UGX";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = layoutInflater.inflate(R.layout.list_item_search_result, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchResultModel.SearchResultBusList bus = searchResultModelList.get(position);
        holder.nameTextView.setText(bus.name);
        holder.infoTextView.setText(bus.busRoute);
        holder.timeTextView.setText(String.format("%s-%s", bus.startTime, bus.endTime));
        holder.priceTextView.setText(String.format("%s %s", bus.currency, bus.price));
        holder.seatsTextView.setText(bus.totalSeats + "" + seats + "\n" + bus.freeSeats + " available");
        //holder.seatsTextView.setText(String.format(Locale.getDefault(), "%d %s", bus.totalSeats, seats));
        holder.itemView.setTag(bus);
        if (bus.busCompanyLogo != null && !bus.busCompanyLogo.equals("")) {
            Picasso.with(holder.iconImageview.getContext()).load(bus.busCompanyLogo)
                    .placeholder(R.drawable.bus_w_background).into(holder.iconImageview);
        }
    }

    @Override
    public int getItemCount() {
        return searchResultModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView infoTextView;
        TextView timeTextView;
        TextView durationTextView;
        TextView priceTextView;
        TextView seatsTextView;
        ImageView iconImageview;

        private ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            infoTextView = itemView.findViewById(R.id.info_text_view);
            timeTextView = itemView.findViewById(R.id.time_text_view);
            durationTextView = itemView.findViewById(R.id.duration_text_view);
            priceTextView = itemView.findViewById(R.id.price_text_view);
            seatsTextView = itemView.findViewById(R.id.seats_text_view);
            iconImageview = itemView.findViewById(R.id.icon_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchResultsActivity.itemClick(getAdapterPosition());
                }
            });
        }
    }

    public static String getMinutesFromMillis(Long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60)) % 60);
    }
}


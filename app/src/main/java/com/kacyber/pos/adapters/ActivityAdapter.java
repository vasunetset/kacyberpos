package com.kacyber.pos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kacyber.pos.R;
import com.kacyber.pos.interfaces.OnSelectCallBack;
import com.kacyber.pos.models.ActivityData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<ActivityData.Datum> dataList;
    OnSelectCallBack onSelectCallBack;

    public ActivityAdapter(Context context, List<ActivityData.Datum> data) {
        this.dataList = data;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView;
        contentView = layoutInflater.inflate(R.layout.acticvity_list_layout, parent, false);
        return new ViewHolder(contentView) {
        };
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.roteTV.setText(dataList.get(position).busRoute);
        holder.bookedByNameTV.setText(dataList.get(position).firstPassangerName);
        holder.priceTV.setText("UGX " + dataList.get(position).totalFair);
        holder.busName.setText(dataList.get(position).busCompanyName);
        holder.statusTV.setText(getbokkingStatus(dataList.get(position).bookingStatusId + ""));

        holder.bookedFromTV.setText(
                String.format("Via %s by %s on %s", dataList.get(position).bookingFrom,
                        dataList.get(position).bookedByName,
                        convertDataFormate(dataList.get(position).bookingOfDate)));

    }
//Via POS by Olara on 27 Sept 2018

    private String convertDataFormate(String date) {
        Date mdate = null;
        String convertDate = "";
        try {
            mdate = simpleDateFormat.parse(date);
            SimpleDateFormat convertdate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            convertDate = convertdate.format(mdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertDate;
    }

    private String getbokkingStatus(String status) {

        if (status == null || status.equals("null")) {
            return "";
        } else if (status.equals("1")) {
            return "Booked";
        } else if (status.equals("2")) {
            return "Verified";
        } else if (status.equals("3")) {
            return "Cancelled";
        } else if (status.equals("4")) {
            return "PaymentFailed";
        } else {
            return "";
        }
    }

    public void onItemSelect(OnSelectCallBack callBack) {
        this.onSelectCallBack = callBack;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void notifyDataChange(List<ActivityData.Datum> activityData) {
        this.dataList = activityData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.roteTV)
        TextView roteTV;
        @BindView(R.id.bookedByNameTV)
        TextView bookedByNameTV;
        @BindView(R.id.bookedFromTV)
        TextView bookedFromTV;
        @BindView(R.id.priceTV)
        TextView priceTV;
        @BindView(R.id.busIcon)
        ImageView busIcon;
        @BindView(R.id.busName)
        TextView busName;
        @BindView(R.id.statusTV)
        TextView statusTV;
        @BindView(R.id.parentLL)
        LinearLayout parentLL;
        @BindView(R.id.dateLayoutRL)
        RelativeLayout dateLayoutRL;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            parentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSelectCallBack.onSelect(getAdapterPosition(), "");
                }
            });

        }
    }

    public static String getMinutesFromMillis(Long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60)) % 60);
    }
}

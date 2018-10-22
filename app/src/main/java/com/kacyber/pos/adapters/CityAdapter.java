package com.kacyber.pos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kacyber.pos.R;
import com.kacyber.pos.models.CityModel;
import com.kacyber.pos.recycalView.FastScrollRecyclerViewInterface;
import com.kacyber.pos.ui.CityListActivity;

/**
 * Created by netset on 26/3/18.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder>implements Filterable,FastScrollRecyclerViewInterface {
    Context context;
    ArrayList<CityModel> cityModelList;
    ArrayList<CityModel> filterItems;
    CityListActivity cityListActivity;
    private HashMap<String, Integer> mMapIndex;

    public CityAdapter(Context context, ArrayList<CityModel> cityModelList,CityListActivity cityListActivity,HashMap<String, Integer> Index) {
        this.context = context;
        this.cityModelList = cityModelList;
        this.cityListActivity=cityListActivity;
        this.filterItems = cityModelList;
        this.mMapIndex=Index;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext())
               .inflate(R.layout.item_city,viewGroup,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        CityModel cityModel=filterItems.get(i);
        myViewHolder.cityNameTv.setText(cityModel.getName());
    }

    @Override
    public int getItemCount() {
        return filterItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                List<CityModel> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered = cityModelList;
                } else {
                    for (CityModel movie : cityModelList) {
                        if (movie.getName().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(movie);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filterItems = (ArrayList<CityModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public HashMap<String, Integer> getMapIndex() {
        return this.mMapIndex;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            cityNameTv=itemView.findViewById(R.id.txt_city_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int po=getAdapterPosition();
                    ((CityListActivity) cityListActivity).itemClick(filterItems.get(po),getAdapterPosition());
                }
            });
        }
    }
}

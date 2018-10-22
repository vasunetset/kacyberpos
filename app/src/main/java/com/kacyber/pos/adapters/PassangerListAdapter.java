package com.kacyber.pos.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.kacyber.pos.R;
import com.kacyber.pos.ui.CartActivity;


/**
 * Created by netset on 26/12/17.
 */

public class PassangerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public enum Mode {TEXT, TEXT_WO_CHEVRON, CHECKABLE, CHECKABLE_SINGLE_ITEM}
    String[] items;
    private boolean[] checkedItems;
    private LayoutInflater layoutInflater;
    private int colorPrimary;
    private int colorAccent;
    private boolean checkable;
    private boolean singleItem;
    private boolean showChevron;
    CartActivity cartActivity;
   // private OnItemClickListener<String> onItemClickListener;
    Mode mMode;

    public PassangerListAdapter(Context context, String[] items,Mode mode,CartActivity cartActivity) {
        this.layoutInflater = LayoutInflater.from(context);
        this.items = items;
        this.checkedItems = new boolean[items.length];
        this.cartActivity=cartActivity;
        this.colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary);
        this.colorAccent = ContextCompat.getColor(context, R.color.colorAccent);
        this.mMode = mode;
        switch (mode) {
            case TEXT:
                showChevron = true;
                break;
            case CHECKABLE:
                checkable = true;
                break;
            case CHECKABLE_SINGLE_ITEM:
                checkable = true;
                singleItem = true;
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView;
        if (checkable) {
            contentView = layoutInflater.inflate(R.layout.list_item_passenger, parent, false);
        } else {
            contentView = layoutInflater.inflate(R.layout.list_item_passenger, parent, false);
            if (!showChevron) {
                ((TextView) contentView).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
        return new RecyclerView.ViewHolder(contentView) {
        };
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (holder.itemView instanceof TextView) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(items[position]);
            if (holder.itemView instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) holder.itemView;
                checkBox.setText(items[position]);
                checkBox.setTextColor(checkedItems[position] ? colorAccent : colorPrimary);
                checkBox.setChecked(checkedItems[position]);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = ((int) view.getTag());
                if (view instanceof CheckBox) {
                    if (singleItem) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                checkedItems[i] = false;
                                notifyItemChanged(i);
                            }
                        }
                    }
                    checkedItems[position] = ((CheckBox) view).isChecked();
                    notifyItemChanged(position);
                }else {
                    ((CartActivity) cartActivity).itemClick(position);
                }

            }
        });
    }

    public String[] getCheckedItems() {
        List<String> checkedItems = new ArrayList<>();
        boolean[] checkedItems1 = this.checkedItems;
        for (int i = 0; i < checkedItems1.length; i++) {
            if (checkedItems1[i]) {
                checkedItems.add(items[i]);
            }
        }
        return checkedItems.toArray(new String[0]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

}

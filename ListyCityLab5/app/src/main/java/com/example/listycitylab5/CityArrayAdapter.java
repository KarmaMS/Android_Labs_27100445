package com.example.listycitylab5;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {
    private int selectedPosition = ListView.INVALID_POSITION;

    public CityArrayAdapter(@NonNull Context context, @NonNull ArrayList<City> cities) {
        super(context, 0, cities);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        MaterialCardView card;
        TextView name;
        TextView province;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
            holder = new ViewHolder();
            holder.card = (MaterialCardView) convertView;
            holder.name = convertView.findViewById(R.id.city_name);
            holder.province = convertView.findViewById(R.id.city_province);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        City city = getItem(position);
        if (city != null) {
            holder.name.setText(city.getName());
            holder.province.setText(city.getProvince());
        }

        boolean isSelected = position == selectedPosition;
        holder.card.setCardBackgroundColor(isSelected ? Color.parseColor("#FFD6EFFF") : Color.WHITE);
        holder.card.setStrokeWidth(isSelected ? 4 : 0);
        holder.card.setStrokeColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark));

        return convertView;
    }
}

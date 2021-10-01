package com.example.bank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends BaseAdapter {

    ArrayList<Places> places = new ArrayList<>();
    Context mContext;


    public PlacesAdapter( Context context, ArrayList<Places> data) {
        this.places = data;
        mContext = context;

    }

    public int getCount() {
        return places.size();
    }

    public Object getItem(int position) {
        return places.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Places current = (Places) getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.atm_list_item, parent, false);
        }
            TextView address = convertView.findViewById(R.id.address);
            TextView type = convertView.findViewById(R.id.type);
            TextView available = convertView.findViewById(R.id.available);
            TextView time = convertView.findViewById(R.id.time);

        address.setText(current.getAddress());
        type.setText(current.getType());
        if (current.getAvailable()) {
            available.setText("Работает");
            available.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        } else {
            available.setText("Закрыто");
            available.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        time.setText(current.getTime());

        return convertView;
    }

}

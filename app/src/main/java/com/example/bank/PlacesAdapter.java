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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat hours = new SimpleDateFormat("hh", Locale.getDefault());
        SimpleDateFormat minutes = new SimpleDateFormat("mm", Locale.getDefault());
        int currentHour = Integer.parseInt(hours.format(date));
        int currentMinute = Integer.parseInt(minutes.format(date));

        String[] open = current.getTime().open.split(":");
        String[] close = current.getTime().close.split(":");
        int openHour = Integer.parseInt(open[0]);
        int openMinute = Integer.parseInt(open[1]);
        int closeHour = Integer.parseInt(close[0]);
        int closeMinute = Integer.parseInt(close[1]);

        boolean status = false;
        if (openHour <= currentHour && closeHour >= currentHour ) {
            status = true;
            if (openHour == currentHour) {
                if (currentMinute < openMinute) {
                    status = false;
                }
            } else if (closeHour == currentHour) if (currentMinute > closeMinute) {
                status = false;
            }
        }
        if (status) {
            available.setText("Работает");
            available.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        } else {
            available.setText("Закрыто");
            available.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        time.setText(current.getTime().open + " - " + current.getTime().close);

        return convertView;
    }

}

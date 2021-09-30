package com.example.bank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {
    ArrayList<Integer> id;
    ArrayList<String> address;
    ArrayList<Boolean> available;
    ArrayList<String> type;
    ArrayList<String> time;
    Context context;

    public PlacesAdapter(Context context, ArrayList<Integer> id,
                         ArrayList<String> address,
                         ArrayList<Boolean> available,
                         ArrayList<String> type,
                         ArrayList<String> time) {
        this.context = context;
        this.id = id;
        this.address = address;
        this.available = available;
        this.type = type;
        this.time = time;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.atm_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.address.setText(address.get(position));
        if (available.get(position)) {
            holder.available.setText("Работает");
            holder.available.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            holder.available.setText("Закрыто");
            holder.available.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.type.setText(type.get(position));
        holder.time.setText(time.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, id.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address, type, available, time;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            address = itemView.findViewById(R.id.address);
            type = itemView.findViewById(R.id.type);
            available = itemView.findViewById(R.id.available);
            time = itemView.findViewById(R.id.time);

        }
    }
}

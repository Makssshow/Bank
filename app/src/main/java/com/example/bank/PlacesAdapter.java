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
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends ArrayAdapter<Places> {
//    ArrayList<Integer> id;
//    ArrayList<String> address;
//    ArrayList<Boolean> available;
//    ArrayList<String> type;
//    ArrayList<String> time;

//    Context context;
//
//    public PlacesAdapter(Context context, ArrayList<Integer> id,
//                         ArrayList<String> address,
//                         ArrayList<Boolean> available,
//                         ArrayList<String> type,
//                         ArrayList<String> time) {
//        this.context = context;
//        this.id = id;
//        this.address = address;
//        this.available = available;
//        this.type = type;
//        this.time = time;
//    }

    private ArrayList<Places> places;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView address, type, available, time;// init the item view's
    }

    public PlacesAdapter(ArrayList<Places> data, Context context) {
        super(context, R.layout.atm_list_item, data);
        this.places = data;
        this.mContext = context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Places current = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.atm_list_item, parent, false);
            viewHolder.address = convertView.findViewById(R.id.address);
            viewHolder.type = convertView.findViewById(R.id.type);
            viewHolder.available = convertView.findViewById(R.id.available);
            viewHolder.time = convertView.findViewById(R.id.time);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.address.setText(current.getAddress());
        viewHolder.type.setText(current.getType());
        if (current.getAvailable()) {
            viewHolder.available.setText("Работает");
            viewHolder.available.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        } else {
            viewHolder.available.setText("Закрыто");
            viewHolder.available.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        viewHolder.time.setText(current.getTime());
        // Return the completed view to render on screen
        return convertView;
    }

//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // infalte the item Layout
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.atm_list_item, parent, false);
//        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        // set the data in items
//        holder.address.setText(address.get(position));
//        if (available.get(position)) {
//            holder.available.setText("Работает");
//            holder.available.setTextColor(ContextCompat.getColor(context, R.color.green));
//        } else {
//            holder.available.setText("Закрыто");
//            holder.available.setTextColor(ContextCompat.getColor(context, R.color.red));
//        }
//        holder.type.setText(type.get(position));
//        holder.time.setText(time.get(position));
//        // implement setOnClickListener event on item view.
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // display a toast with person name on item click
//                Toast.makeText(context, id.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return id.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView address, type, available, time;// init the item view's
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//
//            // get the reference of item view's
//            address = itemView.findViewById(R.id.address);
//            type = itemView.findViewById(R.id.type);
//            available = itemView.findViewById(R.id.available);
//            time = itemView.findViewById(R.id.time);
//
//        }
//    }
}

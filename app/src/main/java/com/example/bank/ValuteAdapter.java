package com.example.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ValuteAdapter extends BaseAdapter {
    ArrayList<Valute> data = new ArrayList<>();
    ArrayList<Boolean> more = new ArrayList<>();
    Context context = null;


    public ValuteAdapter(Context c, ArrayList<Valute> data, ArrayList<Boolean> more) {
        this.data = data;
        this.more = more;
        context = c;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Valute current = (Valute) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        }

        ImageView flag = convertView.findViewById(R.id.course_flag);
        TextView title = convertView.findViewById(R.id.course_title);
        TextView name = convertView.findViewById(R.id.course_name);
        TextView buy = convertView.findViewById(R.id.course_buy);
        TextView sell = convertView.findViewById(R.id.course_sell);
        ImageView buyArrow = convertView.findViewById(R.id.course_buy_arrow);
        ImageView sellArrow = convertView.findViewById(R.id.course_sell_arrow);

        Random random = new Random();
        float intNum = random.nextInt(11);
        float randomNum = intNum / 10000;

        title.setText(current.getCharCode());
        name.setText(current.getName());
        buy.setText(String.format("%.2f", Float.parseFloat(current.getValue().replace(',', '.'))));
        sell.setText(String.format("%.2f", (Float.parseFloat(current.getValue().replace(',', '.')) + (Float.parseFloat(current.getValue().replace(',', '.')) * randomNum))));
        int resID = context.getResources().getIdentifier(current.getCharCode().toLowerCase(Locale.ROOT), "drawable", context.getPackageName());
        flag.setImageResource(resID);

        if (more.get(position)) {
            buyArrow.setImageResource(R.drawable.arrow_up);
        } else {
            buyArrow.setImageResource(R.drawable.arrow_down);
        }


        return convertView;
    }
}

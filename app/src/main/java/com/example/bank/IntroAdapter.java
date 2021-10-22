package com.example.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroAdapter extends PagerAdapter {

    Context mContext;
    List<Intro> mListIntro;

    public IntroAdapter(Context mContext, List<Intro> mListIntro) {
        this.mContext = mContext;
        this.mListIntro = mListIntro;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.intro_layout, null);

        ImageView image = view.findViewById(R.id.intro_image);
        TextView title = view.findViewById(R.id.intro_title);
        TextView description = view.findViewById(R.id.intro_description);

        title.setText(mListIntro.get(position).getTitle());
        description.setText(mListIntro.get(position).getDescription());
        image.setImageResource(mListIntro.get(position).getImage());

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mListIntro.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

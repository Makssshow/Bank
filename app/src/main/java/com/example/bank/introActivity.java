package com.example.bank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class introActivity extends AppCompatActivity {
Button finish;
    private ViewPager viewPager;
    IntroAdapter introAdapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.introPager);
        finish = findViewById(R.id.intro_finish);
        tabLayout = findViewById(R.id.intro_tab);

        finish.setOnClickListener(v -> {
            Intent intent = new Intent(introActivity.this, MainActivity.class);
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("tutorial", true);
            editor.apply();


            startActivity(intent);
            finish();

        });

        List<Intro> mList = new ArrayList<>();

        mList.add(new Intro(R.drawable.ae, "Title 1", "Description 1"));
        mList.add(new Intro(R.drawable.la, "Title 2", "Description 2"));
        mList.add(new Intro(R.drawable.se, "Title 3", "Description 3"));

        introAdapter = new IntroAdapter(this, mList);
        viewPager.setAdapter(introAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position + 1 == mList.size()) {
                    finish.setVisibility(View.VISIBLE);
                } else {
                    finish.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


    }
}
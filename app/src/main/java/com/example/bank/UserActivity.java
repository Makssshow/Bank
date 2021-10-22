package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    ImageView image;
    TextView name, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        image = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);

        Intent intent = getIntent();
        ArrayList<String> data = intent.getStringArrayListExtra("User");

        name.setText(data.get(0));
        desc.setText(data.get(1));
    }
}
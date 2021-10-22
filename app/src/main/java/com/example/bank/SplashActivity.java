package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 300;


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // По истечении времени, запускаем главный активити, а Splash Screen закрываем
            pref = getApplicationContext().getSharedPreferences("pref", 0);
            checkToken();
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkToken() {
        if (!pref.getString("token", "").isEmpty()) {
            NetworkService.getInstance().getJSONApi().getRoleById(pref.getString("token", "")).enqueue(new Callback<Role>() {
                @Override
                public void onResponse(Call<Role> call, Response<Role> response) {
                    Role role = response.body();
                    if (role != null) {
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        ArrayList<String> array = new ArrayList<>();
                        array.add(role.getName());
                        array.add(role.getDescription());
                        intent.putExtra("User", array);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }
                }

                @Override
                public void onFailure(Call<Role> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            });
        }
    }
}
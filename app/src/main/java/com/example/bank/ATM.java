package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

public class ATM extends AppCompatActivity {
    ListView list;
    ProgressBar pb;
    ArrayList<Places> arrayATM = new ArrayList<>();

    private static PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);

        list = findViewById(R.id.listATM);
        pb = findViewById(R.id.progressBar);

        new Thread(() -> {
            try {
                String data = download();
                // get JSONObject from JSON file
                JSONObject obj = new JSONObject(data);
                // fetch JSONArray named users
                JSONArray PlacesArray = obj.getJSONArray("ATM");
                // implement for loop for getting users list data
                if (!arrayATM.isEmpty()) {
                    arrayATM.clear();
                }

                for (int i = 0; i < PlacesArray.length(); i++) {
                    // create a JSONObject for fetching single user data
                    JSONObject PlacesDetails = PlacesArray.getJSONObject(i);
                    // fetch email and name and store it in arraylist
                    arrayATM.add(new Places(PlacesDetails.getInt("id"), PlacesDetails.getString("address"), PlacesDetails.getBoolean("available"), PlacesDetails.getString("type"), PlacesDetails.getString("time")));
                }
                runOnUiThread(() -> {
                    adapter= new PlacesAdapter( ATM.this, arrayATM);
                    list.setAdapter(adapter);
                    pb.setVisibility(View.INVISIBLE);
                });

            } catch (JSONException e) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
                e.printStackTrace();
            } catch (IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
                e.printStackTrace();
            }
        }).start();



    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
        finish();
//        Intent intent = MainActivity.class;
//        startActivity();
        super.onBackPressed();
    }

    private String download() throws IOException {
        StringBuilder result = new StringBuilder();
        HttpsURLConnection connection = null;
        InputStream is = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://makssshow.github.io/Bank/app/src/main/res/raw/atm.json");
            connection = (HttpsURLConnection) url.openConnection();
            is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ATM extends AppCompatActivity {
    RecyclerView list;

    ArrayList<String> arrayATM;

    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<Boolean> available = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);

        list = findViewById(R.id.listATM);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(linearLayoutManager);


        class aboba extends Thread {
            String data = "";

            public void rund() {
                try {
                    URL url = new URL("https://makssshow.github.io/Bank/app/src/main/res/raw/atm.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data = data + line;
                    }

                    if (!data.isEmpty()) {
                        // get JSONObject from JSON file
                        JSONObject obj = new JSONObject(data);
                        // fetch JSONArray named users
                        JSONArray PlacesArray = obj.getJSONArray("ATM");
                        // implement for loop for getting users list data
                        arrayATM.clear();
                        for (int i = 0; i < PlacesArray.length(); i++) {
                            // create a JSONObject for fetching single user data
                            JSONObject PlacesDetails = PlacesArray.getJSONObject(i);
                            // fetch email and name and store it in arraylist
                            arrayATM.add(PlacesDetails.toString());
                            id.add(PlacesDetails.getInt("id"));
                            address.add(PlacesDetails.getString("address"));
                            available.add(PlacesDetails.getBoolean("available"));
                            type.add(PlacesDetails.getString("type"));
                            time.add(PlacesDetails.getString("time"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            //  call the constructor of CustomAdapter to send the reference and data to Adapter
            PlacesAdapter customAdapter = new PlacesAdapter(ATM.this, id, address, available, type, time);
        list.setAdapter(customAdapter); // set the Adapter to RecyclerView
            }
        }
    }
}

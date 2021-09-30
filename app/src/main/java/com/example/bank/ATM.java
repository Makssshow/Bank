package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import javax.net.ssl.HttpsURLConnection;

public class ATM extends AppCompatActivity {
    ListView list;
    TextView a;

    ArrayList<Places> arrayATM = new ArrayList<>();

    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<Boolean> available = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();

    private static PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);

        list = findViewById(R.id.listATM);
        a = findViewById(R.id.texta);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        list.setLayoutManager(linearLayoutManager);

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
                    id.add(PlacesDetails.getInt("id"));
                    address.add(PlacesDetails.getString("address"));
                    available.add(PlacesDetails.getBoolean("available"));
                    type.add(PlacesDetails.getString("type"));
                    time.add(PlacesDetails.getString("time"));

                    runOnUiThread(() -> {
                            a.setText(type.toString());

                    });

                }
                runOnUiThread(() -> {
//                    a.setText(arrayATM.toString());
                });

            } catch (JSONException e) {
                runOnUiThread(() -> {
//                    a.setText(e.getMessage());
                });
                e.printStackTrace();
            } catch (IOException e) {
                runOnUiThread(() -> {

//                    a.setText(e.getMessage());
                });
                e.printStackTrace();
            }
        }).start();

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        adapter = new PlacesAdapter(arrayATM, getApplicationContext());
        list.setAdapter(adapter); // set the Adapter to RecyclerView
//        adapter= new CustomAdapter(dataModels,getApplicationContext());
//        listView.setAdapter(adapter);

    }


    private String download() throws IOException {
        String result = "";
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
                result = result + line;
            }
            return result;
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

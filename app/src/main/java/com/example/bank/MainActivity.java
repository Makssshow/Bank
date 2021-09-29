package com.example.bank;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView date, eur, usd, usdTitle, eurTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.date);

        eur = findViewById(R.id.eur);
        usd = findViewById(R.id.usd);
        eurTitle = findViewById(R.id.eurTitle);
        usdTitle = findViewById(R.id.usdTitle);

        //Get a date
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat dataText = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String formattedDate = dataText.format(c);
        //Set the date
        date.setText(formattedDate);

        formattedDate = df.format(c);
        //Generate link
        final String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + formattedDate;


        new Thread(() -> {
            try {
                String content = download(url);

                ValuteXmlParser parser = new ValuteXmlParser();
                if (parser.parse(content)) {

                    ArrayList<Valute> users = parser.getUsers();

                    runOnUiThread(() -> {
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getCharCode().equals("USD")) {
                                usdTitle.setText(users.get(i).getCharCode());
                                usd.setText(users.get(i).getValue());
                            } else if (users.get(i).getCharCode().equals("EUR")) {
                                eurTitle.setText(users.get(i).getCharCode());
                                eur.setText(users.get(i).getValue());
                            }
                        }
                    });
                }

            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }).start();


    }


    private String download(String urlPath) throws IOException {
        StringBuilder xmlResult = new StringBuilder();
        BufferedReader reader = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                xmlResult.append(line);
            }
            return xmlResult.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

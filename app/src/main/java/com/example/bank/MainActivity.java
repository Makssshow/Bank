package com.example.bank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        SimpleDateFormat dataText = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        //Set the date
        date.setText(formattedDate);


        formattedDate = dataText.format(c);
        //Generate link
        final String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + formattedDate;

        new Thread(() -> {
            try {
                String content = download(url);

                ValuteXmlParser parser = new ValuteXmlParser();
                if (parser.parse(content)) {

                    ArrayList<Valute> users = parser.getValute();

                    runOnUiThread(() -> {
                        for (int i = 0; i < users.size(); i++) {
                            Valute item = users.get(i);
                            String charCode = item.getCharCode();
                            String Value = item.getValue();
                            if (charCode.equals("USD")) {
                                usdTitle.setText(charCode);
                                usd.setText(Value);
                            } else if (charCode.equals("EUR")) {
                                eurTitle.setText(charCode);
                                eur.setText(Value);
                            }
                        }
                    });
                }

            } catch (IOException ex) {
                runOnUiThread(() -> {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        }).start();


    }


    public void Login(View view) {
        FragmentManager manager = getSupportFragmentManager();
        DialogFragmentClass myDialogFragment = new DialogFragmentClass();
        myDialogFragment.show(manager, "Login");
    }

    public void ATM(View v) {
        Intent activityATM = new Intent(getApplicationContext(), ATM.class);
        startActivity(activityATM);
//        finish();
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

package com.example.bank;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class Course extends AppCompatActivity {
    ArrayList<Float> more = new ArrayList<>();
    ArrayList<Boolean> bigger = new ArrayList<>();
    ArrayList<Valute> valute = new ArrayList<>();

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.course_action_bar);
        View view = getSupportActionBar().getCustomView();

        TextView date = view.findViewById(R.id.action_date);
        list = findViewById(R.id.listCourse);

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String today = format.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String yesterday = format.format(cal.getTime());
        cal.add(Calendar.DATE, -2);
        String beforeYesterday = format.format(cal.getTime());

        date.setText(today);

        new Thread(() -> {
            try {
                String yesterdayXML = download("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + yesterday);
                String beforeYesterdayXML = download("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + beforeYesterday);

                ValuteXmlParser parser = new ValuteXmlParser();
                runOnUiThread(() -> {
                    TextView a = findViewById(R.id.a);
//                    a.setText(yesterdayXML);
                });
                if (parser.parse(beforeYesterdayXML)) {
                    for (int i = 0; i < parser.getCount(); i++) {
                        String string = parser.getValute().get(i).getValue().replace(',', '.');
                        more.add(Float.parseFloat(string));
                    }
                }

                parser = new ValuteXmlParser();

                if (parser.parse(yesterdayXML)) {
                    for (int i = 0; i < parser.getCount(); i++) {
                        Valute current = parser.getValute().get(i);
                        valute.add(current);
                        if (more.get(i) < Float.parseFloat(current.getValue().replace(',', '.'))) {
                            bigger.add(true);
                        } else {
                            bigger.add(false);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                ValuteAdapter adapter = new ValuteAdapter(getApplicationContext(), valute, bigger);
                list.setAdapter(adapter);
            });
        }).start();
    }


    public String download(String urlPath) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream is = null;
        BufferedReader buffer = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            buffer = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = buffer.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (buffer != null) {
                buffer.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
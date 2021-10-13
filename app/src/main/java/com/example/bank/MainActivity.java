package com.example.bank;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;

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
    String token;

    AuthAPI auth = new AuthAPI("https://dev-6bzrae6x.us.auth0.com", "BscfhjE7x4gLRC5f25WPmnEqVrbZlCq8", "5J2i82xvVulAWS1o90ksimlyx9xz7oy6TLcOQ-mJiE62ofBobiLJNr4GQfTmGwcF");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String urll = auth.authorizeUrl("https://dev-6bzrae6x.us.auth0.com/authorize")
                .withAudience("https://dev-6bzrae6x.us.auth0.com/users")
                .withScope("openid contacts")
                .withState("state123")
                .build();


        date = findViewById(R.id.date);

        eur = findViewById(R.id.eur);
        usd = findViewById(R.id.usd);
        eurTitle = findViewById(R.id.eurTitle);
        usdTitle = findViewById(R.id.usdTitle);


        //Get a date
        final Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        c.add(Calendar.DATE, -1);
        Date yesterday = c.getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat dataText = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(today);
        //Set the date
        date.setText(formattedDate);


        formattedDate = dataText.format(yesterday);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        LayoutInflater inflater = this.getLayoutInflater();
        View mView = inflater.inflate(R.layout.login, null);
        builder.setView(mView);

        final EditText usernameField = mView.findViewById(R.id.loginField);
        final EditText passwordField = mView.findViewById(R.id.passwordField);

        //Setting message manually and performing action on button click
        builder.setCancelable(false)
                .setNegativeButton("Отмена", (dialog, id) -> dialog.cancel())
                .setPositiveButton("Добавить", (dialog, id) -> {
                    String password = "12345678";
                    String[] dataa = {"maksrio2003g@gmail.com", password};

                    new RetrieveFeedTask().execute(dataa);

                })
                .setTitle("Авторизация")
                .create().show();

    }

    public void checkToken(String token) {
        if (!token.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), ATM.class);
            startActivity(intent);
            finish();
        }
    }

    public void ATM(View v) {
        Intent activityATM = new Intent(getApplicationContext(), ATM.class);
        startActivity(activityATM);
        finish();
    }

    public void Course(View v) {
        Intent intent = new Intent(getApplicationContext(), Course.class);
        startActivity(intent);
        finish();
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            runOnUiThread(() -> {
                token = result;
                TextView a = findViewById(R.id.abc);
                a.setText(result);
//                checkToken(result);
            });
        }

        protected String doInBackground(String... data) {
//            AuthRequest request = auth.login("maksrio2003g@gmail.com", "12345678")
//                    .setAudience("https://dev-6bzrae6x.us.auth0.com/users")
//                    .setScope("openid contacts");
            try {
                TokenHolder result = auth.login(data[0], data[1].toCharArray())
                        .setScope("openid email")
                        .execute();

//                runOnUiThread(() -> {
//                TextView a = findViewById(R.id.abc);
//                a.setText(result.getAccessToken());
//                });
                return result.getAccessToken();
            } catch (APIException exception) {
                // api error
                runOnUiThread(() -> {

                    TextView a = findViewById(R.id.abc);
                    a.setText(exception.getMessage());
                });
            } catch (Auth0Exception exception) {
                // request error
                runOnUiThread(() -> {

                    TextView a = findViewById(R.id.abc);
                    a.setText(exception.getMessage());
                });
            }
            ;
            return null;
        }
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

package com.example.bank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView date, eur, usd, usdTitle, eurTitle;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = ApiUtils.getUserService();


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
        c.add(Calendar.DATE, -2);
        Date beforeYesterday = c.getTime();

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
                    doLogin(usernameField.getText().toString(), passwordField.getText().toString());
                })
                .setTitle("Авторизация")
                .create().show();
//        FragmentManager manager = getSupportFragmentManager();
//        DialogFragmentClass myDialogFragment = new DialogFragmentClass();
//        myDialogFragment.show(manager, "Login");
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

    private void doLogin(final String username, final String password) {
        Call call = userService.login(username, password);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    ResObj resObj = (ResObj) response.body();
                    if (resObj.getMessage().equals("true")) {
                        //login start main activity
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else {
//                        Toast.makeText(LoginActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(LoginActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }});
        };


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

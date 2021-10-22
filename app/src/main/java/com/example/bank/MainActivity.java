package com.example.bank;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


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
import java.util.List;
import java.util.Locale;

import okhttp3.internal.cache.DiskLruCache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView date, eur, usd, usdTitle, eurTitle;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences("pref", 0);
        if (!pref.getBoolean("tutorial", false)) {
            Intent intent = new Intent(MainActivity.this, introActivity.class);
            startActivity(intent);
            finish();
        }


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);


        date = findViewById(R.id.date);

        eur = findViewById(R.id.eur);
        usd = findViewById(R.id.usd);
        eurTitle = findViewById(R.id.eurTitle);
        usdTitle = findViewById(R.id.usdTitle);

        editor = pref.edit();


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

//        final EditText usernameField = mView.findViewById(R.id.loginField);
//        final EditText passwordField = mView.findViewById(R.id.passwordField);

        //Setting message manually and performing action on button click
        builder.setCancelable(false)
                .setNegativeButton("Отмена", (dialog, id) -> {
                            dialog.cancel();
                        }
                )
                .setPositiveButton("Добавить", (dialog, id) -> {
                    Dialog f = (Dialog) dialog;
                    EditText loginField = f.findViewById(R.id.loginField);
                    EditText passwordField = f.findViewById(R.id.passwordField);

                    String login = loginField.getText().toString();
                    String password = passwordField.getText().toString();
                    RolePost data = new RolePost();
//                    data.setId("");
                    data.setName(login);
                    data.setDescription(password);

                    NetworkService.getInstance()
                            .getJSONApi()
                            .postRole(data)
                            .enqueue(new Callback<Role>() {
                                @Override
                                public void onResponse(@NonNull Call<Role> call, @NonNull Response<Role> response) {
                                    Role role = response.body();
//                                    TextView a = findViewById(R.id.abc);
                                    if (role != null) {
                                        editor = pref.edit();
                                        editor.putString("token", role.getId());
                                        editor.apply();
                                        checkToken();
                                    } else {
                                        Toast.makeText(MainActivity.this, response.raw().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Role> call, @NonNull Throwable t) {
                                    TextView a = findViewById(R.id.abc);
                                    a.append(t.getMessage());
                                    t.printStackTrace();
                                }
                            })
                    ;

                })
                .setTitle("Авторизация")
                .create().show();

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

    private void checkToken() {
        if (!pref.getString("token", "").isEmpty()) {
            NetworkService.getInstance().getJSONApi().getRoleById(pref.getString("token", "")).enqueue(new Callback<Role>() {
                @Override
                public void onResponse(Call<Role> call, Response<Role> response) {
                    Role role = response.body();
                    if (role != null) {
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        ArrayList<String> array = new ArrayList<>();
                        array.add(role.getName());
                        array.add(role.getDescription());
                        intent.putExtra("User", array);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Role> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
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

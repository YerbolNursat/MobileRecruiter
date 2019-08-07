package com.example.mobilerecruiter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity {
    EditText username,password;
    Button login;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);

        setContentView(R.layout.activity_login);
        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        login=findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Check()){
                    Log();
                }
            }
        });

    }

    private boolean Check() {
        return username.getText().length() != 0 && password.getText().length() != 0;
    }

    private void Log() {
        NetworkService.getInstance().
                getJSONApi().
                createToken(username.getText().toString(),password.getText().toString()).
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if(response.isSuccessful()) {
                            JSONObject json = null;
                            try {
                                assert response.body() != null;
                                json = new JSONObject(response.body().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert json != null;
                                preferences.edit().putInt("id", json.getInt("id")).apply();
                                preferences.edit().putString("token", "bearer " + json.getString("token")).apply();
                                preferences.edit().putString("username", username.getText().toString()).apply();
                                int is_admin = json.getInt("is_admin");
                                if (is_admin == 0) {
                                    preferences.edit().putBoolean("is_admin", false).apply();
                                } else {
                                    preferences.edit().putBoolean("is_admin", true).apply();
                                }

                                StartIntent();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        Log.d("Error", t.toString());
                    }
                });
    }

    private void checkToken() {

        NetworkService.getInstance().
                getJSONApi().
                checkToken(preferences.getString("token","")).
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            StartIntent();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        Log.d("Error", t.toString());
                    }
                });

    }
    private void StartIntent(){
        finish();
        Intent intent= new Intent(getApplication(),MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkToken();
    }

}

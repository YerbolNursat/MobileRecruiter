package com.example.mobilerecruiter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comments extends AppCompatActivity {
    private SharedPreferences preferences;
    String id;
    EditText editText;
    Button ok,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        preferences = Objects.requireNonNull(getApplicationContext()).getSharedPreferences("myPrefs", MODE_PRIVATE);
        ok=findViewById(R.id.activity_comments_btn_ok);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        cancel=findViewById(R.id.activity_comments_btn_cancel);
        editText=findViewById(R.id.activity_comments_description2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createComment();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void createComment() {
        NetworkService.getInstance()
                .getJSONApi()
                .postComment(editText.getText().toString(),Integer.parseInt(id),preferences.getInt("id",0))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error", t.toString());
                    }
                });
    }

}

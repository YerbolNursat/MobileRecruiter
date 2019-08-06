package com.example.mobilerecruiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vacancy_add extends AppCompatActivity {
    EditText title,description,experience;
    Button create,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_add);
        title=findViewById(R.id.vacancy_add_title);
        description=findViewById(R.id.vacancy_add_description);
        experience=findViewById(R.id.vacancy_add_experience);
        create=findViewById(R.id.vacancy_add_btn_ok);
        cancel=findViewById(R.id.vacancy_add_btn_cancel);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckParameters()){
                    createVacancy();
                    finish();
                }
                System.out.println("Заполните все");
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void createVacancy() {
        NetworkService.getInstance().
                getJSONApi().
                postVacancy(title.getText().toString(),description.getText().toString(),experience.getText().toString(),1).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        finish();
                        Log.d("Success", "200: ");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error", "Error: ");
                    }
                });
    }
    private boolean CheckParameters() {
        return title.getText().toString().length() != 0 && description.getText().toString().length() != 0 && experience.getText().toString().length() != 0;
    }
}

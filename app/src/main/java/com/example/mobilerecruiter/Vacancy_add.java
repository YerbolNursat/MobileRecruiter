package com.example.mobilerecruiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if (CheckParameters()) {
                    createVacancy();
                } else {
                    Toast.makeText(Vacancy_add.this, "Заполните всё", Toast.LENGTH_SHORT).show();
                }
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
                        if (response.isSuccessful()) {
                            finish();
                            Log.d("Success", "200: ");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error", "Error: ");
                    }
                });
    }
    private boolean CheckParameters() {
        return title.getText().toString().replaceAll(" ","").length() != 0 && description.getText().toString().replaceAll(" ","").length() != 0 && experience.getText().toString().replaceAll(" ","").length() != 0;
    }
}

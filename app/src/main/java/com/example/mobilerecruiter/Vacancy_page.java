package com.example.mobilerecruiter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vacancy_page extends AppCompatActivity {
    TextView title, description,experience;
    String id;
    ArrayList<Post> posts;
    ArrayList<Vacancy> vacancies;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacancy_page);
        title = findViewById(R.id.vacancy_page_title);
        description = findViewById(R.id.vacancy_page_description);
        experience=findViewById(R.id.vacancy_page_experience);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                finish();
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        getInfo();
    }
    private void getInfo() {
        NetworkService.getInstance().
                getJSONApi().
                getVacancyById(Integer.parseInt(id)).
                enqueue(new Callback<List<Vacancy>>() {
                    @Override
                    public void onResponse(Call<List<Vacancy>> call, Response<List<Vacancy>> response) {
                        assert response.body() != null;
                        vacancies=new ArrayList<>(response.body());
                        setParameters();
                    }

                    @Override
                    public void onFailure(Call<List<Vacancy>> call, Throwable t) {
                        Log.e("Error", t.toString());
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setParameters() {
        rv=findViewById(R.id.vacancy_page_rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplication()));
        rv.setHasFixedSize(true);
        posts=vacancies.get(0).getCandidates();
        VacancyPostAdapter adapter=new VacancyPostAdapter(posts);
        rv.setAdapter(adapter);
        title.setText(vacancies.get(0).getTitle());
        description.setText(vacancies.get(0).getDescription());
        if(vacancies.get(0).getExperience()!=null)
        experience.setText(vacancies.get(0).getExperience());
    }
}

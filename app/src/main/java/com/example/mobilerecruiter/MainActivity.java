package com.example.mobilerecruiter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    public MainActivity() {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selected=null;
        switch (item.getItemId()) {
            case R.id.navigation_vacancies:
                selected=new Vacancies();
                break;
            case R.id.navigation_posts:
                selected=new Posts();
                break;
            case R.id.navigation_on_customer:
                selected=new On_interviews();
                break;
            case R.id.navigation_on_interview:
                selected=new is_deployed();
                break;
        }
        assert selected != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected).commit();
        return true;
    }
};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Vacancies()).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                preferences.edit().putString("token","").apply();
                Intent intent= new Intent(getApplication(),Login.class);
                finish();
                startActivity(intent);
                return true;
        }
        return true;
    }
}

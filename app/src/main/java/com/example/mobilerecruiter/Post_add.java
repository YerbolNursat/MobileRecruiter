package com.example.mobilerecruiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post_add extends AppCompatActivity {
    LinearLayout parameters,messages,buttons;
    RecyclerView rv;
    Button create,cancel;
    SearchView sv;
    EditText name,surname,telephon_number,skills;
    ArrayList<Message> mylist=new ArrayList<>();
    ArrayList<Message> events;
    String filename,id;
    ArrayList<Skills> Skills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_add);
        parameters=findViewById(R.id.candidate_add_parameters);
        messages=findViewById(R.id.candidate_add_messages);
        buttons=findViewById(R.id.candidate_add_buttons);
        rv=findViewById(R.id.candidate_add_recycle_view);
        create=findViewById(R.id.candidate_add_btn_create);
        cancel=findViewById(R.id.candidate_add_btn_cancel);
        sv=findViewById(R.id.candidate_add_search_view);
        name=findViewById(R.id.candidate_add_name);
        surname=findViewById(R.id.candidate_add_surname);
        telephon_number=findViewById(R.id.candidate_add_telephone_number);
        skills=findViewById(R.id.candidate_add_skills);

        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parameters.setVisibility(View.GONE);
                buttons.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setHasFixedSize(true);
        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                parameters.setVisibility(View.VISIBLE);
                buttons.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
                sv.setQuery(events.get(position).getFrom(),false);
                id=events.get(position).getId();
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckParameters()) {
                    checkTags(getarray(),id);
                }else {
                    System.out.println("Заполните всё");
//                    Toast.makeText(getApplicationContext(),"Заполните всё",Toast.LENGTH_SHORT).show();
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

    private ArrayList<String> getarray() {
        String[] temparr=skills.getText().toString().split(" ");
        return new ArrayList<>(Arrays.asList(temparr));

    }

    private boolean CheckParameters() {
        if(sv.getQuery().toString().length()!=0&&name.getText().toString().length()!=0&&surname.getText().toString().length()!=0&&telephon_number.getText().toString().length()!=0){
            return true;
        }
        return false;
    }
    private void post(String filename, ArrayList<String> tags) {
        NetworkService.getInstance().
                getJSONApi().postPost(name.getText().toString(),surname.getText().toString(),sv.getQuery().toString(),telephon_number.getText().toString(),filename,1,tags).
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
    private void checkTags(final ArrayList<String> arrayList, final String id){
        NetworkService.getInstance().
                getJSONApi().
                getSkills().
                enqueue(new Callback<List<Skills>>() {
                    @Override
                    public void onResponse(Call<List<Skills>> call, Response<List<Skills>> response) {
                        assert response.body() != null;
                        Skills = new ArrayList<>(response.body());
                        for (int i=0;i<arrayList.size();i++){
                            boolean bool=false;
                            for(int j=0;j<Skills.size();j++){
                                if(arrayList.get(i).equals(Skills.get(j).getTag())) {
                                    bool=true;
                                }
                            }
                            if (!bool){
                                System.out.println("Tags incorrect");
//                             Toast.makeText(getApplicationContext(),"Tags incorrect",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        resendstorage(arrayList,id);
                    }

                    @Override
                    public void onFailure(Call<List<Skills>> call, Throwable t) {

                    }
                });
    }
    private void resendstorage(final ArrayList<String> arrayList, String id) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("element1", id)
                .build();

        NetworkService.getInstance().
                getJSONApi().
                resendstorage(requestBody).
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        assert response.body() != null;
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response.body().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            filename =json.getString("cv_file_name");
                            post(filename,arrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("Error", t.toString());
                    }
                });

    }
    @Override
    protected void onStart() {
        setRecycleView();
        super.onStart();
    }
    private void setRecycleView() {
        NetworkService.getInstance()
                .getJSONApi()
                .getMessages()
                .enqueue(new Callback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        assert response.body() != null;
                        events = new ArrayList<>(response.body());
                        Message_adapter adapter = new Message_adapter(events);
                        rv.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        if (sv != null) {
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText.toLowerCase());
                    return false;
                }
            });
        } else {
            mylist.clear();
        }

    }
    private void search(String str) {
        mylist=new ArrayList<>();
        for(Message object:events){
            if (object.getFrom().toLowerCase().contains(str)||object.getFilename().toLowerCase().contains(str)||object.getSubject().toLowerCase().contains(str)) {
                mylist.add(object);
            }
        }
        Message_adapter adapterClass=new Message_adapter(mylist);
        rv.setAdapter(adapterClass);
    }
}
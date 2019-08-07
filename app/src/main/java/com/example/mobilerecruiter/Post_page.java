package com.example.mobilerecruiter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Post_page extends AppCompatActivity {
    TextView name_surname, skills, telephon_number, mail;
    Button call, message, comment;
    String id;
    ArrayList<Post> post;
    ArrayList<Comment> events;
    RecyclerView rv;
    ProgressDialog progressDialog;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        name_surname = findViewById(R.id.post_page_name_surname);
        skills = findViewById(R.id.post_page_skills);
        telephon_number = findViewById(R.id.post_page_telephon_number);
        mail = findViewById(R.id.post_page_mail);
        call = findViewById(R.id.post_page_button_call);
        comment = findViewById(R.id.post_page_button_add_comment);
        message = findViewById(R.id.post_page_button_message);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        progressDialog=new ProgressDialog(Post_page.this);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL );
        handler=new Handler();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + telephon_number.getText()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                String[] recipients={mail.getText().toString()};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
                emailIntent.setType("text/plain");
                emailIntent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(emailIntent, "Send mail"));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                finish();
            case R.id.download:
                Download();
                Toast.makeText(getApplication(), "Downloaded", Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    private void Download() {
        final String url = (NetworkService.BASE_URL+"file/"+post.get(0).getCv_file_name());
        System.out.println(url);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                okhttp3.Response response = null;
                try {
                    response = client.newCall(request).execute();
                    long file_size = response.body().contentLength();
                    BufferedInputStream inputStream = new BufferedInputStream(response.body().byteStream());
                    OutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory() +
                            "/Download/" + name_surname.getText().toString().replace(" ","_")+".pdf");
                    byte[] data = new byte[81920];
                    float total = 0;
                    int read_bytes = 0;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.show();
                        }
                    });

                    while ((read_bytes = inputStream.read(data)) != -1) {
                        total = total + read_bytes;
                        outputStream.write(data, 0, read_bytes);
                        int progress=(int) (total * 100/ file_size);
                        progressDialog.setProgress(progress);
                    }
                    progressDialog.dismiss();
                    outputStream.flush();
                    outputStream.close();
                    response.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getInfo();
    }


    private void getInfo() {
        NetworkService.getInstance().
                getJSONApi().
                getPostById(Integer.parseInt(id)).
                enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        assert response.body() != null;
                        post = new ArrayList<>(response.body());
                        setParameters();
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        Log.e("error", String.valueOf(t));
                    }
                });

    }

    @SuppressLint("SetTextI18n")
    private void setParameters() {
        telephon_number.setText(post.get(0).getTelephon_number());
        mail.setText(post.get(0).getMail());
        rv=findViewById(R.id.post_page_rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplication()));
        rv.setHasFixedSize(true);
        events=post.get(0).getComments();
        Comment_adapter adapter=new Comment_adapter(events);
        rv.setAdapter(adapter);
        skills.setText("");
        if (post.get(0).getSkills().size() > 0) {
            for (int i = 0; i < post.get(0).getSkills().size(); i++) {
                skills.setText(skills.getText() + post.get(0).getSkills().get(i)+ ",");
            }
        }
        name_surname.setText(post.get(0).getF_name() + " " + post.get(0).getL_name());
    }
}
